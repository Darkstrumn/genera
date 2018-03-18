package net.bms.genera.item

import net.bms.genera.Genera
import net.minecraft.item.Item

class ItemCinnabar: Item() {
    init {
        creativeTab = Genera.TabGenera
        unlocalizedName = "cinnabar"
        setRegistryName("cinnabar")
    }
}
