package net.bms.genera.init;

import net.bms.genera.blocks.*;
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
    public static Block BlockAltar;

    public static void init(RegistryEvent.Register<Block> event) {
        BlockNightshadeCrop = new BlockNightshadeCrop();
        BlockFaerieHome =  new BlockFaerieHome();
        BlockWhiteMushroom = new BlockWhiteMushroom();
        BlockBurdockCrop = new BlockBurdockCrop();
        BlockAltar = new BlockAltar();

        event.getRegistry().register(BlockFaerieHome);
        event.getRegistry().register(BlockNightshadeCrop);
        event.getRegistry().register(BlockWhiteMushroom);
        event.getRegistry().register(BlockBurdockCrop);
        event.getRegistry().register(BlockAltar);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderUtil.register(Item.getItemFromBlock(BlockNightshadeCrop), 0, "nightshade");
        RenderUtil.register(Item.getItemFromBlock(BlockFaerieHome), 0, "faerie_home");
        RenderUtil.register(Item.getItemFromBlock(BlockWhiteMushroom), 0, "white_mushroom");
        RenderUtil.register(Item.getItemFromBlock(BlockBurdockCrop), 0, "burdock");
        RenderUtil.register(Item.getItemFromBlock(BlockAltar), 0, "altar");
    }
}
