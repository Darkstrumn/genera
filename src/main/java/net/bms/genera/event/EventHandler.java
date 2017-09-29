package net.bms.genera.event;

import net.bms.genera.init.GeneraBlocks;
import net.bms.genera.init.GeneraItems;
import net.bms.genera.lib.Constants;
import net.bms.genera.rituals.RitualRecipe;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        ItemStack guideBookStack = new ItemStack(Items.WRITTEN_BOOK, 1);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("title", new TextComponentTranslation("book.title.guide").getFormattedText());
        nbt.setString("author", new TextComponentTranslation("book.author.guide").getFormattedText());
        nbt.setInteger("generation", 2);
        NBTTagList nbtList = new NBTTagList();
        for (int index = 0; index <= 16; index++)
            nbtList.appendTag(new NBTTagString(String.format("{\"text\": \"%s\"}", new TextComponentTranslation(String.format("book.pages.guide.%d", index)).getFormattedText())));
        nbt.setTag("pages", nbtList);
        guideBookStack.setTagCompound(nbt);
        if (!event.player.getEntityData().getBoolean("genera.joined_before")) {
            event.player.getEntityData().setBoolean("genera.joined_before", true);
            event.player.getEntityData().setInteger("genera.sacrifices_made", 0);
            event.player.addItemStackToInventory(guideBookStack);
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        GeneraItems.init(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        GeneraBlocks.init(event);
    }

    @SubscribeEvent
    public void registerRituals(RegistryEvent.Register<RitualRecipe> event) {
        try {
            event.getRegistry().register(new RitualRecipe(new ResourceLocation(Constants.MODID, "cinnabar_ritual"))
                    .setRegistryName("cinnabar_ritual"));
            event.getRegistry().register(new RitualRecipe(new ResourceLocation(Constants.MODID, "white_mushroom_ritual"))
                    .setRegistryName("white_mushroom_ritual"));
            event.getRegistry().register(new RitualRecipe(new ResourceLocation(Constants.MODID, "burdock_seed_ritual"))
                    .setRegistryName("burdock_seed_ritual"));
            event.getRegistry().register(new RitualRecipe(new ResourceLocation(Constants.MODID, "iron_nugget_ritual"))
                    .setRegistryName("iron_nugget_ritual"));
            event.getRegistry().register(new RitualRecipe(new ResourceLocation(Constants.MODID, "gold_nugget_ritual"))
                    .setRegistryName("gold_nugget_ritual"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void playerSleep(PlayerSleepInBedEvent event) {
        if (event.getEntityPlayer().getEntityData().getInteger("genera.sacrifices_made") > 0)
            event.getEntityPlayer().getEntityData().setInteger("genera.sacrifices_made", 0);
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        GeneraItems.initModels();
        GeneraBlocks.initModels();
    }
}
