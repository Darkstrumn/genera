package net.beams.genera.init;

import net.beams.genera.lib.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraRenderers {
    public static void preInit() {
        ModelBakery.registerItemVariants(GeneraItems.ItemGlassJar, new ResourceLocation(Constants.MODID,
                "glass_jar_empty"), new ResourceLocation(Constants.MODID, "glass_jar_full"));
    }

    public static void init() {
        register(GeneraItems.ItemSeedNightshade, 0, "nightshade_seed");
        register(Item.getItemFromBlock(GeneraBlocks.BlockNightshadeCrop), 0, "nightshade");
        register(GeneraItems.ItemGlassJar, 0, "glass_jar_empty");
        register(GeneraItems.ItemGlassJar, 1, "glass_jar_full");
        register(Item.getItemFromBlock(GeneraBlocks.BlockFaerieHome), 0, "faerie_home");
    }

    public static void register(Item item, int meta, String name) {
        final ResourceLocation resource = new ResourceLocation(Constants.MODID, name);
        final ModelResourceLocation location = new ModelResourceLocation(resource, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, location);
    }
}
