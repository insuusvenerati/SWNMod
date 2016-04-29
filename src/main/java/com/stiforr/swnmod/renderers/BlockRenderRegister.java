package com.stiforr.swnmod.renderers;

import com.stiforr.swnmod.blocks.EnumType;
import com.stiforr.swnmod.init.ModBlocks;
import com.stiforr.swnmod.reference.References;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlockRenderRegister {



	private static void regBlocks(Block block, int meta, String file, String variant)
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(References.MOD_ID + ":" + file, variant));
	}

    public static void registerBlockRenderer(){
		for (EnumType type : EnumType.values())
		{
			regBlocks(ModBlocks.block_properties, type.getID(), "block_properties", "type=" + type.getName());
		}

    }
    

}
