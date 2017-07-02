package net.bms.genera.entities.passive;

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

    public EntityFaerie(World worldIn) {
        super(worldIn);
        setSize(0.1F, 0.1F);
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
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ItemGlassJar && stack.getItemDamage() == 0) {
            onKillCommand();
            stack = new ItemStack(ItemGlassJar, 1, 1);
            player.setHeldItem(hand, stack);
        }

        return super.processInteract(player, hand);
    }
}
