package net.beams.genera.entities.render;

import net.beams.genera.entities.passive.EntityFaerie;
import net.beams.genera.entities.render.models.ModelFaerie;
import net.beams.genera.lib.Constants;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

/**
 * Created by ben on 3/25/17.
 */
public class RenderFaerie extends RenderLiving<EntityFaerie> {

    private ResourceLocation texture = new ResourceLocation(Constants.MODID, "textures/entity/faerie.png");
    public static final Factory FACTORY = new Factory();

    public RenderFaerie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelFaerie(), 0.2F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFaerie entity) {
        return texture;
    }

    public static class Factory implements IRenderFactory<EntityFaerie> {
        @Override
        public Render<? super EntityFaerie> createRenderFor(RenderManager manager)  {
            return new RenderFaerie(manager);
        }
    }
}
