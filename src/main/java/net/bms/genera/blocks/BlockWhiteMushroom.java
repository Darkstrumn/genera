package net.bms.genera.blocks;

import net.bms.genera.Genera;
import net.bms.genera.entities.passive.EntityFaerie;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWhiteMushroom extends BlockBush {

    public BlockWhiteMushroom() {
        setUnlocalizedName("white_mushroom");
        setRegistryName("white_mushroom");
        setCreativeTab(Genera.TabGenera);
        setHardness(0.0F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote) {
            if (pos.getY() <= 32) {
                EntityFaerie faerie = new EntityFaerie(worldIn, 5.0D, 1, 0.3F, 0);
                faerie.setPosition((double) pos.getX(), (double) pos.up().getY(), (double) pos.getZ());
                worldIn.spawnEntity(faerie);
            }
        }
    }

}
