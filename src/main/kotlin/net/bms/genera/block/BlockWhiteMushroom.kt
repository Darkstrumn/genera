package net.bms.genera.block

import net.bms.genera.Genera
import net.bms.genera.custom.DefaultFaeries
import net.minecraft.block.BlockBush
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class BlockWhiteMushroom : BlockBush() {
    init {
        unlocalizedName = "white_mushroom"
        setRegistryName("white_mushroom")
        setCreativeTab(Genera.TabGenera)
        setHardness(0.0f)
        tickRandomly = true
    }
    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random?) {
        super.updateTick(worldIn, pos, state, rand!!)
        DefaultFaeries.underground?.spawn(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
    }
}
