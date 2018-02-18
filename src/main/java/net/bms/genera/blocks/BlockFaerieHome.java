package net.bms.genera.blocks;

import net.bms.genera.Genera;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.te.TileFaerieHome;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by ben on 4/2/17.
 */
public class BlockFaerieHome extends Block {
    public static PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockFaerieHome() {
        super(Material.WOOD);
        setCreativeTab(Genera.TabGenera);
        setUnlocalizedName("faerie_home");
        setRegistryName("faerie_home");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setHardness(0.3F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos) instanceof TileFaerieHome) {
            TileFaerieHome te = (TileFaerieHome) worldIn.getTileEntity(pos);
            if (te != null) {
                ItemStack itemStack = te.itemStackHandler.getStackInSlot(0);
                if (itemStack != ItemStack.EMPTY) {
                    EntityItem droppedJar = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ());
                    droppedJar.setItem(itemStack);
                    worldIn.spawnEntity(droppedJar);
                }
            }
        }
        EntityItem droppedHome = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ());
        droppedHome.setItem(new ItemStack(Item.getItemFromBlock(this), 1, 0));
        worldIn.spawnEntity(droppedHome);
        super.breakBlock(worldIn, pos, state);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.4, 1.0);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return this.getBoundingBox(blockState, worldIn, pos);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
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
                ItemStack handStack = player.getHeldItem(hand);
                if (handStack.getItem() == GeneraItems.ItemGlassJarFull &&
                        handStack.getCount() == 1) {
                    ItemStack stack = itemHandler.getStackInSlot(0);
                    if (!stack.isEmpty()) {
                        TextComponentTranslation comp = new TextComponentTranslation("string.faerie_home.slot_taken");
                        comp.getStyle().setColor(TextFormatting.RED);
                        player.sendMessage(comp);
                    } else {
                        itemHandler.insertItem(0, handStack, false);
                        player.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                else if (handStack.getCount() > 1) {
                    TextComponentTranslation comp = new TextComponentTranslation("string.player.too_many_jars");
                    comp.getStyle().setColor(TextFormatting.RED);
                    player.sendMessage(comp);
                }
                else {
                    if (player.isSneaking()) {
                        if (handStack.isEmpty()) {
                            ItemStack stack = itemHandler.extractItem(0, 1, false);
                            if (!stack.isEmpty()) {
                                player.setHeldItem(hand, stack);
                            }
                        } else {
                            if (itemHandler.getStackInSlot(0).isEmpty()) {
                                TextComponentTranslation comp = new TextComponentTranslation("string.faerie_home.empty");
                                comp.getStyle().setColor(TextFormatting.RED);
                                player.sendMessage(comp);
                            }
                            else {
                                TextComponentTranslation comp = new TextComponentTranslation("string.player.inventory_full");
                                comp.getStyle().setColor(TextFormatting.RED);
                                player.sendMessage(comp);
                            }
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

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
}
