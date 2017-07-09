package net.bms.genera.capability.storage;

import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public class FaerieInformationStorage implements Capability.IStorage<IFaerieInformation> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IFaerieInformation> capability, IFaerieInformation instance, EnumFacing side) {
        NBTTagCompound comp = new NBTTagCompound();
        comp.setInteger("type", instance.getType());
        comp.setFloat("size", instance.getSize());
        comp.setDouble("maxHealth", instance.getMaxHealth());

        return comp;
    }

    @Override
    public void readNBT(Capability<IFaerieInformation> capability, IFaerieInformation instance, EnumFacing side, NBTBase nbt) {
        instance.setType(((NBTTagCompound) nbt).getInteger("type"));
        instance.setSize(((NBTTagCompound) nbt).getFloat("size"));
        instance.setMaxHealth(((NBTTagCompound) nbt).getDouble("maxHealth"));
    }
}
