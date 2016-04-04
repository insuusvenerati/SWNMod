/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.blueprints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;

import net.minecraftforge.common.util.Constants;

public class SchematicEntity extends Schematic {
    public Class<? extends Entity> entity;

    /** This tree contains additional data to be stored in the blueprint. By default, it will be initialized from
     * Schematic.readFromWord with the standard readNBT function of the corresponding tile (if any) and will be loaded
     * from BptBlock.writeToWorld using the standard writeNBT function. */
    public NBTTagCompound entityNBT = new NBTTagCompound();

    /** This field contains requirements for a given block when stored in the blueprint. Modders can either rely on this
     * list or compute their own int Schematic. */
    public ItemStack[] storedRequirements = new ItemStack[0];
    public BuildingPermission defaultPermission = BuildingPermission.ALL;

    @Override
    public void getRequirementsForPlacement(IBuilderContext context, List<ItemStack> requirements) {
        Collections.addAll(requirements, storedRequirements);
    }

    public void writeToWorld(IBuilderContext context) {
        Entity e = EntityList.func_75615_a(entityNBT, context.world());
        context.world().func_72838_d(e);
    }

    public void readFromWorld(IBuilderContext context, Entity entity) {
        entity.func_70039_c(entityNBT);
    }

    @Override
    public void translateToBlueprint(Vec3 transform) {
        NBTTagList nbttaglist = entityNBT.func_150295_c("Pos", 6);
        Vec3 pos = new Vec3(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
        pos = pos.func_178787_e(transform);
        entityNBT.func_74782_a("Pos", this.newDoubleNBTList(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c));
    }

    @Override
    public void translateToWorld(Vec3 transform) {
        NBTTagList nbttaglist = entityNBT.func_150295_c("Pos", 6);
        Vec3 pos = new Vec3(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
        pos = pos.func_178787_e(transform);

        entityNBT.func_74782_a("Pos", this.newDoubleNBTList(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c));
    }

    @Override
    public void idsToBlueprint(MappingRegistry registry) {}

    @Override
    public void idsToWorld(MappingRegistry registry) {
        try {
            registry.scanAndTranslateStacksToWorld(entityNBT);
        } catch (MappingNotFoundException e) {
            entityNBT = new NBTTagCompound();
        }
    }

    @Override
    public void rotateLeft(IBuilderContext context) {
        NBTTagList nbttaglist = entityNBT.func_150295_c("Pos", 6);
        Vec3 pos = new Vec3(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));
        pos = context.rotatePositionLeft(pos);
        entityNBT.func_74782_a("Pos", this.newDoubleNBTList(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c));

        nbttaglist = entityNBT.func_150295_c("Rotation", 5);
        float yaw = nbttaglist.func_150308_e(0);
        yaw += 90;
        entityNBT.func_74782_a("Rotation", this.newFloatNBTList(yaw, nbttaglist.func_150308_e(1)));
    }

    @Override
    public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.writeSchematicToNBT(nbt, registry);

        nbt.func_74768_a("entityId", registry.getIdForEntity(entity));
        nbt.func_74782_a("entity", entityNBT);

        NBTTagList rq = new NBTTagList();

        for (ItemStack stack : storedRequirements) {
            NBTTagCompound sub = new NBTTagCompound();
            stack.func_77955_b(stack.func_77955_b(sub));
            sub.func_74768_a("id", registry.getIdForItem(stack.func_77973_b()));
            rq.func_74742_a(sub);
        }

        nbt.func_74782_a("rq", rq);
    }

    @Override
    public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.readSchematicFromNBT(nbt, registry);

        entityNBT = nbt.func_74775_l("entity");

        NBTTagList rq = nbt.func_150295_c("rq", Constants.NBT.TAG_COMPOUND);

        ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();

        for (int i = 0; i < rq.func_74745_c(); ++i) {
            try {
                NBTTagCompound sub = rq.func_150305_b(i);

                if (sub.func_74762_e("id") >= 0) {
                    // Maps the id in the blueprint to the id in the world
                    sub.func_74768_a("id", Item.field_150901_e.func_148757_b(registry.getItemForId(sub.func_74762_e("id"))));

                    rqs.add(ItemStack.func_77949_a(sub));
                } else {
                    defaultPermission = BuildingPermission.CREATIVE_ONLY;
                }
            } catch (Throwable t) {
                t.printStackTrace();
                defaultPermission = BuildingPermission.CREATIVE_ONLY;
            }
        }

        storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
    }

    protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble) {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;

        for (int j = 0; j < i; ++j) {
            double d1 = adouble[j];
            nbttaglist.func_74742_a(new NBTTagDouble(d1));
        }

        return nbttaglist;
    }

    protected NBTTagList newFloatNBTList(float... par1ArrayOfFloat) {
        NBTTagList nbttaglist = new NBTTagList();
        float[] afloat = par1ArrayOfFloat;
        int i = par1ArrayOfFloat.length;

        for (int j = 0; j < i; ++j) {
            float f1 = afloat[j];
            nbttaglist.func_74742_a(new NBTTagFloat(f1));
        }

        return nbttaglist;
    }

    public boolean isAlreadyBuilt(IBuilderContext context) {
        NBTTagList nbttaglist = entityNBT.func_150295_c("Pos", 6);
        Vec3 newPosition = new Vec3(nbttaglist.func_150309_d(0), nbttaglist.func_150309_d(1), nbttaglist.func_150309_d(2));

        for (Object o : context.world().field_72996_f) {
            Entity e = (Entity) o;

            Vec3 existingPositon = new Vec3(e.field_70165_t, e.field_70163_u, e.field_70161_v);

            if (existingPositon.func_72438_d(newPosition) <= 0.1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int buildTime() {
        return 5;
    }

    @Override
    public BuildingPermission getBuildingPermission() {
        return defaultPermission;
    }
}
