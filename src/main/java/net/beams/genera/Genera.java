package net.beams.genera;

import net.beams.genera.lib.Constants;
import net.beams.genera.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.NAME)
public class Genera
{
    @SidedProxy(clientSide = "net.beams.genera.proxy.ClientProxy", serverSide = "net.beams.genera.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Genera instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
