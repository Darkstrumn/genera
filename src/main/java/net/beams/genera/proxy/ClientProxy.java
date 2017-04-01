package net.beams.genera.proxy;

import net.beams.genera.init.GeneraEntities;
import net.beams.genera.init.GeneraRenderers;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        GeneraRenderers.preInit();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent e) {
        super.init(e);
        GeneraRenderers.init();
        GeneraEntities.initModels();
    }
}