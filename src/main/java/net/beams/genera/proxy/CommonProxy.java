package net.beams.genera.proxy;

import net.beams.genera.init.GeneraBlocks;
import net.beams.genera.init.GeneraCrafting;
import net.beams.genera.init.GeneraItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        GeneraBlocks.init();
        GeneraItems.init();
        GeneraCrafting.init();
    }

    public void init(FMLInitializationEvent e) {

    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}