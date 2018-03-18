package net.bms.genera.proxy

import net.bms.genera.Genera
import net.bms.genera.capability.FaerieInformation
import net.bms.genera.capability.interfaces.IFaerieInformation
import net.bms.genera.capability.storage.FaerieInformationStorage
import net.bms.genera.event.EventHandler
import net.bms.genera.init.GeneraEntities
import net.bms.genera.init.GeneraTileEntities
import net.bms.genera.packets.MessageGlassJarUpdateStats
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.relauncher.Side

open class CommonProxy {
    open fun preInit(e: FMLPreInitializationEvent) {
        CapabilityManager.INSTANCE.register<IFaerieInformation>(IFaerieInformation::class.java, FaerieInformationStorage(), { FaerieInformation() })
        GeneraEntities.initEntities()
        GeneraTileEntities.initTileEntities()
        MinecraftForge.EVENT_BUS.register(EventHandler())
    }

    open fun init(e: FMLInitializationEvent) {
        Genera.SIMPLEIMPL_INSTANCE.registerMessage<MessageGlassJarUpdateStats, IMessage>(MessageGlassJarUpdateStats.MessageHandlerGlassJarUpdateStats::class.java, MessageGlassJarUpdateStats::class.java, 0, Side.CLIENT)
    }
}