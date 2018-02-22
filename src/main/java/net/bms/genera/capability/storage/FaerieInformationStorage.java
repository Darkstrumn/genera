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
        comp.setString("type", instance.getType());
        comp.setFloat("size", instance.getSize());
        comp.setDouble("max_health", instance.getMaxHealth());
        comp.setInteger("level", instance.getLevel());
        comp.setInteger("current_exp", instance.getCurrentExp());

        return comp;
    }

    @Override
    public void readNBT(Capability<IFaerieInformation> capability, IFaerieInformation instance, EnumFacing side, NBTBase nbt) {
        instance.setType(((NBTTagCompound) nbt).getString("type"));
        instance.setSize(((NBTTagCompound) nbt).getFloat("size"));
        instance.setMaxHealth(((NBTTagCompound) nbt).getDouble("max_health"));
        instance.setLevel(((NBTTagCompound) nbt).getInteger("level"));
        instance.setCurrentExp(((NBTTagCompound) nbt).getInteger("current_exp"));
    }
}
