package net.beams.genera.proxy;

import net.beams.genera.init.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        GeneraBlocks.init();
        GeneraItems.init();
        GeneraEntities.init();
        GeneraTileEntities.init();
    }

    public void init(FMLInitializationEvent e) {
        GeneraCrafting.init();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}