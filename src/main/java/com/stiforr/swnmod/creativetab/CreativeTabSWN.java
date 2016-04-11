package com.stiforr.swnmod.creativetab;

import com.stiforr.swnmod.init.ModItems;
import com.stiforr.swnmod.reference.References;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabSWN {

    public static CreativeTabs SWN_Tab = new CreativeTabs(References.MOD_ID) {

        @Override
        public Item getTabIconItem() {
            return ModItems.crystal;
        }
    };

}
