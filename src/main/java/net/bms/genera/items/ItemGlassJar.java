package net.bms.genera.items;

import net.bms.genera.capability.FaerieInformationProvider;
import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.lib.Constants;
import net.bms.genera.te.TileFaerieHome;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by ben on 3/30/17.
 */
public class ItemGlassJar extends Item {

    public ItemGlassJar() {
        setCreativeTab(CreativeTabs.TOOLS);
        setUnlocalizedName("glass_jar");
        setRegistryName("glass_jar");
        setMaxStackSize(1);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    @Nullable
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new FaerieInformationProvider();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getMetadata() == 1) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) return;
            int type = nbt.getInteger("type");
            String typeName = null;
            switch (type) {
                case 0:
                    typeName = "Woodland";
                    break;
                case 1:
                    typeName = "Underground";
                    break;
                case 2:
                    typeName = "Mountainous";
                    break;
            }
            tooltip.add(String.format("Type: %s", typeName));
            tooltip.add(String.format("Size: %f", nbt.getFloat("size")));
            tooltip.add(String.format("Maximum Health: %s", nbt.getDouble("max_health")));
            tooltip.add(String.format("Level: %d", nbt.getInteger("level")));
            tooltip.add(String.format("Experience Progress: %d", nbt.getInteger("current_exp")));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + (stack.getItemDamage() == 0 ? "empty" : "full");
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int metadata, boolean bool) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound nbt = itemstack.getTagCompound();
        if (nbt != null && nbt.getInteger("current_exp") >= 100) {
            nbt.setInteger("level", nbt.getInteger("level") + 1);
            nbt.setInteger("current_exp", 0);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModel() {
        ModelBakery.registerItemVariants(GeneraItems.ItemGlassJar, new ResourceLocation(Constants.MODID,
                "glass_jar_empty"), new ResourceLocation(Constants.MODID, "glass_jar_full"));
    }

    // 0 == empty; 1 == full
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (isInCreativeTab(tab)) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getMetadata() == 1 && !(worldIn.getTileEntity(pos) instanceof TileFaerieHome)) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null) return EnumActionResult.FAIL;
                EntityFaerie faerie = new EntityFaerie(worldIn, nbt.getDouble("max_health"), nbt.getInteger("type"), nbt.getFloat("size"), nbt.getInteger("level"), nbt.getInteger("current_exp"));
                faerie.setPosition((double) pos.getX(), (double) pos.up().getY(), (double) pos.getZ());
                worldIn.spawnEntity(faerie);
                player.setHeldItem(hand, new ItemStack(GeneraItems.ItemGlassJar, 1, 0));
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
