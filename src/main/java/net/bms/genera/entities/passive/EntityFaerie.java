package net.bms.genera.entities.passive;

import io.netty.buffer.ByteBuf;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.rituals.RitualRecipe;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by ben on 3/25/17.
 */
public class EntityFaerie extends EntityFlying implements IEntityAdditionalSpawnData {
    @CapabilityInject(IFaerieInformation.class)
    private static Capability<IFaerieInformation> FAERIE_INFORMATION = null;
    public IFaerieInformation faerieInformation = FAERIE_INFORMATION.getDefaultInstance();
    private static final int EXP_TO_LEVEL_UP = 50;

    public EntityFaerie(World worldIn) {
        this(worldIn, 2.0D, 0, 0.1F, 1);
    }

    public EntityFaerie(World worldIn, double maxHealth, int type, float size, int level) {
        this(worldIn, maxHealth, type, size, level, 0);
    }

    public EntityFaerie(World worldIn, double maxHealth, int type, float size, int level, int current_exp) {
        super(worldIn);
        faerieInformation.setMaxHealth(maxHealth);
        faerieInformation.setType(type);
        faerieInformation.setSize(size);
        faerieInformation.setLevel(level);
        faerieInformation.setCurrentExp(current_exp);
        setSize(0.5F, 0.5F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityFaerie.AIRandomFly(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!player.world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() == Items.GLASS_BOTTLE) {
                this.setDead();
                ItemStack newStack = new ItemStack(GeneraItems.ItemGlassJarFull);
                newStack.setTagCompound(new NBTTagCompound());
                NBTTagCompound nbt = newStack.getTagCompound();
                if (nbt == null) return false;
                nbt.setFloat("size", faerieInformation.getSize());
                nbt.setInteger("type", faerieInformation.getType());
                nbt.setDouble("max_health", faerieInformation.getMaxHealth());
                nbt.setInteger("level", faerieInformation.getLevel());
                nbt.setInteger("current_exp", faerieInformation.getCurrentExp());
                player.setHeldItem(hand, newStack);
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
        runRituals();
        if (faerieInformation.getCurrentExp() >= EXP_TO_LEVEL_UP) {
            faerieInformation.setLevel(faerieInformation.getLevel() + 1);
            faerieInformation.setCurrentExp(0);
        }
        else if (faerieInformation.getCurrentExp() < 0) {
            faerieInformation.setLevel(faerieInformation.getLevel() - 1);
            faerieInformation.setCurrentExp(0);
        }
        if (faerieInformation.getLevel() < 0)
            faerieInformation.setLevel(0);
        addEffects();
    }

    // called by server
    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeDouble(faerieInformation.getMaxHealth());
        buffer.writeFloat(faerieInformation.getSize());
        buffer.writeInt(faerieInformation.getType());
        buffer.writeInt(faerieInformation.getLevel());
        buffer.writeInt(faerieInformation.getCurrentExp());
    }

    // called by client
    @Override
    public void readSpawnData(ByteBuf additionalData) {
        faerieInformation.setMaxHealth(additionalData.readDouble());
        faerieInformation.setSize(additionalData.readFloat());
        faerieInformation.setType(additionalData.readInt());
        faerieInformation.setLevel(additionalData.readInt());
        faerieInformation.setCurrentExp(additionalData.readInt());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound comp) {
        super.writeEntityToNBT(comp);

        comp.setFloat("size", faerieInformation.getSize());
        comp.setInteger("current_exp", faerieInformation.getCurrentExp());
        comp.setInteger("type", faerieInformation.getType());
        comp.setInteger("level", faerieInformation.getLevel());
        comp.setDouble("max_health", faerieInformation.getMaxHealth());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound comp) {
        super.readEntityFromNBT(comp);
        faerieInformation.setSize(comp.getFloat("size"));
        faerieInformation.setCurrentExp(comp.getInteger("current_exp"));
        faerieInformation.setType(comp.getInteger("type"));
        faerieInformation.setLevel(comp.getInteger("level"));
        faerieInformation.setMaxHealth(comp.getDouble("max_health"));
    }

    private void runRituals() {
        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow((double) faerieInformation.getSize() + 1));
        if (!items.isEmpty()) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            int experienceCost = 0;
            int index = 0;
            ItemStack stack = ItemStack.EMPTY;
            Iterator<RitualRecipe> iterator = GameRegistry.findRegistry(RitualRecipe.class).iterator();
            for (EntityItem item : items) {
                itemStacks.add(index, item.getItem());
            }
            while (iterator.hasNext()) {
                if (!stack.isEmpty()) break;
                RitualRecipe ritual = iterator.next();
                stack = ritual.getResult(itemStacks, faerieInformation.getLevel(), faerieInformation.getType());
                experienceCost = ritual.getInt("experience_cost");
            }
            if (!stack.isEmpty()) {
                for (EntityItem item : items) {
                    item.setItem(ItemStack.EMPTY);
                }
                EntityItem item = new EntityItem(world, posX, posY, posZ);
                item.setItem(stack);
                if (!world.isRemote)
                    world.spawnEntity(item);
                faerieInformation.setLevel(faerieInformation.getLevel() - experienceCost);
            }
        }
    }

    private void addEffects() {
        EntityPlayer player = this.world.getNearestAttackablePlayer(this, 10, 10);
        if (player == null) return;
        switch (faerieInformation.getType()) {
            case 0: // Woodland
                if (player.getEntityData().getInteger("genera.sacrifices_made") < 5) {
                    Potion healthBoost = Potion.getPotionById(21);
                    if (healthBoost == null) return;
                    if (!player.isPotionActive(healthBoost)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(healthBoost, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                    if (faerieInformation.getLevel() >= 3) {
                        Potion health = Potion.getPotionById(6);
                        if (health == null) return;
                        if (!player.isPotionActive(health)) {
                            faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                            player.addPotionEffect(new PotionEffect(health, ((int) faerieInformation.getMaxHealth()) * 300));
                        }
                    }
                    if (faerieInformation.getLevel() >= 10) {
                        boolean hasCinnabar = false;
                        int slot = 0;
                        for (; slot <= player.inventory.getSizeInventory(); slot++) {
                            ItemStack stack = player.inventory.getStackInSlot(slot);
                            if (stack.getItem() == GeneraItems.ItemCinnabar &&
                                    stack.getCount() >= 2) {
                                hasCinnabar = true;
                            }
                        }
                        if (hasCinnabar) {
                            if (player.getHealth() <= player.getMaxHealth() - 1.0) {
                                player.setHealth(player.getMaxHealth());
                                faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() - 25);
                                player.inventory.decrStackSize(slot, 2);
                            }
                        }
                    }
                }
                else {
                    Potion poison = Potion.getPotionById(19);
                    if (poison == null) return;
                    if (!player.isPotionActive(poison)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(poison, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                }
                break;
            case 1: // Underground
                if (player.getEntityData().getInteger("genera.sacrifices_made") < 5) {
                    Potion haste = Potion.getPotionById(3);
                    if (haste == null) return;
                    if (!player.isPotionActive(haste)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(haste, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                    if (faerieInformation.getLevel() >= 5) {
                        Potion nightVision = Potion.getPotionById(16);
                        if (nightVision == null) return;
                        if (!player.isPotionActive(nightVision)) {
                            faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                            player.addPotionEffect(new PotionEffect(nightVision, ((int) faerieInformation.getMaxHealth()) * 300));
                        }
                    }
                }
                else {
                    Potion blindness = Potion.getPotionById(15);
                    if (blindness == null) return;
                    if (!player.isPotionActive(blindness)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(blindness, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                }
                break;
            case 2: // Mountainous
                if (player.getEntityData().getInteger("genera.sacrifices_made") < 5) {
                    Potion jump_boost = Potion.getPotionById(8);
                    if (jump_boost == null) return;
                    if (!player.isPotionActive(jump_boost)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(jump_boost, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                }
                else {
                    Potion wither = Potion.getPotionById(20);
                    if (wither == null) return;
                    if (!player.isPotionActive(wither)) {
                        faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                        player.addPotionEffect(new PotionEffect(wither, ((int) faerieInformation.getMaxHealth()) * 300));
                    }
                }
                break;
        }
    }

    static class AIRandomFly extends EntityAIBase
    {
        private final EntityFlying parentEntity;

        public AIRandomFly(EntityFlying entity)
        {
            this.parentEntity = entity;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.parentEntity.getRNG();
            double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2 + 2.0, 1.0D);
        }
    }
}