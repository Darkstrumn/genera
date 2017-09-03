package net.bms.genera.entities.passive;

import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.entities.ai.AIRandomFly;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import static net.bms.genera.init.GeneraItems.ItemGlassJar;

/**
 * Created by ben on 3/25/17.
 */
public class EntityFaerie extends EntityFlying {

    @CapabilityInject(IFaerieInformation.class)
    private static Capability<IFaerieInformation> FAERIE_INFORMATION = null;
    private static IFaerieInformation faerieInformation = new FaerieInformation();

    public EntityFaerie(World worldIn) {
        super(worldIn);
        faerieInformation.setSize(0.1F);
        faerieInformation.setMaxHealth(4.0D);
        faerieInformation.setType(0);
        setSize(faerieInformation.getSize(), faerieInformation.getSize());
    }

    public EntityFaerie(World worldIn, Double maxHealth, int type, float size) {
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

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(faerieInformation.getMaxHealth());
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ItemGlassJar && stack.getItemDamage() == 0) {
            onKillCommand();
            stack.setItemDamage(1);
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                System.out.println("ItemGlassJar's tag compound is null. Tell Ben to fix this!");
            }
            else {
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
}
