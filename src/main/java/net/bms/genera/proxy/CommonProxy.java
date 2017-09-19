package net.bms.genera.proxy;

import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.capability.storage.FaerieInformationStorage;
import net.bms.genera.event.EventHandler;
import net.bms.genera.init.GeneraEntities;
import net.bms.genera.init.GeneraTileEntities;
import net.bms.genera.lib.Constants;
import net.bms.genera.rituals.RitualRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        RegistryBuilder ritualRegistryBuilder = new RegistryBuilder();
        ritualRegistryBuilder.setMaxID(512)
                .setType(RitualRecipe.class)
                .setName(new ResourceLocation(Constants.MODID, "ritual_registry"));
        ritualRegistryBuilder.create();

        CapabilityManager.INSTANCE.register(IFaerieInformation.class, new FaerieInformationStorage(), FaerieInformation.class);
        GeneraEntities.init();
        GeneraTileEntities.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    // keep this for the future
    public void init(FMLInitializationEvent e) {
    }

    // ditto
    public void postInit(FMLPostInitializationEvent e) {

    }
}