package net.bms.genera.entities.passive;

import io.netty.buffer.ByteBuf;
import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.entities.ai.AIRandomFly;
import net.bms.genera.init.GeneraBlocks;
import net.bms.genera.init.GeneraItems;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.List;

import static net.bms.genera.init.GeneraItems.ItemGlassJar;

/**
 * Created by ben on 3/25/17.
 */
public class EntityFaerie extends EntityFlying implements IEntityAdditionalSpawnData{

    @CapabilityInject(IFaerieInformation.class)
    private static Capability<IFaerieInformation> FAERIE_INFORMATION = null;
    private IFaerieInformation faerieInformation = new FaerieInformation();

    public EntityFaerie(World worldIn) {
        this(worldIn, 2.0D, 0, 0.1F);
    }

    public EntityFaerie(World worldIn, double maxHealth, int type, float size) {
        super(worldIn);
        faerieInformation.setMaxHealth(maxHealth);
        faerieInformation.setType(type);
        faerieInformation.setSize(size);
        setSize(faerieInformation.getSize(), faerieInformation.getSize());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new AIRandomFly(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == ItemGlassJar && stack.getItemDamage() == 0) {
                onKillCommand();
                stack.setItemDamage(1);
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null) return false;
                nbt.setFloat("size", faerieInformation.getSize());
                nbt.setInteger("type", faerieInformation.getType());
                nbt.setDouble("max_health", faerieInformation.getMaxHealth());
                player.setHeldItem(hand, stack);
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean hasCapability(Capability<?> capObject, EnumFacing side) {
        return capObject == FAERIE_INFORMATION && side == EnumFacing.UP || super.hasCapability(capObject, side);
    }

    @Override
    public <T> T getCapability(Capability<T> capObject, EnumFacing side) {
        return capObject == FAERIE_INFORMATION && side == EnumFacing.UP ? FAERIE_INFORMATION.cast(FAERIE_INFORMATION.getDefaultInstance()) : super.getCapability(capObject, side);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow((double) faerieInformation.getSize() + 1));
        for (EntityItem item : items) {
            if (faerieInformation.getType() == 0) {
                if (item.getItem().getItem() == Item.getItemFromBlock(Blocks.BROWN_MUSHROOM)) {
                    int amount = item.getItem().getCount();
                    item.setItem(new ItemStack(Item.getItemFromBlock(GeneraBlocks.BlockWhiteMushroom), amount, 0));
                }
            }
            else if (faerieInformation.getType() == 1) {
                if (item.getItem().getItem() == Items.DYE && item.getItem().getMetadata() == 1) {
                    int amount = item.getItem().getCount();
                    item.setItem(new ItemStack(GeneraItems.ItemCinnabar, amount, 0));
                }
            }
        }

        EntityPlayer player = this.world.getNearestAttackablePlayer(this, 10, 10);
        if (player == null) return;
        switch (faerieInformation.getType()) {
            case 0: // Woodland
                Potion healthBoost = Potion.getPotionById(21);
                if (healthBoost == null) return;
                if (!player.isPotionActive(healthBoost))
                    player.addPotionEffect(new PotionEffect(healthBoost, ((int)faerieInformation.getMaxHealth()) * 150));
                break;
            case 1: // Underground
                Potion haste = Potion.getPotionById(3);
                if (haste == null) return;
                if (!player.isPotionActive(haste))
                    player.addPotionEffect(new PotionEffect(haste, ((int) faerieInformation.getMaxHealth()) * 150));
                break;
        }
    }

    // called by server
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeDouble(faerieInformation.getMaxHealth());
        buffer.writeFloat(faerieInformation.getSize());
        buffer.writeInt(faerieInformation.getType());
    }

    // called by client
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        faerieInformation.setType(additionalData.readInt());
        faerieInformation.setSize(additionalData.readFloat());
        faerieInformation.setMaxHealth(additionalData.readDouble());
    }
}
