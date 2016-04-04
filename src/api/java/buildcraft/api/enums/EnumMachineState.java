package buildcraft.api.enums;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

import buildcraft.api.properties.BuildCraftProperties;

public enum EnumMachineState implements IStringSerializable {
    OFF,
    ON,
    DONE;

    public static EnumMachineState getType(IBlockState state) {
        return (EnumMachineState) state.func_177229_b(BuildCraftProperties.MACHINE_STATE);
    }

    @Override
    public String func_176610_l() {
        return name();
    }
}
