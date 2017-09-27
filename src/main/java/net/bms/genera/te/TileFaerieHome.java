package net.bms.genera.te;

import net.bms.genera.Genera;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.packets.MessageGlassJarUpdateStats;
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

/**
 * Created by ben on 4/2/17.
 */
public class TileFaerieHome extends TileEntity implements ITickable {
    private final int SIZE = 1;
    private final int TIME_BETWEEN_GROWTH = 20 * 30;
    private int timeSinceLastGrowth = 0;

    public ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE) {
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
        if (!world.isRemote) {
            timeSinceLastGrowth++;
            if (timeSinceLastGrowth >= TIME_BETWEEN_GROWTH) {
                ItemStack stack = cap.getStackInSlot(0);
                if (stack.getItem() == GeneraItems.ItemGlassJar &&
                        stack.getMetadata() == 1 &&
                        stack.getCount() == 1) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    if (nbt != null) {
                        nbt.setFloat("size", nbt.getFloat("size") + 0.02F);
                        nbt.setDouble("max_health", nbt.getDouble("max_health") + 0.5D);
                        nbt.setInteger("current_exp", nbt.getInteger("current_exp") + 25);
                    }
                }
                Genera.SIMPLEIMPL_INSTANCE.sendToAll(new MessageGlassJarUpdateStats(stack, getPos()));
                timeSinceLastGrowth = 0;
            }
        }
    }
}
