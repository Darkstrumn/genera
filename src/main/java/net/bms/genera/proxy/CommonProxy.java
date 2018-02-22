package net.bms.genera.proxy;

import net.bms.genera.Genera;
import net.bms.genera.capability.FaerieInformation;
import net.bms.genera.capability.interfaces.IFaerieInformation;
import net.bms.genera.capability.storage.FaerieInformationStorage;
import net.bms.genera.custom.Faerie;
import net.bms.genera.custom.Ritual;
import net.bms.genera.event.EventHandler;
import net.bms.genera.init.GeneraEntities;
import net.bms.genera.init.GeneraTileEntities;
import net.bms.genera.packets.MessageGlassJarUpdateStats;
import net.bms.genera.util.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.RegistryBuilder;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        RegistryBuilder ritualRegistryBuilder = new RegistryBuilder();
        ritualRegistryBuilder.setMaxID(1024)
                .setType(Ritual.class)
                .setName(new ResourceLocation(Constants.MODID, "ritual_registry"));
        ritualRegistryBuilder.create();

        RegistryBuilder faerieRegistryBuilder = new RegistryBuilder();
        faerieRegistryBuilder.setMaxID(512)
                .setType(Faerie.class)
                .setName(new ResourceLocation(Constants.MODID, "faerie_registry"));
        faerieRegistryBuilder.create();

        CapabilityManager.INSTANCE.register(IFaerieInformation.class, new FaerieInformationStorage(), FaerieInformation::new);
        GeneraEntities.init();
        GeneraTileEntities.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init(FMLInitializationEvent e) {
        Genera.SIMPLEIMPL_INSTANCE.registerMessage(MessageGlassJarUpdateStats.MessageHandlerGlassJarUpdateStats.class, MessageGlassJarUpdateStats.class, 0, Side.CLIENT);
    }

    // keep this for the future
    public void postInit(FMLPostInitializationEvent e) {

    }
}