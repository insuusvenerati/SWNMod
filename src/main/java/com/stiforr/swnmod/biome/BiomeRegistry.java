package com.stiforr.swnmod.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class BiomeRegistry {

    public static void init(){
        biomeSwamp = new BiomeGenSwamp(137).setBiomeName("Yoda Swamp");
    }

    public static BiomeGenBase biomeSwamp;

    public static void register(){
        BiomeDictionary.registerBiomeType(biomeSwamp, BiomeDictionary.Type.SWAMP);
        BiomeManager.addSpawnBiome(biomeSwamp);
    }
}
