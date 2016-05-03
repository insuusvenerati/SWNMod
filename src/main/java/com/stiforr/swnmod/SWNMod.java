package com.stiforr.swnmod;

import com.stiforr.swnmod.handlers.ConfigurationHandler;
import com.stiforr.swnmod.init.ModRecipes;
import com.stiforr.swnmod.proxy.CommonProxy;
import com.stiforr.swnmod.reference.References;
import com.stiforr.swnmod.utility.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, version = References.VERSION, name = References.NAME, guiFactory = References.GUI_FACTORY)
public class SWNMod {

    @Mod.Instance(References.MOD_ID)
    public static SWNMod instance;

    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY, modId = References.MOD_ID)
    public static CommonProxy proxy;


    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent e){
        proxy.preInit(e);

        ConfigurationHandler.init(e.getSuggestedConfigurationFile());

        LogHelper.debug("PreInit Success");
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent e){
        proxy.init(e);

        ModRecipes.init();

        LogHelper.debug("Init Success");

    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent e){
        proxy.postInit(e);


        LogHelper.debug("PostInit Success");
    }



}
