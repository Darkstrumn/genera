package net.beams.genera.proxy;

import net.beams.genera.init.GeneraRenderers;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
    @Override
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent e) {
        super.init(e);
        GeneraRenderers.init();
    }
}