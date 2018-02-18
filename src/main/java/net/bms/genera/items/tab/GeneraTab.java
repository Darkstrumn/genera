package net.bms.genera.items.tab;

import net.bms.genera.init.GeneraItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GeneraTab extends CreativeTabs {
    public GeneraTab() {
        super("genera");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(GeneraItems.ItemCinnabar);
    }
}
