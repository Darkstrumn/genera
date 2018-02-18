package net.bms.genera.entities.render;

import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.util.Constants;
import net.bms.genera.util.ModelHandle;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import static net.minecraft.client.renderer.GlStateManager.*;

public class RenderFaerie extends Render<EntityFaerie> implements IRenderFactory<EntityFaerie> {

    private ModelHandle body = ModelHandle.of(new ResourceLocation(Constants.MODID, "entity/faerie/body"));
    private ModelHandle wing_r_top = ModelHandle.of(new ResourceLocation(Constants.MODID, "entity/faerie/wing_right"));
    private ModelHandle wing_r_bot = ModelHandle.of(new ResourceLocation(Constants.MODID, "entity/faerie/wing_right"));
    private ModelHandle wing_l_top = ModelHandle.of(new ResourceLocation(Constants.MODID, "entity/faerie/wing_left"));
    private ModelHandle wing_l_bot = ModelHandle.of(new ResourceLocation(Constants.MODID, "entity/faerie/wing_left"));

    public RenderFaerie(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.1F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFaerie entity) {
        return null;
    }

    @Override
    public void doRender(EntityFaerie entity, double x, double y, double z, float entityYaw, float partialTicks) {
        String wing_top = String.format("%s:entity/faerie/wood/wing_top", Constants.MODID);
        String wing_bottom = String.format("%s:entity/faerie/wood/wing_bottom", Constants.MODID);
        String body_texture = String.format("%s:entity/faerie/wood/body", Constants.MODID);

        switch (entity.faerieInformation.getType()) {
            case 0:
                wing_top = String.format("%s:entity/faerie/wood/wing_top", Constants.MODID);
                wing_bottom = String.format("%s:entity/faerie/wood/wing_bottom", Constants.MODID);
                body_texture = String.format("%s:entity/faerie/wood/body", Constants.MODID);
                break;
            case 1:
                wing_top = String.format("%s:entity/faerie/cave/wing_top", Constants.MODID);
                wing_bottom = String.format("%s:entity/faerie/cave/wing_bottom", Constants.MODID);
                body_texture = String.format("%s:entity/faerie/cave/body", Constants.MODID);
                break;
            case 2:
                wing_top = String.format("%s:entity/faerie/mountain/wing_top", Constants.MODID);
                wing_bottom = String.format("%s:entity/faerie/mountain/wing_bottom", Constants.MODID);
                body_texture = String.format("%s:entity/faerie/mountain/body", Constants.MODID);
                break;
        }

        if (!wing_r_top.getTextureReplacements().containsKey("0") || wing_r_top.getTextureReplacements().containsKey("0") && !wing_r_top.getTextureReplacements().get("0").equals(wing_top))
            wing_r_top = wing_r_top.replace("0", wing_top);
        if (!wing_r_bot.getTextureReplacements().containsKey("0") || wing_r_bot.getTextureReplacements().containsKey("0") && !wing_r_bot.getTextureReplacements().get("0").equals(wing_bottom))
            wing_r_bot = wing_r_bot.replace("0", wing_bottom);

        if (!wing_l_top.getTextureReplacements().containsKey("0") || wing_l_top.getTextureReplacements().containsKey("0") && !wing_l_top.getTextureReplacements().get("0").equals(wing_top))
            wing_l_top = wing_l_top.replace("0", wing_top);
        if (!wing_l_bot.getTextureReplacements().containsKey("0") || wing_l_bot.getTextureReplacements().containsKey("0") && !wing_l_bot.getTextureReplacements().get("0").equals(wing_bottom))
            wing_l_bot = wing_l_bot.replace("0", wing_bottom);

        if (!body.getTextureReplacements().containsKey("0") || body.getTextureReplacements().containsKey("0") && !body.getTextureReplacements().get("0").equals(body_texture))
            body = body.replace("0", body_texture);

        double rotation =
                Math.sin((double)entity.ticksExisted * 100d) * 20d; //fluid movement
                //Math.sin((double) entity.ticksExisted - 0.5 * Math.sin((double) entity.ticksExisted)) * 20d; //half movement, more insect like

        pushMatrix();

        translate(x, y, z);

        translate(-0.125, 0, -0.125);

        enableAlpha();
        enableBlend();

        ////////////////////////////////////////////
        pushMatrix();

        translate(0.125, 0, 0);
        rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        rotate((float) (renderManager.options.thirdPersonView == 2 ? -1 : 1) * renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        translate(-0.125, 0, 0);

        translate(0.0625f / 2f, 0, 0);
        scale(0.75, 0.75, 0.75);
        body.render();
        popMatrix();

        //////////////////////////////////////////////
        pushMatrix();
        translate(-0.0625, 0, 0);
        //set rotation
        translate(0.125, 0.125, 0);
        //rotate
        rotate(45f, 0, 1, 0);
        rotate((float) rotation, 0, 1, 0);
        rotate(-(float) -rotation, 0, 0, 1);
        //end rotation
        translate(-0.125, -0.125, 0);
        wing_l_bot.render();
        popMatrix();

        ////////////////////////////////////////////////
        pushMatrix();
        translate(-0.09, 0.07, 0);
        //set rotation
        translate(0.125, 0.0625, 0);
        //rotate
        rotate(20f, 0, 1, 0);
        rotate(-(float) rotation, 0, 1, 0);
        rotate((float) -rotation, 1, 0, 1);
        //end rotation
        translate(-0.125, -0.0625, 0);
        scale(1.2, 1.2, 1.2);
        wing_l_top.render();
        popMatrix();

        //////////////////////////////////
        pushMatrix();
        translate(0.1875, 0, 0);
        //Set rotation
        translate(0, 0.125, 0);
        //rotate
        rotate(-45f, 0, 1, 0);
        rotate(-(float) rotation, 0, 1, 0);
        rotate((float) -rotation, 0, 0, 1);
        //end rotation
        translate(0, -0.125, 0);
        wing_r_bot.render();
        popMatrix();

        //////////////////////////////////////
        pushMatrix();
        translate(0.1875, 0.07, 0);
        //Set rotation
        translate(0, 0.0625, 0);
        //rotate
        rotate(-20f, 0, 1, 0);
        rotate((float) rotation, 0, 1, 0);
        rotate(-(float) -rotation, -1, 0, 1);
        //end rotation
        translate(0, -0.0625, 0);
        scale(1.2, 1.2, 1.2);
        wing_r_top.render();
        popMatrix();


        disableBlend();
        disableAlpha();

        popMatrix();

    }

    @Override
    public Render createRenderFor(RenderManager manager) {
        return this;
    }
}