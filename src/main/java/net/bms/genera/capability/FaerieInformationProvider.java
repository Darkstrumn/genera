package net.bms.genera.capability;

import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public class FaerieInformationProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(IFaerieInformation.class)
    static Capability<IFaerieInformation> FAERIE_INFORMATION_CAPABILITY = null;
    private final IFaerieInformation cap = FAERIE_INFORMATION_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == FAERIE_INFORMATION_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == FAERIE_INFORMATION_CAPABILITY ? FAERIE_INFORMATION_CAPABILITY.cast(cap) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return cap.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        cap.deserializeNBT(nbt);
    }
}
