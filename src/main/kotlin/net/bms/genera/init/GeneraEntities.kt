package net.bms.genera.init

import net.bms.genera.Genera
import net.bms.genera.entity.EntityFaerie
import net.bms.genera.entity.render.RenderFaerie
import net.bms.genera.entity.render.model.ModelFaerie
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.registry.EntityRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object GeneraEntities {
    fun initEntities() {
        EntityRegistry.registerModEntity(ResourceLocation(Genera.MODID, "faerie"), EntityFaerie::class.java,
                "faerie", 1, Genera.instance, 64, 15, true,
                0xffff00, 0xe5d158)
    }

    @SideOnly(Side.CLIENT)
    fun initModels() {
        RenderingRegistry.registerEntityRenderingHandler<EntityFaerie>(EntityFaerie::class.java, {manager: RenderManager ->
            RenderFaerie(manager, ModelFaerie())
        })
    }
}
