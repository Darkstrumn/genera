package net.bms.genera;

import net.bms.genera.items.tab.GeneraTab;
import net.bms.genera.proxy.CommonProxy;
import net.bms.genera.util.Constants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.MODID, version = Constants.VERSION, name = Constants.NAME, acceptedMinecraftVersions = "[1.12,)",
        updateJSON = "https://raw.githubusercontent.com/BenjaminSutter/genera/master/update.json", dependencies = "after:baubles;")
public class Genera
{
    public static CreativeTabs TabGenera;
    public static boolean isBaublesPresent = false;

    @SidedProxy(clientSide = "net.bms.genera.proxy.ClientProxy", serverSide = "net.bms.genera.proxy.ServerProxy")
    private static CommonProxy proxy;

    public static final SimpleNetworkWrapper SIMPLEIMPL_INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MODID);

    @Mod.Instance
    public static Genera instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        TabGenera = new GeneraTab();
        proxy.preInit(event);
        if (Loader.isModLoaded("baubles")) isBaublesPresent = true;
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
