package com.stiforr.swnmod.biome;

import biomesoplenty.api.biome.generation.BOPGeneratorBase;
import biomesoplenty.common.util.config.BOPConfig;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeGenSwamp extends BOPGeneratorBase{

    public BiomeGenSwamp(float amountPerChunk) {
        super(amountPerChunk);
    }

    @Override
    public BlockPos getScatterY(World world, Random random, int x, int z) {
        return null;
    }

    @Override
    public void configure(BOPConfig.IConfigObj conf) {

    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        return false;
    }
}
