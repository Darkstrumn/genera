package net.bms.genera.items;

import net.bms.genera.init.GeneraItems;
import net.bms.genera.lib.Constants;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @SideOnly(Side.CLIENT)
    public static void initModel() {
        ModelBakery.registerItemVariants(GeneraItems.ItemGlassJar, new ResourceLocation(Constants.MODID,
                "glass_jar_empty"), new ResourceLocation(Constants.MODID, "glass_jar_full"));
    }

    // 0 == empty; 1 == full
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }
}
