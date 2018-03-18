package net.bms.genera.item.tab

import net.bms.genera.init.GeneraItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

class GeneraTab : CreativeTabs("genera") {

    override fun getTabIconItem(): ItemStack {
        return ItemStack(GeneraItems.ItemCinnabar)
    }
}
