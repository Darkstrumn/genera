package net.bms.genera.block

import net.minecraft.block.Block
import net.minecraft.block.BlockBush
import net.minecraft.block.IGrowable
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.IPlantable
import java.util.*

/**
 * Created by ben on 3/18/17.
 */
open class BlockGeneraCrop internal constructor() : BlockBush(), IGrowable {

    init {
        tickRandomly = true
        defaultState = this.blockState.baseState.withProperty(AGE, 0)
    }

    private fun grow(state: IBlockState, worldIn: World?, pos: BlockPos?) {
        var growStage = state.getValue(AGE) + 1
        if (growStage > 2)
            growStage = 2
        worldIn!!.setBlockState(pos!!, state.withProperty(AGE, growStage))
    }

    override fun onBlockActivated(worldIn: World?, pos: BlockPos?, state: IBlockState?, playerIn: EntityPlayer?, hand: EnumHand?, facing: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (playerIn!!.getHeldItem(hand!!).item === ItemStack(Items.DYE, 1, 15).item) {
            grow(state!!, worldIn, pos)
            playerIn!!.getHeldItem(hand!!).count -= 1
            return super.onBlockActivated(worldIn!!, pos!!, state, playerIn!!, hand!!, facing!!, hitX, hitY, hitZ)
        }
        return false
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, AGE)
    }

    override fun canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean {
        return state.block === Blocks.FARMLAND
    }

    override fun canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean {
        return state.getValue(AGE) < 2
    }

    override fun grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState) {
        grow(state, worldIn, pos)
    }

    override fun updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random?) {
        super.updateTick(worldIn, pos, state, rand!!)

        if (worldIn.getLightFromNeighbors(pos.up()) >= 1) {
            val i = state.getValue(AGE)

            if (i < 2) {
                val f = getGrowthChance(this, worldIn, pos)

                if (rand.nextInt((25.0f / f).toInt() + 1) == 0) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, i + 1), 2)
                }
            }
        }
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(AGE)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return this.defaultState.withProperty(AGE, meta)
    }

    companion object {

        internal val AGE: IProperty<Int> = PropertyInteger.create("age", 0, 2)

        private fun getGrowthChance(blockIn: Block, worldIn: World, pos: BlockPos): Float {
            var f = 1.0f
            val blockpos1 = pos.down()

            for (i in -1..1) {
                for (j in -1..1) {
                    var f1 = 0.0f
                    val iblockstate = worldIn.getBlockState(blockpos1.add(i, 0, j))

                    if (iblockstate.block.canSustainPlant(worldIn.getBlockState(blockpos1.add(i, 0, j)),
                                    worldIn, blockpos1.add(i, 0, j), EnumFacing.UP, blockIn as IPlantable)) {
                        f1 = 1.0f

                        if (iblockstate.block.isFertile(worldIn, blockpos1.add(i, 0, j))) {
                            f1 = 3.0f
                        }
                    }

                    if (i != 0 || j != 0) {
                        f1 /= 4.0f
                    }

                    f += f1
                }
            }

            val blockpos2 = pos.north()
            val blockpos3 = pos.south()
            val blockpos4 = pos.west()
            val blockpos5 = pos.east()
            val flag = blockIn === worldIn.getBlockState(blockpos4).block || blockIn === worldIn.getBlockState(blockpos5).block
            val flag1 = blockIn === worldIn.getBlockState(blockpos2).block || blockIn === worldIn.getBlockState(blockpos3).block

            if (flag && flag1) {
                f /= 2.0f
            } else {
                val flag2 = blockIn === worldIn.getBlockState(blockpos4.north()).block || blockIn === worldIn.getBlockState(blockpos5.north()).block || blockIn === worldIn.getBlockState(blockpos5.south()).block || blockIn === worldIn.getBlockState(blockpos4.south()).block

                if (flag2) {
                    f /= 2.0f
                }
            }

            return f
        }
    }
}
