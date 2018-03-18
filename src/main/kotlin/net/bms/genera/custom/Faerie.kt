package net.bms.genera.custom

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.bms.genera.entity.EntityFaerie
import net.minecraft.world.World
import net.minecraftforge.registries.IForgeRegistryEntry
import java.io.File
import java.io.FileReader
import java.io.IOException

class Faerie @Throws(IOException::class)
constructor(file: File): IForgeRegistryEntry.Impl<Faerie>() {
    private val jsonObject: JsonObject

    val bodyTexture: String
        get() = jsonObject.get("body_texture").asString

    val name: String
        get() = jsonObject.get("faerie_type_local").asString

    val type: String
        get() = jsonObject.get("faerie_type").asString

    init {
        val reader = FileReader(file)
        val jsonParser = Gson()
        jsonObject = jsonParser.fromJson(reader, JsonObject::class.java)
    }

    fun spawn(world: World, x: Double, y: Double, z: Double) {
        if (!world.isRemote) {
            val maxHealth = jsonObject.get("default_health").asDouble
            val type = jsonObject.get("faerie_type").asString
            val size = jsonObject.get("default_size").asFloat
            val level = jsonObject.get("default_level").asInt
            val faerie = EntityFaerie(world, maxHealth, type, size, level)
            faerie.setPosition(x, y, z)
            world.spawnEntity(faerie)
        }
    }
}
