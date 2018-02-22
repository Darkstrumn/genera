package net.bms.genera.capability;

import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public class FaerieInformation implements IFaerieInformation {
    private String type = "woodland";
    private float size;
    private double maxHealth;
    private int level;
    private int current_exp;

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public float getSize() {
        return this.size;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setCurrentExp(int exp) {
        this.current_exp = exp;
    }

    @Override
    public int getCurrentExp() {
        return this.current_exp;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY.getStorage().readNBT(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, this, null, nbt);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTBase base = FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY.getStorage().writeNBT(FaerieInformationProvider.FAERIE_INFORMATION_CAPABILITY, this, null);
        nbt.setTag("nbt_base", base);
        return nbt;
    }
}
