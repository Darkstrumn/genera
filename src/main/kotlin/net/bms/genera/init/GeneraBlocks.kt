package net.bms.genera.init

import net.bms.genera.block.*
import net.bms.genera.util.RenderUtil
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

/**
 * Created by ben on 3/18/17.
 */
object GeneraBlocks {

    lateinit var BlockNightshadeCrop: Block
    lateinit var BlockFaerieHome: Block
    lateinit var BlockWhiteMushroom: Block
    lateinit var BlockBurdockCrop: Block
    lateinit var BlockAltar: Block

    fun initBlocks(registry: IForgeRegistry<Block>) {
        BlockNightshadeCrop = BlockNightshadeCrop()
        BlockFaerieHome = BlockFaerieHome()
        BlockWhiteMushroom = BlockWhiteMushroom()
        BlockBurdockCrop = BlockBurdockCrop()
        BlockAltar = BlockAltar()

        registry.register(BlockFaerieHome)
        registry.register(BlockNightshadeCrop)
        registry.register(BlockWhiteMushroom)
        registry.register(BlockBurdockCrop)
        registry.register(BlockAltar)
    }

    @SideOnly(Side.CLIENT)
    fun initModels() {
        RenderUtil.register(Item.getItemFromBlock(BlockNightshadeCrop), "nightshade")
        RenderUtil.register(Item.getItemFromBlock(BlockFaerieHome), "faerie_home")
        RenderUtil.register(Item.getItemFromBlock(BlockWhiteMushroom), "white_mushroom")
        RenderUtil.register(Item.getItemFromBlock(BlockBurdockCrop), "burdock")
        RenderUtil.register(Item.getItemFromBlock(BlockAltar), "altar")
    }
}
