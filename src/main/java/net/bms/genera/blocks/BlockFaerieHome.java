package net.bms.genera.blocks;

import net.bms.genera.init.GeneraItems;
import net.bms.genera.te.TileFaerieHome;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Created by ben on 4/2/17.
 */
public class BlockFaerieHome extends Block implements ITileEntityProvider{
    public static PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockFaerieHome() {
        super(Material.IRON);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setUnlocalizedName("faerie_home");
        setRegistryName("faerie_home");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFaerieHome();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING,
                placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileFaerieHome)) {
            return false;
        }

        if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) && ((TileFaerieHome) te).canInteractWith(player)) {
            IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (itemHandler != null) {
                if (player.getHeldItem(hand).getItem() == new ItemStack(GeneraItems.ItemGlassJar, 1, 1).getItem()) {
                    if (itemHandler.getStackInSlot(1) != ItemStack.EMPTY) {
                        TextComponentTranslation comp = new TextComponentTranslation("string.faerie_home.slot_taken");
                        comp.getStyle().setColor(TextFormatting.RED);
                        player.sendMessage(comp);
                    } else if (itemHandler.getStackInSlot(1) == ItemStack.EMPTY) {
                        itemHandler.insertItem(1, player.getHeldItem(hand), false);
                        player.setHeldItem(hand, ItemStack.EMPTY);
                    }
                } else {
                    if (player.isSneaking()) {
                        if (player.getHeldItem(hand) == ItemStack.EMPTY) {
                            ItemStack stack = itemHandler.extractItem(1, 1, false);
                            if (stack != ItemStack.EMPTY) {
                                player.setHeldItem(hand, stack);
                            }
                        } else {
                            TextComponentTranslation comp = new TextComponentTranslation("string.player.inventory_full");
                            comp.getStyle().setColor(TextFormatting.RED);
                            player.sendMessage(comp);
                        }
                    } else if (!player.isSneaking()) {
                        TextComponentTranslation comp = new TextComponentTranslation("string.player.not_sneaking");
                        comp.getStyle().setColor(TextFormatting.RED);
                        player.sendMessage(comp);
                    }
                }
            }
        }

        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
