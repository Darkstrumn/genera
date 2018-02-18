package net.bms.genera.items;

import net.bms.genera.Genera;
import net.bms.genera.capability.FaerieInformationProvider;
import net.bms.genera.entities.passive.EntityFaerie;
import net.bms.genera.te.TileFaerieHome;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGlassJarFull extends Item {
    public ItemGlassJarFull() {
        setCreativeTab(Genera.TabGenera);
        setUnlocalizedName("glass_jar_full");
        setRegistryName("glass_jar_full");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) return;
        int type = nbt.getInteger("type");
        String typeName = null;
        switch (type) {
            case 0:
                typeName = I18n.format("item.glass_jar.description.type.woodland");
                break;
            case 1:
                typeName = I18n.format("item.glass_jar.description.type.cave");
                break;
            case 2:
                typeName = I18n.format("item.glass_jar.description.type.highlands");
                break;
        }
        tooltip.add(I18n.format("item.glass_jar.description.type.specifier") + ": " + typeName);
        tooltip.add(I18n.format("item.glass_jar.description.size.specifier") + ": " + nbt.getFloat("size"));
        tooltip.add(I18n.format("item.glass_jar.description.max_health.specifier") + ": " + nbt.getDouble("max_health"));
        tooltip.add(I18n.format("item.glass_jar.description.level.specifier") + ": " + nbt.getInteger("level"));
        tooltip.add(I18n.format("item.glass_jar.description.current_exp.specifier") + ": " + nbt.getInteger("current_exp"));
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

    @Override
    @Nullable
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FaerieInformationProvider();
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (!(worldIn.getTileEntity(pos) instanceof TileFaerieHome)) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null) return EnumActionResult.FAIL;
                EntityFaerie faerie = new EntityFaerie(worldIn, nbt.getDouble("max_health"), nbt.getInteger("type"), nbt.getFloat("size"), nbt.getInteger("level"), nbt.getInteger("current_exp"));
                faerie.setPosition((double) pos.getX(), (double) pos.up().getY(), (double) pos.getZ());
                worldIn.spawnEntity(faerie);
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
