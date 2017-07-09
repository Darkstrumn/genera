package net.bms.genera.items;

import net.bms.genera.capability.FaerieInformationProvider;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.lib.Constants;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (stack.getMetadata() == 1) {
            if (stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null) != null) {
                tooltip.add(String.format("Type: %d", stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).getType()));
                tooltip.add(String.format("Size: %f", stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).getSize()));
                tooltip.add(String.format("Maximum Health: %s", stack.getCapability(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, null).getMaxHealth().toString()));
            }
        }
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
