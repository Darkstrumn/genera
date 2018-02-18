package net.bms.genera.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.bms.genera.Genera;
import net.bms.genera.util.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemConnlaRing extends Item implements IBauble {
    public ItemConnlaRing() {
        setCreativeTab(Genera.TabGenera);
        setMaxStackSize(1);
        setRegistryName(Constants.MODID, "ring_of_connla");
        setUnlocalizedName("ring_of_connla");
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
