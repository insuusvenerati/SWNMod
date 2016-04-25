package com.stiforr.swnmod.init;

import com.stiforr.swnmod.blocks.KyberOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    public static Block kyberOre;








    public static void init(){
        kyberOre = new KyberOre(Material.rock, ModItems.crystal, 0, 1, 2);
    }

    public static void registerBlocks(){
        GameRegistry.registerBlock(ModBlocks.kyberOre);
    }

}
