package net.bms.genera.custom;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ritual extends IForgeRegistryEntry.Impl<Ritual> {
    private JsonObject jsonObject;

    public Ritual(File file) throws IOException {
        FileReader reader = new FileReader(file);
        Gson jsonParser = new Gson();
        jsonObject = jsonParser.fromJson(reader, JsonObject.class);
    }

    public ItemStack getResult(NonNullList<ItemStack> itemStacks, int faerieLevel, String faerieType) {
        ItemStack returnValue = ItemStack.EMPTY;
        if (getInt("experience_cost") > getInt("faerie_level")) return returnValue;
        if (faerieLevel >= getInt("faerie_level") && faerieType.equals(getString("faerie_type"))) {
            JsonArray ingredientArray = jsonObject.getAsJsonArray("ingredients");
            if (ingredientArray == null) return returnValue;
            int ingredientsFound = 0;
            for (int index = 0; index < ingredientArray.size(); index++) {
                JsonElement ingredientElement = ingredientArray.get(index);
                for (ItemStack stack : itemStacks) {
                    if (stack.isEmpty()) continue;
                    ResourceLocation reloc = stack.getItem().getRegistryName();
                    if (reloc == null) continue;
                    if (reloc.equals(new ResourceLocation (ingredientElement.getAsJsonArray().get(0).getAsString())) &&
                            stack.getMetadata() == ingredientElement.getAsJsonArray().get(1).getAsInt()) {
                        ingredientsFound += 1;
                    }
                }
            }
            if (ingredientsFound >= ingredientArray.size()) {
                JsonArray resultArray = jsonObject.get("result").getAsJsonArray();
                ResourceLocation reloc = new ResourceLocation(resultArray.get(0).getAsString());
                int amount = resultArray.get(1).getAsInt();
                int meta = resultArray.get(2).getAsInt();
                IForgeRegistry<Item> registry = GameRegistry.findRegistry(Item.class);
                if (registry == null) return returnValue;
                Item item = registry.getValue(reloc);
                if (item == null) return returnValue;
                returnValue = new ItemStack(item, amount, meta);
            }
        }
        return returnValue;
    }

    public int getInt(String elementName) {
        return jsonObject.get(elementName).getAsInt();
    }

    public String getString(String elementName) {
        return jsonObject.get(elementName).getAsString();
    }
}