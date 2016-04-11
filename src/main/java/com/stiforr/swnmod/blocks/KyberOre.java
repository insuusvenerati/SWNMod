package com.stiforr.swnmod.blocks;

import com.stiforr.swnmod.creativetab.CreativeTabSWN;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class KyberOre extends Block{

    private Item drop;
    private int meta;
    private int least_quantity;
    private int most_quantity;




    public KyberOre(Material blockMaterialIn, Item drop, int meta, int least_quantity, int most_quantity) {
        super(blockMaterialIn);

        this.drop = drop;
        this.meta = meta;
        this.least_quantity = least_quantity;
        this.most_quantity = most_quantity;

        this.setHarvestLevel("pickaxe", 1);
        this.setHardness(10.0f);
        this.setResistance(15.0f);
        this.setUnlocalizedName("kyberOre");
        this.setRegistryName("kyberOre");
        this.setCreativeTab(CreativeTabSWN.SWN_Tab);
        this.setLightLevel(0.50f);

    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.meta;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.drop;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        if (this.least_quantity >= this.most_quantity)
            return this.least_quantity;
        return this.least_quantity + random.nextInt(this.most_quantity - this.least_quantity + fortune + 1);
    }
}
