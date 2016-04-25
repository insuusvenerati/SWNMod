package com.stiforr.swnmod.renderers;

import com.stiforr.swnmod.init.ModBlocks;
import com.stiforr.swnmod.reference.References;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockRenderRegister {

    public static void preInit(){
        ModelBakery.addVariantName(Item.getItemFromBlock(ModBlocks.block_properties), "swnmod:block_properties_black", "swnmod:block_properties_white");
    }

    public static void regBlocks(Block block, int meta, String file){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block ), meta, new ModelResourceLocation(References.MOD_ID + ":" + file, "inventory"));
    }

    public static void registerBlockRenderer(){
        regBlocks(ModBlocks.block_properties, 0, "block_properties_white");
        regBlocks(ModBlocks.block_properties, 1, "block_properties_black");

    }

}
