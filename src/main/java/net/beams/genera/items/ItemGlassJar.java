package net.beams.genera.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + (stack.getItemDamage() == 0 ? "empty" : "full");
    }

    // 0 = empty; 1 == full
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
    }
}
