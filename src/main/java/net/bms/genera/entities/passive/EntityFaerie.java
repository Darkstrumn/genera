package net.bms.genera.entities.passive;

import io.netty.buffer.ByteBuf;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.rituals.RitualRecipe;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Iterator;
import java.util.List;

import static net.bms.genera.init.GeneraItems.ItemGlassJar;

// TODO: Faeries can escape jars in chests, but not jars in Faerie Enclosures; this may not be possible

/**
 * Created by ben on 3/25/17.
 */
public class EntityFaerie extends EntityCreature implements IEntityAdditionalSpawnData{

    @CapabilityInject(IFaerieInformation.class)
    private static Capability<IFaerieInformation> FAERIE_INFORMATION = null;
    public IFaerieInformation faerieInformation = FAERIE_INFORMATION.getDefaultInstance();
    private static final int EXP_TO_LEVEL_UP = 100;

    public EntityFaerie(World worldIn) {
        this(worldIn, 2.0D, 0, 0.1F, 1);
    }

    public EntityFaerie(World worldIn, double maxHealth, int type, float size, int level) {
        super(worldIn);
        faerieInformation.setMaxHealth(maxHealth);
        faerieInformation.setType(type);
        faerieInformation.setSize(size);
        faerieInformation.setLevel(level);
        faerieInformation.setCurrentExp(0);
        setSize(faerieInformation.getSize(), faerieInformation.getSize());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAIWanderAvoidWater( this, 1.0D));
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
                nbt.setInteger("level", faerieInformation.getLevel());
                nbt.setInteger("current_exp", faerieInformation.getCurrentExp());
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
        runRituals();
        if (faerieInformation.getCurrentExp() >= EXP_TO_LEVEL_UP) {
            faerieInformation.setLevel(faerieInformation.getLevel() + 1);
            faerieInformation.setCurrentExp(0);
        }
        addPotionEffects();
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
            Iterator<RitualRecipe> iterator = GameRegistry.findRegistry(RitualRecipe.class).iterator();
            for (EntityItem item : items) {
                while (iterator.hasNext()) {
                    RitualRecipe nextRitual = iterator.next();
                    if (faerieInformation.getType() != nextRitual.getFaerieType()) break;
                    String[] ingredientArray = nextRitual.getIngredient();
                    IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
                    if (itemRegistry != null) {
                        ResourceLocation resOne = new ResourceLocation(ingredientArray[0]);
                        if (itemRegistry.containsKey(resOne) &&
                                item.getItem().getItem() == itemRegistry.getValue(resOne) &&
                                item.getItem().getMetadata() == Integer.parseInt(ingredientArray[1])) {
                            String[] resultArray = nextRitual.getResult();
                            ResourceLocation resourceLocation = new ResourceLocation(resultArray[0]);
                            if (itemRegistry.containsKey(resourceLocation)) {
                                Item resultItem = itemRegistry.getValue(resourceLocation);
                                if (resultItem != null)
                                    item.setItem(new ItemStack(resultItem, Integer.parseInt(resultArray[1]),
                                            Integer.parseInt(resultArray[2])));
                            }
                        }
                    }
                }
            }
        }
    }

    private void addPotionEffects() {
        EntityPlayer player = this.world.getNearestAttackablePlayer(this, 10, 10);
        if (player == null) return;
        switch (faerieInformation.getType()) {
            case 0: // Woodland
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
                break;
            case 1: // Underground
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
                break;
            case 2: // Mountainous
                Potion jump_boost = Potion.getPotionById(8);
                if (jump_boost == null) return;
                if (!player.isPotionActive(jump_boost)) {
                    faerieInformation.setCurrentExp(faerieInformation.getCurrentExp() + 10);
                    player.addPotionEffect(new PotionEffect(jump_boost, ((int) faerieInformation.getMaxHealth()) * 300));
                }
                break;
        }
    }
}
