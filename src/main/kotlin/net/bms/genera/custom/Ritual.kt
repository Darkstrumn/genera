package net.bms.genera.custom

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import java.io.File
import java.io.FileReader
import java.io.IOException

class Ritual @Throws(IOException::class)
constructor(file: File) : IForgeRegistryEntry.Impl<Ritual>() {
    private val jsonObject: JsonObject

    init {
        val reader = FileReader(file)
        val jsonParser = Gson()
        jsonObject = jsonParser.fromJson(reader, JsonObject::class.java)
    }

    fun getResult(itemStacks: NonNullList<ItemStack>, faerieLevel: Int, faerieType: String): ItemStack {
        var returnValue = ItemStack.EMPTY
        if (getInt("experience_cost") > getInt("faerie_level")) return returnValue
        if (faerieLevel >= getInt("faerie_level") && faerieType == getString("faerie_type")) {
            val ingredientArray = jsonObject.getAsJsonArray("ingredients") ?: return returnValue
            var ingredientsFound = 0
            for (index in 0 until ingredientArray.size()) {
                val ingredientElement = ingredientArray.get(index)
                for (stack in itemStacks) {
                    if (stack.isEmpty) continue
                    val reloc = stack.item.registryName ?: continue
                    if (reloc == ResourceLocation(ingredientElement.asJsonArray.get(0).asString) && stack.metadata == ingredientElement.asJsonArray.get(1).asInt) {
                        ingredientsFound += 1
                    }
                }
            }
            if (ingredientsFound >= ingredientArray.size()) {
                val resultArray = jsonObject.get("result").asJsonArray
                val reloc = ResourceLocation(resultArray.get(0).asString)
                val amount = resultArray.get(1).asInt
                val meta = resultArray.get(2).asInt
                val registry = GameRegistry.findRegistry(Item::class.java) ?: return returnValue
                val item = registry.getValue(reloc) ?: return returnValue
                returnValue = ItemStack(item, amount, meta)
            }
        }
        return returnValue
    }

    fun getInt(elementName: String): Int {
        return jsonObject.get(elementName).asInt
    }

    fun getString(elementName: String): String {
        return jsonObject.get(elementName).asString
    }
}