package net.bms.genera.capability.interfaces;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public interface IFaerieInformation {
    void setType(int type);
    int getType();

    void setSize(Float size);
    Float getSize();

    void setMaxHealth(Double maxHealth);
    Double getMaxHealth();

    void deserializeNBT(NBTTagCompound nbt);
    NBTTagCompound serializeNBT();
}
