package net.bms.genera.block

import net.bms.genera.Genera
import net.bms.genera.init.GeneraItems
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionType
import net.minecraft.potion.PotionUtils
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class BlockAltar : Block(Material.ROCK) {
    init {
        unlocalizedName = "altar"
        setRegistryName("altar")
        setHardness(0.4f)
        setCreativeTab(Genera.TabGenera)
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

    override fun onBlockActivated(world: World?, pos: BlockPos?, state: IBlockState?, player: EntityPlayer?, hand: EnumHand?,
                                  side: EnumFacing?, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val handStack = player!!.getHeldItem(hand!!)
        if (handStack.item === GeneraItems.ItemGlassJarFull) {
            val nbt = handStack.tagCompound ?: return false
            val potion: PotionType?
            player.entityData.setInteger("genera.sacrifices_made", player.entityData.getInteger("genera.sacrifices_made") + 1)
            when (nbt.getString("type")) {
                "woodland" -> {
                    potion = PotionType.getPotionTypeForName("regeneration")
                    if (potion == null) return false
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(ItemStack(Items.POTIONITEM), potion))
                    return true
                }
                "underground" -> {
                    potion = PotionType.getPotionTypeForName("night_vision")
                    if (potion == null) return false
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(ItemStack(Items.POTIONITEM), potion))
                    return true
                }
                "highland" -> {
                    potion = PotionType.getPotionTypeForName("leaping")
                    if (potion == null) return false
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(ItemStack(Items.POTIONITEM), potion))
                    return true
                }
            }
        }
        return false
    }
}
