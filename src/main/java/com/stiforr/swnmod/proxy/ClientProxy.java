package com.stiforr.swnmod.proxy;

import com.stiforr.swnmod.renderers.BlockRenderRegister;
import com.stiforr.swnmod.renderers.ItemRenderRegister;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{


    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
		BlockRenderRegister.preInit();
		BlockRenderRegister.registerBlockRenderer();
    }

    @Override
    public void init(FMLInitializationEvent e){
        super.init(e);

        ItemRenderRegister.registerItemRenderer();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }



}
