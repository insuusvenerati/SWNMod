/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License 1.0, or MMPL. Please check the contents
 * of the license located in http://www.mod-buildcraft.com/MMPL-1.0.txt */
package buildcraft.api.robots;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import net.minecraftforge.fluids.IFluidHandler;

import buildcraft.api.core.BCLog;
import buildcraft.api.core.EnumPipePart;
import buildcraft.api.statements.StatementSlot;
import buildcraft.api.transport.IInjectable;

public abstract class DockingStation {
    public EnumFacing side;
    public World world;

    private long robotTakingId = EntityRobotBase.NULL_ROBOT_ID;
    private EntityRobotBase robotTaking;

    private boolean linkIsMain = false;

    private BlockPos pos;

    public DockingStation(BlockPos iIndex, EnumFacing iSide) {
        pos = iIndex;
        side = iSide;
    }

    public DockingStation() {}

    public boolean isMainStation() {
        return linkIsMain;
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing side() {
        return side;
    }

    public EntityRobotBase robotTaking() {
        if (robotTakingId == EntityRobotBase.NULL_ROBOT_ID) {
            return null;
        } else if (robotTaking == null) {
            robotTaking = RobotManager.registryProvider.getRegistry(world).getLoadedRobot(robotTakingId);
        }

        return robotTaking;
    }

    public void invalidateRobotTakingEntity() {
        robotTaking = null;
    }

    public long linkedId() {
        return robotTakingId;
    }

    public boolean takeAsMain(EntityRobotBase robot) {
        if (robotTakingId == EntityRobotBase.NULL_ROBOT_ID) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            linkIsMain = true;
            robotTaking = robot;
            robotTakingId = robot.getRobotId();
            registry.registryMarkDirty();
            robot.setMainStation(this);
            registry.take(this, robot.getRobotId());

            return true;
        } else {
            return robotTakingId == robot.getRobotId();
        }
    }

    public boolean take(EntityRobotBase robot) {
        if (robotTaking == null) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            linkIsMain = false;
            robotTaking = robot;
            robotTakingId = robot.getRobotId();
            registry.registryMarkDirty();
            registry.take(this, robot.getRobotId());

            return true;
        } else {
            return robot.getRobotId() == robotTakingId;
        }
    }

    public void release(EntityRobotBase robot) {
        if (robotTaking == robot && !linkIsMain) {
            IRobotRegistry registry = RobotManager.registryProvider.getRegistry(world);
            unsafeRelease(robot);
            registry.registryMarkDirty();
            registry.release(this, robot.getRobotId());
        }
    }

    /** Same a release but doesn't clear the registry (presumably called from the registry). */
    public void unsafeRelease(EntityRobotBase robot) {
        if (robotTaking == robot) {
            linkIsMain = false;
            robotTaking = null;
            robotTakingId = EntityRobotBase.NULL_ROBOT_ID;
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.func_74783_a("pos", new int[] { getPos().func_177958_n(), getPos().func_177956_o(), getPos().func_177952_p() });
        nbt.func_74774_a("side", (byte) side.ordinal());
        nbt.func_74757_a("isMain", linkIsMain);
        nbt.func_74772_a("robotId", robotTakingId);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.func_74764_b("index")) {
            // For compatibility with older versions of minecraft and buildcraft
            NBTTagCompound indexNBT = nbt.func_74775_l("index");
            int x = indexNBT.func_74762_e("i");
            int y = indexNBT.func_74762_e("j");
            int z = indexNBT.func_74762_e("k");
            pos = new BlockPos(x, y, z);
        } else {
            int[] array = nbt.func_74759_k("pos");
            if (array.length == 3) {
                pos = new BlockPos(array[0], array[1], array[2]);
            } else if (array.length != 0) {
                BCLog.logger.warn("Found an integer array that wwas not the right length! (" + array + ")");
            } else {
                BCLog.logger.warn("Did not find any integer positions! This is a bug!");
            }
        }
        side = EnumFacing.values()[nbt.func_74771_c("side")];
        linkIsMain = nbt.func_74767_n("isMain");
        robotTakingId = nbt.func_74763_f("robotId");
    }

    public boolean isTaken() {
        return robotTakingId != EntityRobotBase.NULL_ROBOT_ID;
    }

    public long robotIdTaking() {
        return robotTakingId;
    }

    public BlockPos index() {
        return pos;
    }

    @Override
    public String toString() {
        return "{" + pos + ", " + side + " :" + robotTakingId + "}";
    }

    public boolean linkIsDocked() {
        if (robotTaking() != null) {
            return robotTaking().getDockingStation() == this;
        } else {
            return false;
        }
    }

    public boolean canRelease() {
        return !isMainStation() && !linkIsDocked();
    }

    public boolean isInitialized() {
        return true;
    }

    public abstract Iterable<StatementSlot> getActiveActions();

    public IInjectable getItemOutput() {
        return null;
    }

    public EnumPipePart getItemOutputSide() {
        return EnumPipePart.CENTER;
    }

    public IInventory getItemInput() {
        return null;
    }

    public EnumPipePart getItemInputSide() {
        return EnumPipePart.CENTER;
    }

    public IFluidHandler getFluidOutput() {
        return null;
    }

    public EnumPipePart getFluidOutputSide() {
        return EnumPipePart.CENTER;
    }

    public IFluidHandler getFluidInput() {
        return null;
    }

    public EnumPipePart getFluidInputSide() {
        return EnumPipePart.CENTER;
    }

    public boolean providesPower() {
        return false;
    }

    public IRequestProvider getRequestProvider() {
        return null;
    }

    public void onChunkUnload() {

    }
}
