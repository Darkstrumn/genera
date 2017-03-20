package net.beams.genera.event;

import net.beams.genera.init.GeneraItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by ben on 3/19/17.
 */
@Mod.EventBusSubscriber
public class BlockEventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.TALLGRASS) {
            event.getWorld().spawnEntity(new EntityItem(event.getWorld(),
                    event.getPos().getX(),
                    event.getPos().getY(),
                    event.getPos().getZ(),
                    new ItemStack(GeneraItems.ItemSeedNightshade,
                            MathHelper.getInt(event.getWorld().rand, 0, 2))));
        }
    }

}
