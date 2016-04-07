package com.stiforr.swnmod.renderers;

import com.stiforr.swnmod.init.ModItems;
import com.stiforr.swnmod.reference.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public final class ItemRenderRegister {


    public static void registerItemRenderer(){
        reg(ModItems.testSword);
        reg(ModItems.lightSaber_dark);
        reg(ModItems.orb);

    }

    private static void reg(Item item){
        String modid = References.MOD_ID;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
