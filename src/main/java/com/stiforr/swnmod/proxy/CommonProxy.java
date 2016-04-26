package com.stiforr.swnmod.proxy;

import com.stiforr.swnmod.init.ModBlocks;
import com.stiforr.swnmod.init.ModItems;
import com.stiforr.swnmod.world.SWNModWorldGen;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy{

    public void preInit(FMLPreInitializationEvent e){
		ModItems.init();
		ModItems.registerItems();
		ModBlocks.init();
		ModBlocks.registerBlocks();
    }

    public void init(FMLInitializationEvent e){
        GameRegistry.registerWorldGenerator(new SWNModWorldGen(), 0);
    }

    public void postInit(FMLPostInitializationEvent e){

    }

    //TODO - Intialize the configuration
    /*@Override
    public void initConfiguration(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event);
    }*/

}
