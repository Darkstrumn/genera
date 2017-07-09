package net.bms.genera.capability;

import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by benjaminsutter on 7/3/17.
 */
public class FaerieInformationProvider implements ICapabilitySerializable<NBTBase> {

    @CapabilityInject(IFaerieInformation.class)
    public static final Capability<FaerieInformation> FAERIE_INFORMATION_CAPABILITY = null;
    private FaerieInformation instance = FAERIE_INFORMATION_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == FAERIE_INFORMATION_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == FAERIE_INFORMATION_CAPABILITY ? FAERIE_INFORMATION_CAPABILITY.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return FAERIE_INFORMATION_CAPABILITY.getStorage().writeNBT(FAERIE_INFORMATION_CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        FAERIE_INFORMATION_CAPABILITY.getStorage().readNBT(FAERIE_INFORMATION_CAPABILITY, instance, null, nbt);
    }
}
