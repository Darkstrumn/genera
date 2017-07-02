package net.bms.genera.init;

import net.bms.genera.items.ItemGlassJar;
import net.bms.genera.items.ItemSeedNightshade;
import net.bms.genera.lib.RenderUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraItems {
    public static Item ItemSeedNightshade;
    public static Item ItemGlassJar;

    public static void init(RegistryEvent.Register<Item> event) {
        ItemSeedNightshade = new ItemSeedNightshade();
        ItemGlassJar = new ItemGlassJar();

        event.getRegistry().register(GeneraItems.ItemGlassJar);
        event.getRegistry().register(GeneraItems.ItemSeedNightshade);

        event.getRegistry().register(new ItemBlock(GeneraBlocks.BlockFaerieHome).setRegistryName("faerie_home"));
        event.getRegistry().register(new ItemBlock(GeneraBlocks.BlockNightshadeCrop).setRegistryName("nightshade"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        net.bms.genera.items.ItemGlassJar.initModel();
        RenderUtil.register(ItemSeedNightshade, 0, "nightshade_seed");
        RenderUtil.register(Item.getItemFromBlock(GeneraBlocks.BlockNightshadeCrop), 0, "nightshade");
        RenderUtil.register(ItemGlassJar, 0, "glass_jar_empty");
        RenderUtil.register(ItemGlassJar, 1, "glass_jar_full");
    }
}
