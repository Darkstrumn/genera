package net.bms.genera.block

import net.bms.genera.Genera
import net.bms.genera.init.GeneraItems
import net.bms.genera.te.TileFaerieHome
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.items.CapabilityItemHandler
import java.util.*

class BlockFaerieHome : Block(Material.WOOD) {
    init {
        setCreativeTab(Genera.TabGenera)
        unlocalizedName = "faerie_home"
        setRegistryName("faerie_home")
        defaultState = blockState.baseState.withProperty(FACING, EnumFacing.NORTH)
        setHardness(0.3f)
    }

    override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item? {
        return null
    }

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        if (worldIn.getTileEntity(pos) is TileFaerieHome) {
            val te = worldIn.getTileEntity(pos) as TileFaerieHome?
            if (te != null) {
                val itemStack = te.itemStackHandler.getStackInSlot(0)
                if (itemStack != ItemStack.EMPTY) {
                    val droppedJar = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
                    droppedJar.item = itemStack
                    worldIn.spawnEntity(droppedJar)
                }
            }
        }
        val droppedHome = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
        droppedHome.item = ItemStack(Item.getItemFromBlock(this), 1, 0)
        worldIn.spawnEntity(droppedHome)
        super.breakBlock(worldIn, pos, state)
    }

    override fun isOpaqueCube(state: IBlockState?): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun getBlockLayer(): BlockRenderLayer {
        return BlockRenderLayer.CUTOUT_MIPPED
    }

    override fun isFullCube(state: IBlockState?): Boolean {
        return false
    }

    override fun getBoundingBox(state: IBlockState?, source: IBlockAccess?, pos: BlockPos?): AxisAlignedBB {
        return AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.4, 1.0)
    }

    override fun getCollisionBoundingBox(blockState: IBlockState, worldIn: IBlockAccess, pos: BlockPos): AxisAlignedBB? {
        return this.getBoundingBox(blockState, worldIn, pos)
    }

    override fun createTileEntity(world: World, state: IBlockState): TileEntity? {
        return TileFaerieHome()
    }

    override fun onBlockPlacedBy(world: World?, pos: BlockPos?, state: IBlockState?, placer: EntityLivingBase?,
                                 stack: ItemStack?) {
        world!!.setBlockState(pos!!, state!!.withProperty(FACING,
                placer!!.horizontalFacing.opposite), 2)
    }

    override fun getStateFromMeta(meta: Int): IBlockState {
        return defaultState.withProperty(FACING, EnumFacing.getFront(meta and 7))
    }

    override fun getMetaFromState(state: IBlockState): Int {
        return state.getValue(FACING).index
    }

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?,
                                  side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (world!!.isRemote) {
            return true
        }
        val te = world.getTileEntity(pos!!) as? TileFaerieHome ?: return false

        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) && te.canInteractWith(player!!)) {
            val itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
            if (itemHandler != null) {
                val handStack = player.getHeldItem(hand)
                if (handStack.item === GeneraItems.ItemGlassJarFull && handStack.count == 1) {
                    val stack = itemHandler.getStackInSlot(0)
                    if (!stack.isEmpty) {
                        val comp = TextComponentTranslation("string.faerie_home.slot_taken")
                        comp.style.color = TextFormatting.RED
                        player.sendMessage(comp)
                    } else {
                        itemHandler.insertItem(0, handStack, false)
                        player.setHeldItem(hand, ItemStack.EMPTY)
                    }
                } else if (handStack.count > 1) {
                    val comp = TextComponentTranslation("string.player.too_many_jars")
                    comp.style.color = TextFormatting.RED
                    player.sendMessage(comp)
                } else {
                    if (player.isSneaking) {
                        if (handStack.isEmpty) {
                            val stack = itemHandler.extractItem(0, 1, false)
                            if (!stack.isEmpty) {
                                player.setHeldItem(hand, stack)
                            }
                        } else {
                            if (itemHandler.getStackInSlot(0).isEmpty) {
                                val comp = TextComponentTranslation("string.faerie_home.empty")
                                comp.style.color = TextFormatting.RED
                                player.sendMessage(comp)
                            } else {
                                val comp = TextComponentTranslation("string.player.inventory_full")
                                comp.style.color = TextFormatting.RED
                                player.sendMessage(comp)
                            }
                        }
                    } else if (!player.isSneaking) {
                        val comp = TextComponentTranslation("string.player.not_sneaking")
                        comp.style.color = TextFormatting.RED
                        player.sendMessage(comp)
                    }
                }
            }
        }

        return true
    }

    override fun createBlockState(): BlockStateContainer {
        return BlockStateContainer(this, FACING)
    }

    override fun hasTileEntity(state: IBlockState?): Boolean {
        return true
    }

    companion object {
        var FACING = PropertyDirection.create("facing")
    }
}
