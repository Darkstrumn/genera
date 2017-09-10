package net.bms.genera.proxy;

import net.bms.genera.init.GeneraEntities;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        GeneraEntities.initModels();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }
}