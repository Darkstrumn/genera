package net.bms.genera.block

import net.bms.genera.custom.DefaultFaeries
import net.bms.genera.init.GeneraItems
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class BlockBurdockCrop: BlockGeneraCrop() {
    init {
        unlocalizedName = "burdock"
        setRegistryName("burdock")
    }

    override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item? {
        return if (state!!.getValue(AGE) == 2) GeneraItems.ItemSeedBurdock else null
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random?) {
        super.updateTick(worldIn, pos, state, rand!!)
        if (state.getValue(AGE) > 1) {
            DefaultFaeries.highland?.spawn(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        }
    }
}