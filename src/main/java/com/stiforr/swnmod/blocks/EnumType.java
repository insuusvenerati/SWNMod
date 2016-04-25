package com.stiforr.swnmod.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumType implements IStringSerializable{

    WHITE(0, "white"),
    BLACK(1, "black");

    private int ID;
    private String name;

    private EnumType(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }


    @Override
    public String toString() {
        return getName();
    }

}
