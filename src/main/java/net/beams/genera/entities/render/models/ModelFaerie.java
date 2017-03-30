package net.beams.genera.entities.render.models;

import net.beams.genera.entities.passive.EntityFaerie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * Created by ben on 3/25/17.
 */
public class ModelFaerie extends ModelBase {

    public ModelRenderer body;

    public ModelFaerie() {
        body = new ModelRenderer(this, 0, 0);
        body.addBox(0.2F, 0.2F, 0.2F, 4, 4, 4);
        body.setTextureSize(16, 16);
        body.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.rotateAngleX = 0F * (float) Math.PI / 180;
        body.rotateAngleY = 0F * (float) Math.PI / 180;
        body.rotateAngleZ = 0F * (float) Math.PI / 180;
    }

    public void render(Entity entity, float time, float swingSuppress,
                       float par4, float headAngleY, float headAngleX, float par7) {
        renderFaerie((EntityFaerie) entity, time, swingSuppress, par4, headAngleY, headAngleX, par7);
    }

    public void renderFaerie(EntityFaerie entity, float time, float swingSuppress,
                             float par4, float headAngleY, float headAngleX, float par7) {
        setRotationAngles(time, swingSuppress, par4, headAngleY, headAngleX, par7, entity);
        GL11.glPushMatrix();
        body.render(par7);
        GL11.glPopMatrix();
    }

}
