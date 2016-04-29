package com.stiforr.swnmod.init;

import com.stiforr.swnmod.blocks.BlockProperties;
import com.stiforr.swnmod.blocks.ItemBlockMeta;
import com.stiforr.swnmod.blocks.KyberOre;
import com.stiforr.swnmod.utility.ItemMultiTextureHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block kyberOre;
    public static BlockProperties block_properties;


    public static final String[] SubNames = new String[] {"copper", "tin", "lead"};






    public static void init(){
        kyberOre = new KyberOre(Material.rock, ModItems.crystal, 0, 1, 2);
		block_properties = new BlockProperties("block_properties", Material.rock, 1, 1, SubNames);


    }

    public static void registerBlocks(){
        GameRegistry.registerBlock(ModBlocks.kyberOre);

        GameRegistry.registerBlock(block_properties, ItemMultiTextureHelper.class, "block_properties", new Object[]{SubNames});

    }

}
