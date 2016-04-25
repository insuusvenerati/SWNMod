package com.stiforr.swnmod.init;

import com.stiforr.swnmod.items.ItemBase;
import com.stiforr.swnmod.items.MaterialBase;
import com.stiforr.swnmod.weapons.SwordBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

    public static Item lightSaber_dark;
    public static Item fantSword;
    public static Item flameSword;
    public static Item blazeIngot;
    public static Item crystal;

    public static Item orb;


    public static void init(){
        orb = new ItemBase().setRegistryName("orb").setUnlocalizedName("orb");



        lightSaber_dark = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("lightSaber_dark").setUnlocalizedName("lightSaber_dark");
        fantSword = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("fantSword").setUnlocalizedName("fantSword");
        flameSword = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("flameSword").setUnlocalizedName("flameSword");


        blazeIngot = new MaterialBase().setRegistryName("blazeIngot").setUnlocalizedName("blazeIngot");
        crystal = new MaterialBase().setRegistryName("crystal").setUnlocalizedName("crystal");
    }

    public static void registerItems(){
        GameRegistry.registerItem(orb);
        GameRegistry.registerItem(lightSaber_dark);
        GameRegistry.registerItem(fantSword);
        GameRegistry.registerItem(flameSword);
        GameRegistry.registerItem(blazeIngot);
        GameRegistry.registerItem(crystal);

    }

}
