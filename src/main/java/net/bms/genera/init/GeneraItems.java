package net.bms.genera.init;

import net.bms.genera.items.*;
import net.bms.genera.util.RenderUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraItems {
    public static Item ItemSeedNightshade;
    public static Item ItemGlassJarFull;
    public static Item ItemCinnabar;
    public static Item ItemBurdockSeeds;
    public static Item ItemConnlaRing;

    public static void init(IForgeRegistry<Item> registry) {
        ItemSeedNightshade = new ItemSeedNightshade();
        ItemGlassJarFull = new ItemGlassJarFull();
        ItemCinnabar = new ItemCinnabar();
        ItemBurdockSeeds = new ItemBurdockSeeds();
        ItemConnlaRing = new ItemConnlaRing();

        registry.register(ItemGlassJarFull);
        registry.register(ItemSeedNightshade);
        registry.register(ItemCinnabar);
        registry.register(ItemBurdockSeeds);
        registry.register(ItemConnlaRing);

        registry.register(new ItemBlock(GeneraBlocks.BlockFaerieHome).setRegistryName("faerie_home"));
        registry.register(new ItemBlock(GeneraBlocks.BlockNightshadeCrop).setRegistryName("nightshade"));
        registry.register(new ItemBlock(GeneraBlocks.BlockWhiteMushroom).setRegistryName("white_mushroom"));
        registry.register(new ItemBlock(GeneraBlocks.BlockBurdockCrop).setRegistryName("burdock"));
        registry.register(new ItemBlock(GeneraBlocks.BlockAltar).setRegistryName("altar"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderUtil.register(ItemSeedNightshade,"nightshade_seed");
        RenderUtil.register(ItemGlassJarFull, "glass_jar_full");
        RenderUtil.register(ItemCinnabar, "cinnabar");
        RenderUtil.register(ItemBurdockSeeds, "burdock_seed");
        RenderUtil.register(ItemConnlaRing, "ring_of_connla");
    }
}
