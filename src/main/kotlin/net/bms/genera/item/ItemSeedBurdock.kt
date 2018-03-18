package net.bms.genera.item

import net.bms.genera.Genera
import net.bms.genera.init.GeneraBlocks
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.EnumPlantType
import net.minecraftforge.common.IPlantable

class ItemBurdockSeeds: Item(), IPlantable {
    init {
        setRegistryName("burdock_seed")
        unlocalizedName = "burdock_seed"
        creativeTab = Genera.TabGenera
    }

    override fun getPlantType(world: IBlockAccess, pos: BlockPos): EnumPlantType {
        return EnumPlantType.Crop
    }

    override fun getPlant(world: IBlockAccess, pos: BlockPos): IBlockState {
        return GeneraBlocks.BlockBurdockCrop.defaultState
    }

    override fun onItemUse(player: EntityPlayer?, worldIn: World?, pos: BlockPos?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {
        val state = worldIn!!.getBlockState(pos!!)
        val stateUp = worldIn.getBlockState(pos.up())

        if (state.block.canSustainPlant(state, worldIn, pos, facing!!, this) && stateUp.block === Blocks.AIR) {
            worldIn.setBlockState(pos.up(), GeneraBlocks.BlockBurdockCrop.defaultState)
            player!!.getHeldItem(hand!!).count = player.getHeldItem(hand).count - 1
        }

        return EnumActionResult.PASS
    }
}

