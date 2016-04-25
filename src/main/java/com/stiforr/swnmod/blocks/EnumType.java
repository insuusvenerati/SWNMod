package com.stiforr.swnmod.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumType implements IStringSerializable{

    WHITE,
    BLACK;

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public int getID() {
        return ordinal();
    }


    @Override
    public String toString() {
        return getName();
    }

}
