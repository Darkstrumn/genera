package net.bms.genera.init;

import net.bms.genera.blocks.BlockBurdockCrop;
import net.bms.genera.blocks.BlockFaerieHome;
import net.bms.genera.blocks.BlockNightshadeCrop;
import net.bms.genera.blocks.BlockWhiteMushroom;
import net.bms.genera.lib.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraBlocks {

    public static Block BlockNightshadeCrop;
    public static Block BlockFaerieHome;
    public static Block BlockWhiteMushroom;
    public static Block BlockBurdockCrop;

    public static void init(RegistryEvent.Register<Block> event) {
        BlockNightshadeCrop = new BlockNightshadeCrop();
        BlockFaerieHome =  new BlockFaerieHome();
        BlockWhiteMushroom = new BlockWhiteMushroom();
        BlockBurdockCrop = new BlockBurdockCrop();

        event.getRegistry().register(GeneraBlocks.BlockFaerieHome);
        event.getRegistry().register(GeneraBlocks.BlockNightshadeCrop);
        event.getRegistry().register(GeneraBlocks.BlockWhiteMushroom);
        event.getRegistry().register(GeneraBlocks.BlockBurdockCrop);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderUtil.register(Item.getItemFromBlock(GeneraBlocks.BlockNightshadeCrop), 0, "nightshade");
        RenderUtil.register(Item.getItemFromBlock(GeneraBlocks.BlockFaerieHome), 0, "faerie_home");
        RenderUtil.register(Item.getItemFromBlock(GeneraBlocks.BlockWhiteMushroom), 0, "white_mushroom");
        RenderUtil.register(Item.getItemFromBlock(GeneraBlocks.BlockBurdockCrop), 0, "burdock");
    }
}
