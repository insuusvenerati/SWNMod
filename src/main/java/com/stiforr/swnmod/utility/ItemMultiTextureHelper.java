package com.stiforr.swnmod.utility;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemMultiTextureHelper extends ItemMultiTexture {

    public ItemMultiTextureHelper(Block block, String[] names) {
        super(block, block, names);
    }
}
