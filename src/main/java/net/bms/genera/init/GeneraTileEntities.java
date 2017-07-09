package net.bms.genera.init;

import net.bms.genera.te.TileFaerieHome;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ben on 4/3/17.
 */
public class
GeneraTileEntities {

    public static void init() {
        GameRegistry.registerTileEntity(TileFaerieHome.class, "faerie_home");
    }

}
