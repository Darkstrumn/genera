package net.bms.genera.rituals;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RitualRecipe extends IForgeRegistryEntry.Impl<RitualRecipe> {
    private JsonObject jsonObject;

    public RitualRecipe(File file) throws IOException {
        FileReader reader = new FileReader(file);
        Gson jsonParser = new Gson();
        jsonObject = jsonParser.fromJson(reader, JsonObject.class);
    }

    public int getLevelCost() {
        JsonElement element = jsonObject.get("experience_cost");
        return element.getAsInt();
    }

    public int getFaerieType() {
        JsonElement element = jsonObject.get("faerie_type");
        return element.getAsInt();
    }

    public int getFaerieLevel() {
        JsonElement element = jsonObject.get("faerie_level");
        return element.getAsInt();
    }

    public String[] getIngredient() {
        JsonElement element = jsonObject.get("ingredient");
        JsonArray array = element.getAsJsonArray();
        String[] returnValue = new String[2];
        for (int index = 0; index <= array.size() - 1; index++) {
            returnValue[index] = array.get(index).getAsString();
        }
        return returnValue;
    }

    public String[] getResult() {
        JsonElement element = jsonObject.get("result");
        JsonArray array = element.getAsJsonArray();
        String[] returnValue = new String[3];
        for (int index = 0; index <= array.size() - 1; index++) {
            returnValue[index] = array.get(index).getAsString();
        }
        return returnValue;
    }
}