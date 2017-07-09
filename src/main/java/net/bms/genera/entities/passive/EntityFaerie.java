package net.bms.genera.entities.passive;

import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.FaerieInformationProvider;
import net.bms.genera.entities.ai.AIRandomFly;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import static net.bms.genera.init.GeneraItems.ItemGlassJar;

/**
 * Created by ben on 3/25/17.
 */
public class EntityFaerie extends EntityFlying {

    public FaerieInformation faerieInformation = this.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null);

    public EntityFaerie(World worldIn) {
        super(worldIn);
        this.faerieInformation.setSize(0.1F);
        this.faerieInformation.setMaxHealth(4.0D);
        this.faerieInformation.setType(0);
        setSize(this.faerieInformation.getSize(), this.faerieInformation.getSize());
    }

    public EntityFaerie(World worldIn, Double maxHealth, int type, float size) {
        super(worldIn);
        this.faerieInformation.setMaxHealth(maxHealth);
        this.faerieInformation.setType(type);
        this.faerieInformation.setSize(size);
        setSize(this.faerieInformation.getSize(), this.faerieInformation.getSize());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new AIRandomFly(this));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.faerieInformation.getMaxHealth());
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ItemGlassJar && stack.getItemDamage() == 0) {
            onKillCommand();
            stack = new ItemStack(ItemGlassJar, 1, 1);
            stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).setSize(this.faerieInformation.getSize());
            stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).setType(this.faerieInformation.getType());
            stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).setMaxHealth(this.faerieInformation.getMaxHealth());
            player.setHeldItem(hand, stack);
        }

        return super.processInteract(player, hand);
    }
}
