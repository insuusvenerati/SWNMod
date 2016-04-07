package com.stiforr.swnmod.utility;

import com.stiforr.swnmod.init.ModItems;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.stiforr.swnmod.init.ModItems.*;

public class RegisterHelper {

    public static void init(){
        GameRegistry.registerItem(ModItems.orb);
        GameRegistry.registerItem(testSword);
        GameRegistry.registerItem(lightSaber_dark);
        GameRegistry.registerItem(fantSword);
        GameRegistry.registerItem(flameSword);
        GameRegistry.registerItem(blazeIngot);
    }
}
