package net.bms.genera.proxy;

import net.bms.genera.event.EventHandler;
import net.bms.genera.init.GeneraEntities;
import net.bms.genera.init.GeneraTileEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        GeneraEntities.init();
        GeneraTileEntities.init();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}