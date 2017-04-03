package net.beams.genera.init;

import net.beams.genera.blocks.BlockFaerieHome;
import net.beams.genera.blocks.BlockNightshadeCrop;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraBlocks {

    public static Block BlockNightshadeCrop;
    public static Block BlockFaerieHome;

    public static void init() {
        BlockNightshadeCrop = new BlockNightshadeCrop();
        BlockFaerieHome =  new BlockFaerieHome();

        GameRegistry.register(BlockNightshadeCrop);
        GameRegistry.register(new ItemBlock(BlockNightshadeCrop).setRegistryName(BlockNightshadeCrop.getRegistryName()));
        GameRegistry.register(BlockFaerieHome);
        GameRegistry.register(new ItemBlock(BlockFaerieHome).setRegistryName(BlockFaerieHome.getRegistryName()));
    }
}
