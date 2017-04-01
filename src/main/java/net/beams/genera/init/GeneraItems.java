package net.beams.genera.init;

import net.beams.genera.items.ItemGlassJar;
import net.beams.genera.items.ItemSeedNightshade;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraItems {
    public static Item ItemSeedNightshade;
    public static Item ItemGlassJar;

    public static void init() {
        ItemSeedNightshade = new ItemSeedNightshade();
        ItemGlassJar = new ItemGlassJar();

        GameRegistry.register(ItemSeedNightshade);
        GameRegistry.register(ItemGlassJar);
    }
}
