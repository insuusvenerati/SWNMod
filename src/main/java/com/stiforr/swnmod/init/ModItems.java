package com.stiforr.swnmod.init;

import com.stiforr.swnmod.items.ItemBase;
import com.stiforr.swnmod.items.MaterialBase;
import com.stiforr.swnmod.weapons.SwordBase;
import net.minecraft.item.Item;

public class ModItems {

    public static Item testSword;
    public static Item lightSaber_dark;
    public static Item fantSword;
    public static Item flameSword;
    public static Item blazeIngot;
    public static Item crystal;

    public static Item orb;


    public static void init(){
        orb = new ItemBase().setRegistryName("orb").setUnlocalizedName("orb");
        testSword = new SwordBase(Item.ToolMaterial.IRON).setRegistryName("testSword").setUnlocalizedName("testSword");
        lightSaber_dark = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("lightSaber_dark").setUnlocalizedName("lightSaber_dark");
        fantSword = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("fantSword").setUnlocalizedName("fantSword");
        flameSword = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("flameSword").setUnlocalizedName("flameSword");
        blazeIngot = new MaterialBase().setRegistryName("blazeIngot").setUnlocalizedName("blazeIngot");
        crystal = new MaterialBase().setRegistryName("crystal").setUnlocalizedName("crystal");
    }

}
