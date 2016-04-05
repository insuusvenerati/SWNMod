package com.stiforr.swnmod;

import com.stiforr.swnmod.handlers.ConfigurationHandler;
import com.stiforr.swnmod.proxy.IProxy;
import com.stiforr.swnmod.reference.References;
import com.sun.org.apache.regexp.internal.RE;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, version = References.VERSION, name = References.NAME)
public class SWNMod {

    @Mod.Instance(References.MOD_ID)
    public static SWNMod instance;

    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY, modId = References.MOD_ID)
    public static IProxy proxy;


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event){

        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event){

    }



}
