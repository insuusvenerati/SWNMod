package com.stiforr.swnmod.blocks;

import com.stiforr.swnmod.creativetab.CreativeTabSWN;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class BlockProperties extends Block{

    public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumType.class);
    public String[] SUBNAMES;


    public BlockProperties(String unlocalizedName, Material material, float hardness, float resistance, String[] subnames){
        super(material);
        SUBNAMES = subnames;
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(CreativeTabSWN.SWN_Tab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName("block_properties");
        // Set the default state of the block
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.COPPER));

    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, TYPE);
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta){
            case 0:
                return getDefaultState().withProperty(TYPE, EnumType.COPPER);
            case 1:
                return getDefaultState().withProperty(TYPE, EnumType.TIN);
            case 2:
                return getDefaultState().withProperty(TYPE, EnumType.LEAD);
            default:
                return getDefaultState().withProperty(TYPE, EnumType.COPPER);
        }

    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumType type = (EnumType) state.getValue(TYPE);
        return type.getID();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        list.add(new ItemStack(itemIn, 1, 0)); //Meta 0
        list.add(new ItemStack(itemIn, 1, 1)); //Meta 1
        list.add(new ItemStack(itemIn, 1, 2)); //Meta 2
    }



    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
    }
}
