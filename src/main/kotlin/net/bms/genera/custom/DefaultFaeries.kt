package net.bms.genera.custom

import net.minecraftforge.fml.common.registry.GameRegistry


object DefaultFaeries {
    @GameRegistry.ObjectHolder("genera:woodland")
    @JvmStatic
    val woodland: Faerie? = null

    @GameRegistry.ObjectHolder("genera:underground")
    @JvmStatic
    val underground: Faerie? = null

    @GameRegistry.ObjectHolder("genera:highland")
    @JvmStatic
    val highland: Faerie? = null
}