package net.beams.genera.init;

import net.beams.genera.Genera;
import net.beams.genera.entities.passive.EntityFaerie;
import net.beams.genera.entities.render.RenderFaerie;
import net.beams.genera.lib.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ben on 3/25/17.
 */
public class GeneraEntities {

    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Constants.MODID,"faerie"), EntityFaerie.class,
                "faerie", 1, Genera.instance, 64, 3, true,
                0xffff00, 0xe5d158);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFaerie.class, RenderFaerie.FACTORY);
    }
}
