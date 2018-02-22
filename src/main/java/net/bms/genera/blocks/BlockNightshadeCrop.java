package net.bms.genera.blocks;

import net.bms.genera.custom.Faerie;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.util.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

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
        IForgeRegistry<Faerie> registry = GameRegistry.findRegistry(Faerie.class);
        if (registry != null) {
            Faerie faerie = registry.getValue(new ResourceLocation(Constants.MODID, "woodland"));
            if (faerie != null)
                faerie.spawn(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
        }

    }
}