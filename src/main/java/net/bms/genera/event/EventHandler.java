package net.bms.genera.event;

import net.bms.genera.init.GeneraBlocks;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.rituals.RitualRecipe;
import net.bms.genera.util.Constants;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.File;
import java.io.IOException;

/**
 * Created by ben on 3/19/17.
 */
@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.TALLGRASS) {
            float randomValue = event.getWorld().rand.nextFloat();
            if (randomValue >= 0.9) {
                event.getWorld().spawnEntity(new EntityItem(event.getWorld(),
                        event.getPos().getX(),
                        event.getPos().getY(),
                        event.getPos().getZ(),
                        new ItemStack(GeneraItems.ItemSeedNightshade, 1)));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void stitch(TextureStitchEvent.Pre event){
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/wood/body"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/wood/wing_bottom"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/wood/wing_top"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/cave/body"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/cave/wing_bottom"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/cave/wing_top"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/mountain/body"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/mountain/wing_bottom"));
        event.getMap().registerSprite(new ResourceLocation(Constants.MODID,"entity/faerie/mountain/wing_top"));
    }

    @SubscribeEvent
    public void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.getEntityData().getBoolean("genera.joined_before")) {
            event.player.getEntityData().setBoolean("genera.joined_before", true);
            event.player.getEntityData().setInteger("genera.sacrifices_made", 0);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerItems(RegistryEvent.Register<Item> event) {
        GeneraItems.init(event.getRegistry());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        GeneraBlocks.init(event.getRegistry());
    }

    @SubscribeEvent
    public void registerRituals(RegistryEvent.Register<RitualRecipe> event) {
        registerRitualsFromFile(this.getClass().getResource(String.format("/assets/%s/rituals", Constants.MODID)).getFile(), event.getRegistry());
        registerRitualsFromFile("./config/genera/rituals", event.getRegistry());
    }

    @SubscribeEvent
    public void playerSleep(PlayerSleepInBedEvent event) {
        if (event.getEntityPlayer().getEntityData().getInteger("genera.sacrifices_made") > 0)
            event.getEntityPlayer().getEntityData().setInteger("genera.sacrifices_made",
                    event.getEntityPlayer().getEntityData().getInteger("genera.sacrifices_made") - 1);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        GeneraItems.initModels();
        GeneraBlocks.initModels();
    }

    private void registerRitualsFromFile(String filename, IForgeRegistry<RitualRecipe> registry) {
        try {
            File ritualDir = new File(filename);
            if (!ritualDir.exists())
                ritualDir.mkdirs();
            if (ritualDir.isDirectory()) {
                File[] ritualFiles = ritualDir.listFiles();
                if (ritualFiles != null) {
                    for (File ritualFile : ritualFiles) {
                        registry.register(new RitualRecipe(ritualFile).setRegistryName(ritualFile.getName().substring(0, ritualFile.getName().length() - 5)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
