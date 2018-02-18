package net.bms.genera.blocks;

import net.bms.genera.Genera;
import net.bms.genera.init.GeneraItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAltar extends Block {
    public BlockAltar() {
        super(Material.ROCK);
        setUnlocalizedName("altar");
        setRegistryName("altar");
        setHardness(0.4F);
        setCreativeTab(Genera.TabGenera);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack handStack = player.getHeldItem(hand);
        if (handStack.getItem() == GeneraItems.ItemGlassJarFull &&
                handStack.getMetadata() == 1) {
            NBTTagCompound nbt = handStack.getTagCompound();
            if (nbt == null) return false;
            PotionType potion;
            player.getEntityData().setInteger("genera.sacrifices_made", player.getEntityData().getInteger("genera.sacrifices_made") + 1);
            switch (nbt.getInteger("type")) {
                case 0:
                    potion = PotionType.getPotionTypeForName("regeneration");
                    if (potion == null) return false;
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), potion));
                    return false;
                case 1:
                    potion = PotionType.getPotionTypeForName("night_vision");
                    if (potion == null) return false;
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), potion));
                    return false;
                case 2:
                    potion = PotionType.getPotionTypeForName("leaping");
                    if (potion == null) return false;
                    player.setHeldItem(hand, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), potion));
                    return false;
            }
        }
        return false;
    }
}
