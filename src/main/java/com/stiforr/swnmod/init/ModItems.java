package com.stiforr.swnmod.init;

import com.stiforr.swnmod.items.ItemBase;
import com.stiforr.swnmod.weapons.SwordBase;
import net.minecraft.item.Item;

public class ModItems {

    public static Item testSword;
    public static Item lightSaber_dark;

    public static Item orb;

    static {
        testSword = new SwordBase(Item.ToolMaterial.IRON).setRegistryName("testSword").setUnlocalizedName("testSword");
        lightSaber_dark = new SwordBase(Item.ToolMaterial.EMERALD).setRegistryName("lightSaber_dark").setUnlocalizedName("lightSaber_dark");
    }


    public static void init(){
        orb = new ItemBase().setRegistryName("orb").setUnlocalizedName("orb");
    }

}
