package net.bms.genera.capability

import net.bms.genera.capability.interfaces.IFaerieInformation
import net.minecraft.nbt.NBTTagCompound

class FaerieInformation: IFaerieInformation {
    override var currentExp: Int = 0
    override var type = "woodland"
    override var size: Float = 0F
    override var maxHealth: Double = 0.0
    override var level: Int = 0

    override fun deserializeNBT(nbt: NBTTagCompound) {
        FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY?.storage?.readNBT(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, this, null, nbt)
    }

    override fun serializeNBT(): NBTTagCompound {
        val nbt = NBTTagCompound()
        val base = FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY?.storage?.writeNBT(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, this, null)
        nbt.setTag("nbt_base", base!!)
        return nbt
    }
}
