package net.bms.genera.init;

import net.bms.genera.Genera;
import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.entities.render.RenderFaerie;
import net.bms.genera.lib.Constants;
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
                "faerie", 1, Genera.instance, 64, 15, true,
                0xffff00, 0xe5d158);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFaerie.class, RenderFaerie::new);
    }
}
