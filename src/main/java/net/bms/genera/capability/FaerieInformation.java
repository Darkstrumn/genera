package net.bms.genera.capability;

import net.bms.genera.capability.interfaces.IFaerieInformation;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public class FaerieInformation implements IFaerieInformation {
    private int type = 0;
    private Float size = 0.1F;
    private Double maxHealth = 4.0D;

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public void setSize(Float size) {
        this.size = size;
    }

    @Override
    public Float getSize() {
        return this.size;
    }

    @Override
    public void setMaxHealth(Double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public Double getMaxHealth() {
        return this.maxHealth;
    }
}
