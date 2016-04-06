package com.stiforr.swnmod.handlers;

import com.stiforr.swnmod.reference.References;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;


public class ConfigurationHandler {

    /*Create static instance of Configuration Class*/
    private static Configuration configuration;

    /*
    * Set default values because I don't know how to booleans
    */
    public static boolean testValue = false;
    public static boolean debugHelper = false;


    /**
    *
    * Called during PreInit.
    * If Configuration doesn't exist, instantiate it and give it the param.
    *
    * @param configFile //TODO add here
    * */
    public static void init(File configFile){

        if (configuration == null){
            configuration = new Configuration(configFile);
        }

    }



    /*For the config screen*/
    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equalsIgnoreCase(References.MOD_ID)){
            loadConfiguration();

        }
    }


    public void loadConfiguration(){
        //TODO Need to actually use this somewhere
        debugHelper = configuration.getBoolean("debug", Configuration.CATEGORY_GENERAL, false, "Turn debug mode on");
//        testValue = configuration.getBoolean("configValue", Configuration.CATEGORY_GENERAL, false, "this is an example config value");

        /*If the configuration has changed then save*/
        if (configuration.hasChanged()){
            configuration.save();
        }
    }
}
