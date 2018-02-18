package net.bms.genera.items;

import net.bms.genera.Genera;
import net.bms.genera.init.GeneraBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemBurdockSeeds extends Item implements IPlantable {

    public ItemBurdockSeeds() {
        setRegistryName("burdock_seed");
        setUnlocalizedName("burdock_seed");
        setCreativeTab(Genera.TabGenera);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return GeneraBlocks.BlockBurdockCrop.getDefaultState();
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState state = worldIn.getBlockState(pos);
        IBlockState stateUp = worldIn.getBlockState(pos.up());

        if (state.getBlock().canSustainPlant(state, worldIn, pos, facing, this) && stateUp.getBlock() == Blocks.AIR) {
            worldIn.setBlockState(pos.up(), GeneraBlocks.BlockBurdockCrop.getDefaultState());
            player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount() - 1);
        }

        return EnumActionResult.PASS;
    }
}

