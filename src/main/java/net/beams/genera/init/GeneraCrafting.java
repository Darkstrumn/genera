package net.beams.genera.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ben on 3/18/17.
 */
public class GeneraCrafting {
    public static void init() {
        GameRegistry.addRecipe(new ItemStack(GeneraBlocks.BlockFaerieHome),
                "WWW", "WGW", "WWW", 'W', Blocks.LOG, 'G', Blocks.GLASS_PANE);
        GameRegistry.addRecipe(new ItemStack(GeneraItems.ItemGlassJar, 1, 0),
                "   ", " C ", " G ", 'G', Blocks.GLASS, 'C', Items.CLAY_BALL);
    }
}
