package net.bms.genera.init

import net.bms.genera.Genera
import net.bms.genera.item.*
import net.bms.genera.util.RenderUtil
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.IForgeRegistry

/**
 * Created by ben on 3/18/17.
 */
object GeneraItems{
    lateinit var ItemSeedNightshade: Item
    lateinit var ItemGlassJarFull: Item
    lateinit var ItemCinnabar: Item
    lateinit var ItemSeedBurdock: Item
    lateinit var ItemConnlaRing: Item

    fun initItems(registry: IForgeRegistry<Item>) {
        ItemSeedNightshade = ItemSeedNightshade()
        ItemGlassJarFull = ItemGlassJarFull()
        ItemCinnabar = ItemCinnabar()
        ItemSeedBurdock = ItemBurdockSeeds()
        if (Genera.isBaublesPresent) {
            ItemConnlaRing = ItemConnlaRing()
        }

        registry.register(ItemGlassJarFull)
        registry.register(ItemSeedNightshade)
        registry.register(ItemCinnabar)
        registry.register(ItemSeedBurdock)
        if (Genera.isBaublesPresent) {
            registry.register(ItemConnlaRing)
        }

        registry.register(ItemBlock(GeneraBlocks.BlockFaerieHome).setRegistryName("faerie_home"))
        registry.register(ItemBlock(GeneraBlocks.BlockNightshadeCrop).setRegistryName("nightshade"))
        registry.register(ItemBlock(GeneraBlocks.BlockWhiteMushroom).setRegistryName("white_mushroom"))
        registry.register(ItemBlock(GeneraBlocks.BlockBurdockCrop).setRegistryName("burdock"))
        registry.register(ItemBlock(GeneraBlocks.BlockAltar).setRegistryName("altar"))
    }

    @SideOnly(Side.CLIENT)
    fun initModels() {
        RenderUtil.register(ItemSeedNightshade, "nightshade_seed")
        RenderUtil.register(ItemGlassJarFull, "glass_jar_full")
        RenderUtil.register(ItemCinnabar, "cinnabar")
        RenderUtil.register(ItemSeedBurdock, "burdock_seed")
        if (Genera.isBaublesPresent) {
            RenderUtil.register(ItemConnlaRing, "ring_of_connla")
        }
    }
}
