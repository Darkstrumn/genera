package net.bms.genera.entity.render.model

import net.minecraft.client.model.ModelBase
import net.minecraft.client.model.ModelRenderer
import net.minecraft.entity.Entity

class ModelFaerie: ModelBase() {
    private val body: ModelRenderer = ModelRenderer(this, 0, 0)

    init {
        body.addBox(0F, 0F, 0F, 1, 1, 1)
        body.setTextureSize(16, 16)
    }

    override fun render(entityIn: Entity?, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        body.render(0.25F)
    }
}