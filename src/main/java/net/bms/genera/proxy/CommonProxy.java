package net.bms.genera.proxy;

import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.capability.storage.FaerieInformationStorage;
import net.bms.genera.event.EventHandler;
import net.bms.genera.init.GeneraEntities;
import net.bms.genera.init.GeneraTileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        CapabilityManager.INSTANCE.register(IFaerieInformation.class, new FaerieInformationStorage(), FaerieInformation.class);
        GeneraEntities.init();
        GeneraTileEntities.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}