/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.robots;

import net.minecraft.nbt.NBTTagCompound;

public abstract class ResourceId {

    protected ResourceId() {}

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.func_74778_a("resourceName", RobotManager.getResourceIdName(getClass()));
    }

    protected void readFromNBT(NBTTagCompound nbt) {}

    public static ResourceId load(NBTTagCompound nbt) {
        try {
            Class<?> cls;
            if (nbt.func_74764_b("class")) {
                // Migration support for 6.4.x
                cls = RobotManager.getResourceIdByLegacyClassName(nbt.func_74779_i("class"));
            } else {
                cls = RobotManager.getResourceIdByName(nbt.func_74779_i("resourceName"));
            }

            ResourceId id = (ResourceId) cls.newInstance();
            id.readFromNBT(nbt);

            return id;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
