package net.bms.genera

import net.bms.genera.Genera.MODID
import net.bms.genera.Genera.NAME
import net.bms.genera.Genera.VERSION
import net.bms.genera.item.tab.GeneraTab
import net.bms.genera.proxy.CommonProxy
import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.common.Loader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import org.apache.logging.log4j.Logger
import java.io.File

@Mod(modid = MODID, version = VERSION, name = NAME, acceptedMinecraftVersions = "[1.12,)",
        updateJSON = "https://raw.githubusercontent.com/BenjaminSutter/genera/master/update.json",
        dependencies = "after:baubles;required-after:forgelin;", modLanguage = "kotlin",
        modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter", certificateFingerprint = "@FINGERPRINT@")
object Genera {

    const val MODID = "genera"
    const val NAME = "Genera"
    const val VERSION = "@VERSION@"

    lateinit var TabGenera: CreativeTabs
    var isBaublesPresent = false

    @SidedProxy(clientSide = "net.bms.genera.proxy.ClientProxy", serverSide = "net.bms.genera.proxy.CommonProxy")
    private lateinit var proxy: CommonProxy

    val SIMPLEIMPL_INSTANCE: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID)

    @Mod.Instance
    lateinit var instance: Genera

    lateinit var logger: Logger

    lateinit var configFolder: File

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        logger = event.modLog
        configFolder = event.modConfigurationDirectory
        TabGenera = GeneraTab()
        proxy.preInit(event)
        isBaublesPresent = Loader.isModLoaded("baubles")
    }

    @Mod.EventHandler
    fun init(e: FMLInitializationEvent) {
        proxy.init(e)
    }
}