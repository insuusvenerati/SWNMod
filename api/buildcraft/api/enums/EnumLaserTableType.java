package buildcraft.api.enums;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import buildcraft.api.properties.BuildCraftProperties;

public enum EnumLaserTableType implements IStringSerializable {
    ASSEMBLY_TABLE,
    ADVANCED_CRAFTING_TABLE,
    INTEGRATION_TABLE,
    CHARGING_TABLE,
    PROGRAMMING_TABLE,
    STAMPING_TABLE;

    public static EnumLaserTableType getType(IBlockState state) {
        return (EnumLaserTableType) state.func_177229_b(BuildCraftProperties.LASER_TABLE_TYPE);
    }

    @Override
    public String func_176610_l() {
        return name();
    }
}
