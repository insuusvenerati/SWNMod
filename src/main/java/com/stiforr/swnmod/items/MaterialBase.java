package com.stiforr.swnmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MaterialBase extends Item {

    public MaterialBase() {
        super();
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxStackSize(64);
    }
}
