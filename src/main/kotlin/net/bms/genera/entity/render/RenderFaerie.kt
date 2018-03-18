package net.bms.genera.entity.render

import net.bms.genera.Genera
import net.bms.genera.custom.DefaultFaeries
import net.bms.genera.entity.EntityFaerie
import net.minecraft.client.model.ModelBase
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class RenderFaerie(manager: RenderManager, model: ModelBase): RenderLiving<EntityFaerie>(manager, model, 0.2F) {
    override fun getEntityTexture(entity: EntityFaerie?): ResourceLocation? {
        return when (entity?.faerieInformation?.type) {
            DefaultFaeries.underground?.type -> ResourceLocation(Genera.MODID, "textures/entity/faerie/${DefaultFaeries.underground?.bodyTexture}.png")
            DefaultFaeries.woodland?.type -> ResourceLocation(Genera.MODID, "textures/entity/faerie/${DefaultFaeries.woodland?.bodyTexture}.png")
            DefaultFaeries.highland?.type -> ResourceLocation(Genera.MODID, "textures/entity/faerie/${DefaultFaeries.highland?.bodyTexture}.png")
            else -> ResourceLocation(Genera.MODID, "textures/entity/faerie/${DefaultFaeries.woodland?.bodyTexture}.png")
        }
    }
}