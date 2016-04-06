package com.stiforr.swnmod.utility;


import com.stiforr.swnmod.handlers.ConfigurationHandler;
import com.stiforr.swnmod.reference.References;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

//TODO - Setup a debug configuration option like in AgriCraft
public abstract class LogHelper {
    public static void log(Level logLevel, Object object){
        FMLLog.log(References.NAME, logLevel, String.valueOf(object));
    }

    public static void debug(Object object){
        if(ConfigurationHandler.debugHelper){
            log(Level.INFO, "ModDebug " + object);
        }
    }

    public static void printStackTrace(Exception e) {
        if(ConfigurationHandler.debugHelper) {
            e.printStackTrace();
        }
    }

}
