package com.stiforr.swnmod.init;

import com.stiforr.swnmod.blocks.KyberOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

    public static Block kyberOre;








    public static void init(){
        kyberOre = new KyberOre(Material.rock, ModItems.crystal, 0, 1, 2);
    }

}
