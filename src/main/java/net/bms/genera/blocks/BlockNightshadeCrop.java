package net.bms.genera.blocks;

import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.init.GeneraItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        if (state.getValue(AGE) == 2)
            return GeneraItems.ItemSeedNightshade;
        return null;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote) {
            if (state.getValue(AGE) == 2) {
                EntityFaerie faerie = new EntityFaerie(worldIn, 4.0D, 0, 0.1F, 1);
                faerie.setPosition((double) pos.getX(), (double) pos.up().getY(), (double) pos.getZ());
                worldIn.spawnEntity(faerie);
            }
        }
    }
}