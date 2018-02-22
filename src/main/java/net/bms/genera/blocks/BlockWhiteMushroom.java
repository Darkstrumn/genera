package net.bms.genera.blocks;

import net.bms.genera.Genera;
import net.bms.genera.custom.Faerie;
import net.bms.genera.util.Constants;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
        IForgeRegistry<Faerie> registry = GameRegistry.findRegistry(Faerie.class);
        if (registry != null) {
            Faerie faerie = registry.getValue(new ResourceLocation(Constants.MODID, "underground"));
            if (faerie != null)
                faerie.spawn(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
        }
    }

}
