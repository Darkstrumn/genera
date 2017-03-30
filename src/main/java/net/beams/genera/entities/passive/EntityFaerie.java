package net.beams.genera.entities.passive;

import net.beams.genera.entities.ai.AIRandomFly;
import net.minecraft.entity.EntityFlying;
import net.minecraft.world.World;

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
        // PLACEHOLDER FOR POSSIBLE FUTURE USE
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new AIRandomFly(this));
    }

}
