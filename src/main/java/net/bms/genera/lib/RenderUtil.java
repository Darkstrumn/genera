package net.bms.genera.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by esutt_000 on 7/1/2017.
 */
public class RenderUtil {
    public static void register(Item item, int meta, String name) {
        final ResourceLocation resource = new ResourceLocation(Constants.MODID, name);
        final ModelResourceLocation location = new ModelResourceLocation(resource, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, location);
    }
}
