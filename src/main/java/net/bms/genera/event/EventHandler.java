package net.bms.genera.event;

import net.bms.genera.init.GeneraBlocks;
import net.bms.genera.init.GeneraItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by ben on 3/19/17.
 */
@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.TALLGRASS) {
            event.getWorld().spawnEntity(new EntityItem(event.getWorld(),
                    event.getPos().getX(),
                    event.getPos().getY(),
                    event.getPos().getZ(),
                    new ItemStack(GeneraItems.ItemSeedNightshade,
                            MathHelper.getInt(event.getWorld().rand, 0, 1))));
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        GeneraItems.init(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        GeneraBlocks.init(event);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        GeneraItems.initModels();
        GeneraBlocks.initModels();
    }
}
