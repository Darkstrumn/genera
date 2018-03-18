package net.bms.genera.capability.interfaces

import net.minecraft.nbt.NBTTagCompound

interface IFaerieInformation {
    var type: String
    var size: Float
    var maxHealth: Double
    var level: Int
    var currentExp: Int

    fun deserializeNBT(nbt: NBTTagCompound)
    fun serializeNBT(): NBTTagCompound
}
