package com.stiforr.swnmod.init;

import com.stiforr.swnmod.weapons.SwordBase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {


    //public static Item testThing = new IngredientBase().setUnlocalizedName("test").setRegistryName("test");
    public static Item testSword = new SwordBase(Item.ToolMaterial.IRON).setRegistryName("testSword").setUnlocalizedName("testSword");





    @SuppressWarnings("deprecation")
	public static void init(){
        //GameRegistry.registerItem(testThing);
        GameRegistry.registerItem(testSword);


    }



}
