package net.beams.genera.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraRenderers {
    public static void init() {
        register(GeneraItems.ItemSeedNightshade);
        register(Item.getItemFromBlock(GeneraBlocks.BlockNightshadeCrop));
    }

    public static void register(Item item) {
        final ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName().toString(),
                "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, location);
    }
}
