package net.bms.genera.capability

import net.bms.genera.capability.interfaces.IFaerieInformation
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

/**
 * Created by benjaminsutter on 7/3/17.
 */
class FaerieInformationProvider : ICapabilitySerializable<NBTTagCompound> {
    private val cap = FAERIE_INFORMATION_CAPABILITY!!.defaultInstance

    override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
        return capability === FAERIE_INFORMATION_CAPABILITY
    }

    override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
        return if (capability === FAERIE_INFORMATION_CAPABILITY) FAERIE_INFORMATION_CAPABILITY!!.cast<T>(cap) else null
    }

    override fun serializeNBT(): NBTTagCompound {
        return cap!!.serializeNBT()
    }

    override fun deserializeNBT(nbt: NBTTagCompound) {
        cap!!.deserializeNBT(nbt)
    }

    companion object {
        @CapabilityInject(IFaerieInformation::class)
        internal var FAERIE_INFORMATION_CAPABILITY: Capability<IFaerieInformation>? = null
    }
}
