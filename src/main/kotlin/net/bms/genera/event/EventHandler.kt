package net.bms.genera.event

import net.bms.genera.Genera
import net.bms.genera.custom.Faerie
import net.bms.genera.custom.Ritual
import net.bms.genera.init.GeneraBlocks
import net.bms.genera.init.GeneraItems
import net.minecraft.block.Block
import net.minecraft.entity.item.EntityItem
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryBuilder
import java.io.File
import java.io.IOException

@Mod.EventBusSubscriber
class EventHandler {
    @SubscribeEvent
    fun playerJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        if (!event.player.entityData.getBoolean("genera.joined_before")) {
            event.player.entityData.setBoolean("genera.joined_before", true)
            event.player.entityData.setInteger("genera.sacrifices_made", 0)
        }
    }

    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        GeneraItems.initItems(event.registry)
    }



    @Mod.EventHandler
    fun fingerprintViolation(event: FMLFingerprintViolationEvent) {
        println("Genera detected an invalid fingerprint. You cannot get support from the mod author for this file.")
    }

    @SubscribeEvent
    fun registerBlocks(event: RegistryEvent.Register<Block>) {
        GeneraBlocks.initBlocks(event.registry)
    }

    @SubscribeEvent
    fun registerRituals(event: RegistryEvent.Register<Ritual>) {
        registerRitualsFromFile(this.javaClass.getResource("/assets/${Genera.MODID}/rituals")?.path, event.registry)
        registerRitualsFromFile(Genera.configFolder.path + "/rituals", event.registry)
    }

    @SubscribeEvent
    fun playerSleep(event: PlayerSleepInBedEvent) {
        if (event.entityPlayer.entityData.getInteger("genera.sacrifices_made") > 0)
            event.entityPlayer.entityData.setInteger("genera.sacrifices_made",
                    event.entityPlayer.entityData.getInteger("genera.sacrifices_made") - 1)
    }

    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        GeneraItems.initModels()
        GeneraBlocks.initModels()
    }

    @SubscribeEvent
    fun registerFaeries(event: RegistryEvent.Register<Faerie>) {
        registerFaeriesFromFile(this.javaClass.getResource("/assets/${Genera.MODID}/faerie")?.path, event.registry)
        registerFaeriesFromFile(Genera.configFolder.path + "/faerie", event.registry)
    }

    private fun registerFaeriesFromFile(filename: String?, registry: IForgeRegistry<Faerie>) {
        if (filename == null) return
        try {
            val dir = File(filename)
            if (!dir.exists())
                dir.mkdirs()
            if (dir.isDirectory) {
                val files = dir.listFiles()
                if (files != null) {
                    for (file in files) {
                        registry.register(Faerie(file).setRegistryName(file.name.substring(0, file.name.length - 5)))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun registerRitualsFromFile(filename: String?, registry: IForgeRegistry<Ritual>) {
        if (filename == null) return
        try {
            val dir = File(filename)
            if (!dir.exists())
                dir.mkdirs()
            if (dir.isDirectory) {
                val files = dir.listFiles()
                if (files != null) {
                    for (file in files) {
                        if (!Genera.isBaublesPresent && file.name == "ring_of_connla_ritual.json")
                            continue
                        registry.register(Ritual(file).setRegistryName(file.name.substring(0, file.name.length - 5)))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        @SubscribeEvent
        fun onBlockBreak(event: BlockEvent.BreakEvent) {
            if (event.state.block === Blocks.TALLGRASS) {
                val randomValue = event.world.rand.nextFloat()
                if (randomValue >= 0.9) {
                    event.world.spawnEntity(EntityItem(event.world,
                            event.pos.x.toDouble(),
                            event.pos.y.toDouble(),
                            event.pos.z.toDouble(),
                            ItemStack(GeneraItems.ItemSeedNightshade, 1)))
                }
            }
        }

        @SubscribeEvent
        @JvmStatic
        fun registerRegistries(event: RegistryEvent.NewRegistry) {
            println("Registering Ritual Registry.")
            RegistryBuilder<Ritual>().setMaxID(1024)
                    .setType(Ritual::class.java)
                    .setName(ResourceLocation(Genera.MODID, "ritual_registry"))
                    .create()

            println("Registering Faerie Registry.")
            RegistryBuilder<Faerie>().setMaxID(512)
                    .setType(Faerie::class.java)
                    .setName(ResourceLocation(Genera.MODID, "faerie_registry"))
                    .create()
        }
    }
}
