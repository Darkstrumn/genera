package net.bms.genera.init

import net.bms.genera.te.TileFaerieHome
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by ben on 4/3/17.
 */
object GeneraTileEntities {
    fun initTileEntities() {
        GameRegistry.registerTileEntity(TileFaerieHome::class.java, "faerie_enclosure")
    }
}
