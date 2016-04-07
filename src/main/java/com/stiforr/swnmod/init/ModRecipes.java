package com.stiforr.swnmod.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){
        GameRegistry.addRecipe(new ItemStack(ModItems.lightSaber_dark, 1), new Object[]{" o ", " o ", " s ", 'o', ModItems.orb, 's', Items.stick});
    }

}
