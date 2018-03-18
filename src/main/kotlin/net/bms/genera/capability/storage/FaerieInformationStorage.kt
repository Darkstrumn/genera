package net.bms.genera.capability.storage

import net.bms.genera.capability.interfaces.IFaerieInformation
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

/**
 * Created by benjaminsutter on 7/3/17.
 */
class FaerieInformationStorage : Capability.IStorage<IFaerieInformation> {
    override fun writeNBT(capability: Capability<IFaerieInformation>, instance: IFaerieInformation, side: EnumFacing?): NBTBase? {
        val comp = NBTTagCompound()
        comp.setString("type", instance.type)
        comp.setFloat("size", instance.size)
        comp.setDouble("max_health", instance.maxHealth)
        comp.setInteger("level", instance.level)
        comp.setInteger("current_exp", instance.currentExp)

        return comp
    }

    override fun readNBT(capability: Capability<IFaerieInformation>, instance: IFaerieInformation, side: EnumFacing?, nbt: NBTBase) {
        instance.type = (nbt as NBTTagCompound).getString("type")
        instance.size = nbt.getFloat("size")
        instance.maxHealth = nbt.getDouble("max_health")
        instance.level = nbt.getInteger("level")
        instance.currentExp = nbt.getInteger("current_exp")
    }
}
