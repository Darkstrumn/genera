package net.bms.genera.init;

import net.bms.genera.blocks.*;
import net.bms.genera.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraBlocks {

    public static Block BlockNightshadeCrop;
    public static Block BlockFaerieHome;
    public static Block BlockWhiteMushroom;
    public static Block BlockBurdockCrop;
    public static Block BlockAltar;

    public static void init(IForgeRegistry<Block> registry) {
        BlockNightshadeCrop = new BlockNightshadeCrop();
        BlockFaerieHome =  new BlockFaerieHome();
        BlockWhiteMushroom = new BlockWhiteMushroom();
        BlockBurdockCrop = new BlockBurdockCrop();
        BlockAltar = new BlockAltar();

        registry.register(BlockFaerieHome);
        registry.register(BlockNightshadeCrop);
        registry.register(BlockWhiteMushroom);
        registry.register(BlockBurdockCrop);
        registry.register(BlockAltar);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderUtil.register(Item.getItemFromBlock(BlockNightshadeCrop),"nightshade");
        RenderUtil.register(Item.getItemFromBlock(BlockFaerieHome), "faerie_home");
        RenderUtil.register(Item.getItemFromBlock(BlockWhiteMushroom), "white_mushroom");
        RenderUtil.register(Item.getItemFromBlock(BlockBurdockCrop),"burdock");
        RenderUtil.register(Item.getItemFromBlock(BlockAltar), "altar");
    }
}
