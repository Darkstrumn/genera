package net.bms.genera.custom;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.bms.genera.entities.passive.EntityFaerie;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Faerie extends IForgeRegistryEntry.Impl<Faerie> {
    private JsonObject jsonObject;

    public Faerie(File file) throws IOException {
        FileReader reader = new FileReader(file);
        Gson jsonParser = new Gson();
        jsonObject = jsonParser.fromJson(reader, JsonObject.class);
    }

    public void spawn(World world, double x, double y, double z) {
        if (!world.isRemote) {
            double maxHealth = jsonObject.get("default_health").getAsDouble();
            String type = jsonObject.get("faerie_type").getAsString();
            float size = jsonObject.get("default_size").getAsFloat();
            int level = jsonObject.get("default_level").getAsInt();
            EntityFaerie faerie = new EntityFaerie(world, maxHealth, type, size, level);
            faerie.setPosition(x, y, z);
            world.spawnEntity(faerie);
        }
    }

    public String getBodyTexture() {
        return jsonObject.get("body_texture").getAsString();
    }

    public String getWingTopTexture() {
        return jsonObject.get("wing_top_texture").getAsString();
    }

    public String getWingBottomTexture() {
        return jsonObject.get("wing_bottom_texture").getAsString();
    }

    public String getName() {
        return jsonObject.get("faerie_type_local").getAsString();
    }
}
