package net.bms.genera.entities.render.models;

import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.entities.render.RenderFaerie;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public class ModelFaerieLightmap implements LayerRenderer<EntityFaerie> {
    private final RenderFaerie renderFaerie;
    public ModelFaerieLightmap(RenderFaerie renderFaerie) {
        this.renderFaerie = renderFaerie;
    }

    @Override
    public void doRenderLayer(EntityFaerie entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderFaerie.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.renderFaerie.setLightmap(entitylivingbaseIn);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
