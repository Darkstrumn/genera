package net.bms.genera.te;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

/**
 * Created by ben on 4/2/17.
 */
public class TileFaerieHome extends TileEntity implements ITickable {
    private static final int SIZE = 6;
    private static final int TIME_BETWEEN_GROWTH = 600;
    private static int timeSinceLastGrowth = 0;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            TileFaerieHome.this.markDirty();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) nbt.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("items", itemStackHandler.serializeNBT());
        return nbt;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return !isInvalid() && player.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> cap, EnumFacing side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
    }

    @Override
    public <T> T getCapability(Capability<T> cap, EnumFacing side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) itemStackHandler : super.getCapability(cap, side);
    }

    @Override
    public void update() {
        IItemHandler cap = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (cap == null) return;
        timeSinceLastGrowth++;
        if (timeSinceLastGrowth >= TIME_BETWEEN_GROWTH) {
            Random rand = new Random();
            for (int slot = 0; slot <= SIZE - 1; slot++) {
                float selector = rand.nextFloat();
                if (selector >= 0.0 && selector <= 0.6) {
                    if (cap.getStackInSlot(slot) == ItemStack.EMPTY) return;
                    NBTTagCompound nbt = cap.getStackInSlot(rand.nextInt(6)).getTagCompound();
                    if (nbt == null) return;
                    nbt.setFloat("size", nbt.getFloat("size") + 0.02F);
                } else if (selector >= 0.7 && selector <= 1.0) {
                    if (cap.getStackInSlot(slot) == ItemStack.EMPTY) return;
                    NBTTagCompound nbt = cap.getStackInSlot(rand.nextInt(6)).getTagCompound();
                    if (nbt == null) return;
                    nbt.setDouble("max_health", nbt.getDouble("max_health") + 1.0D);
                }
            }
            timeSinceLastGrowth = 0;
        }
    }
}
