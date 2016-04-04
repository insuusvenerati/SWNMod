/** Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.blueprints;

import java.util.*;

import com.google.gson.GsonBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.BlockFluidBase;

import buildcraft.api.core.BCLog;

import net.minecraft.util.EnumFacing.Axis;
public class SchematicBlock extends SchematicBlockBase {
    public IBlockState state = null;
    public BuildingPermission defaultPermission = BuildingPermission.ALL;

    /** This field contains requirements for a given block when stored in the blueprint. Modders can either rely on this
     * list or compute their own int Schematic. */
    public ItemStack[] storedRequirements = new ItemStack[0];

    private boolean doNotUse = false;

    @Override
    public void getRequirementsForPlacement(IBuilderContext context, List<ItemStack> requirements) {
        if (state != null) {
            if (storedRequirements.length != 0) {
                Collections.addAll(requirements, storedRequirements);
            } else {
                requirements.add(getItemStack(state));
            }
        }
    }

    @Override
    public boolean isAlreadyBuilt(IBuilderContext context, BlockPos pos) {
        IBlockState placed = context.world().func_180495_p(pos);
        if (state == placed) return true;
        if (state.func_177230_c() != placed.func_177230_c()) return false;
        // This fixes bugs with blocks like stairs that return extra properties that were not visible from the meta.
        if (state.func_177230_c().func_176201_c(state) == placed.func_177230_c().func_176201_c(placed)) return true;
        return false;
    }

    @Override
    public void placeInWorld(IBuilderContext context, BlockPos pos, List<ItemStack> stacks) {
        super.placeInWorld(context, pos, stacks);

        this.setBlockInWorld(context, pos);
    }

    @Override
    public void storeRequirements(IBuilderContext context, BlockPos pos) {
        super.storeRequirements(context, pos);

        if (state != null) {
            List<ItemStack> req = state.func_177230_c().getDrops(context.world(), pos, state, 0);

            if (req != null) {
                storedRequirements = new ItemStack[req.size()];
                req.toArray(storedRequirements);
            }
        }
    }

    @Override
    public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.writeSchematicToNBT(nbt, registry);

        writeBlockToNBT(nbt, registry);
        writeRequirementsToNBT(nbt, registry);
    }

    @Override
    public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.readSchematicFromNBT(nbt, registry);

        readBlockFromNBT(nbt, registry);
        if (!doNotUse()) {
            readRequirementsFromNBT(nbt, registry);
        }
    }

    /** Get a list of relative block coordinates which have to be built before this block can be placed. */
    public Set<BlockPos> getPrerequisiteBlocks(IBuilderContext context) {
        Set<BlockPos> indexes = new HashSet<BlockPos>();
        if (state.func_177230_c() instanceof BlockFalling) {
            indexes.add(new BlockPos(0, -1, 0));
        }
        return indexes;
    }

    @Override
    public BuildingStage getBuildStage() {
        if (state.func_177230_c() instanceof BlockFluidBase || state.func_177230_c() instanceof BlockLiquid) {
            return BuildingStage.EXPANDING;
        } else {
            return BuildingStage.STANDALONE;
        }
    }

    @Override
    public BuildingPermission getBuildingPermission() {
        return defaultPermission;
    }

    // Utility functions
    protected void setBlockInWorld(IBuilderContext context, BlockPos pos) {
        context.world().func_180501_a(pos, state, 3);
    }

    @Override
    public boolean doNotUse() {
        return doNotUse;
    }

    protected void readBlockFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        try {
            Block block = registry.getBlockForId(nbt.func_74762_e("blockId"));
            state = block.func_176203_a(nbt.func_74762_e("blockMeta"));
        } catch (MappingNotFoundException e) {
            BCLog.logger.info(e);
            doNotUse = true;
        }
    }

    protected void readRequirementsFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        if (nbt.func_74764_b("rq")) {
            NBTTagList rq = nbt.func_150295_c("rq", Constants.NBT.TAG_COMPOUND);

            ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();
            for (int i = 0; i < rq.func_74745_c(); ++i) {
                try {
                    NBTTagCompound sub = rq.func_150305_b(i);
                    registry.stackToWorld(sub);
                    rqs.add(ItemStack.func_77949_a(sub));
                } catch (MappingNotFoundException e) {
                    defaultPermission = BuildingPermission.CREATIVE_ONLY;
                } catch (Throwable t) {
                    t.printStackTrace();
                    defaultPermission = BuildingPermission.CREATIVE_ONLY;
                }
            }

            storedRequirements = rqs.toArray(new ItemStack[rqs.size()]);
        } else {
            storedRequirements = new ItemStack[0];
        }
    }

    protected void writeBlockToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        nbt.func_74768_a("blockId", registry.getIdForBlock(state.func_177230_c()));
        nbt.func_74768_a("blockMeta", state.func_177230_c().func_176201_c(state));
    }

    protected void writeRequirementsToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        if (storedRequirements.length > 0) {
            NBTTagList rq = new NBTTagList();

            for (ItemStack stack : storedRequirements) {
                if (stack == null || stack.func_77973_b() == null) throw new IllegalStateException("Found a null requirement! " + getClass());
                NBTTagCompound sub = new NBTTagCompound();
                stack.func_77955_b(sub);
                rq.func_74742_a(sub);
            }

            nbt.func_74782_a("rq", rq);
        }
    }

    protected ItemStack getItemStack(IBlockState state, int quantity) {
        return new ItemStack(state.func_177230_c(), quantity, state.func_177230_c().func_180651_a(state));
    }

    protected ItemStack getItemStack(IBlockState state) {
        return getItemStack(state, 1);
    }

    // Pretty much all blocks (that rotate) rotate this way now
    @Override
    public void rotateLeft(IBuilderContext context) {
        IProperty<EnumFacing> facingProp = getFacingProp();
        if (facingProp != null) {
            EnumFacing face = state.func_177229_b(facingProp);
            if (face.func_176740_k() == Axis.Y) return;
            state = state.func_177226_a(facingProp, face.func_176746_e());
        }
    }

    protected IProperty<EnumFacing> getFacingProp() {
        Collection<IProperty> props = state.func_177227_a();
        for (IProperty prop : props) {
            if ("facing".equals(prop.func_177701_a()) && state.func_177229_b(prop) instanceof EnumFacing) {
                return prop;
            }
        }
        return null;
    }
}
