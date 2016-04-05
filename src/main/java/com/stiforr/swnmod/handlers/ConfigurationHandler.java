package com.stiforr.swnmod.handlers;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {
    public static void init(File configFile){

        Configuration configuration = new Configuration(configFile);

        try{
            configuration.load();
            boolean configValue = configuration.get(Configuration.CATEGORY_GENERAL, "configValue", true, "This is a test config value").getBoolean(true);
        }
        catch (Exception e){

        }

        finally{
            configuration.save();
        }

    }
}
