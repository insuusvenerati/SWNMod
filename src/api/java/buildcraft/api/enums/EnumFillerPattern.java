package buildcraft.api.enums;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumFillerPattern implements IStringSerializable {
    NONE,
    BOX,
    CLEAR,
    CYLINDER,
    FILL,
    FLATTEN,
    FRAME,
    HORIZON,
    PYRAMID,
    STAIRS;

    @Override
    public String toString() {
        return func_176610_l();
    }

    @Override
    public String func_176610_l() {
        return name().toLowerCase(Locale.ROOT);
    }
}
