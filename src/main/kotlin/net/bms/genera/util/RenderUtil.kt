package net.bms.genera.util

import net.bms.genera.Genera
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader

object RenderUtil {
    fun register(item: Item, name: String) {
        val resource = ResourceLocation(Genera.MODID, name)
        val location = ModelResourceLocation(resource, "inventory")
        ModelLoader.setCustomModelResourceLocation(item, 0, location)
    }
}