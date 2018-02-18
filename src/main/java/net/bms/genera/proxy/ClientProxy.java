package net.bms.genera.proxy;

import net.bms.genera.init.GeneraEntities;
import net.bms.genera.init.GeneraItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        GeneraEntities.initModels();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void init(FMLInitializationEvent e) {
        super.init(e);
        registerItemTints();
    }

    @SideOnly(Side.CLIENT)
    public void registerItemTints(){
        ItemColors ic = Minecraft.getMinecraft().getItemColors();
        ic.registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 1) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt == null) return 0xFFFFFF;
                if (nbt.getInteger("type") == 0) return 0xBF1316;
                else if (nbt.getInteger("type") == 1) return 0x7D90AD;
                else if (nbt.getInteger("type") == 2) return 0x62D47E;
            }
            return 0xFFFFFF;
        }, GeneraItems.ItemGlassJarFull);
    }
}