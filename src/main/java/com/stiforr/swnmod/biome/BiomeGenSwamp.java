package com.stiforr.swnmod.biome;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenSwamp extends BiomeGenBase{
    public BiomeGenSwamp(int id) {
        super(id);


        this.theBiomeDecorator.treesPerChunk = 5;
        this.theBiomeDecorator.grassPerChunk = 2;
        this.setColor(522674);
        this.setTemperatureRainfall(0.8F, 0.9F);

        /*this.topBlock = Blocks.grass;*/


    }



}
