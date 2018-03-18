package net.bms.genera.proxy

import net.bms.genera.init.GeneraEntities
import net.bms.genera.init.GeneraItems
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ClientProxy : CommonProxy() {

    @SideOnly(Side.CLIENT)
    override fun preInit(e: FMLPreInitializationEvent) {
        super.preInit(e)
        GeneraEntities.initModels()
    }

    @SideOnly(Side.CLIENT)
    override fun init(e: FMLInitializationEvent) {
        super.init(e)
        registerItemTints()
    }

    @SideOnly(Side.CLIENT)
    fun registerItemTints() {
        val ic = Minecraft.getMinecraft().itemColors
        ic.registerItemColorHandler({ stack: ItemStack, tintIndex: Int ->
            if (tintIndex == 1) {
                val nbt = stack.tagCompound
                when {
                    nbt?.getString("type") == "woodland" -> return@registerItemColorHandler 0xBF1316
                    nbt?.getString("type") == "underground" -> return@registerItemColorHandler 0x7D90AD
                    nbt?.getString("type") == "highland" -> return@registerItemColorHandler 0x62D47E
                }
            }
            0xFFFFFF
        }, arrayOf(GeneraItems.ItemGlassJarFull))
    }
}
