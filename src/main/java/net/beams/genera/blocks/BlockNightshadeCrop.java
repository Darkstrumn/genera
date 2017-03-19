package net.beams.genera.blocks;

import net.beams.genera.init.GeneraItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

/**
 * Created by ben on 3/18/17.
 */
public class BlockNightshadeCrop extends BlockGeneraCrop {

    public BlockNightshadeCrop() {
        setUnlocalizedName("nightshade");
        setRegistryName("nightshade");
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {

        if (state.getValue(AGE).intValue() == 2)
            return GeneraItems.ItemSeedNightshade;
        return null;
    }
}
