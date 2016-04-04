/** Copyright (c) 2011-2014, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.blueprints;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

import buildcraft.api.core.JavaTools;

public class SchematicTile extends SchematicBlock {

    /** This tree contains additional data to be stored in the blueprint. By default, it will be initialized from
     * Schematic.readFromWord with the standard readNBT function of the corresponding tile (if any) and will be loaded
     * from BptBlock.writeToWorld using the standard writeNBT function. */
    public NBTTagCompound tileNBT = new NBTTagCompound();

    @Override
    public void idsToBlueprint(MappingRegistry registry) {}

    @Override
    public void idsToWorld(MappingRegistry registry) {
        try {
            registry.scanAndTranslateStacksToWorld(tileNBT);
        } catch (MappingNotFoundException e) {
            tileNBT = new NBTTagCompound();
        }
    }

    public void onNBTLoaded() {

    }

    /** Places the block in the world, at the location specified in the slot. */
    @Override
    public void placeInWorld(IBuilderContext context, BlockPos pos, List<ItemStack> stacks) {
        super.placeInWorld(context, pos, stacks);

        if (state.func_177230_c().hasTileEntity(state)) {
            tileNBT.func_74768_a("x", pos.func_177958_n());
            tileNBT.func_74768_a("y", pos.func_177956_o());
            tileNBT.func_74768_a("z", pos.func_177952_p());
            TileEntity tile = TileEntity.func_145827_c(tileNBT);
            tile.func_145834_a(context.world());
            context.world().func_175690_a(pos, tile);
        }
    }

    @Override
    public void initializeFromObjectAt(IBuilderContext context, BlockPos pos) {
        super.initializeFromObjectAt(context, pos);

        if (state.func_177230_c().hasTileEntity(state)) {
            TileEntity tile = context.world().func_175625_s(pos);

            if (tile != null) {
                tile.func_145841_b(tileNBT);
            }

            tileNBT = (NBTTagCompound) tileNBT.func_74737_b();
            onNBTLoaded();
        }
    }

    @Override
    public void storeRequirements(IBuilderContext context, BlockPos pos) {
        super.storeRequirements(context, pos);

        if (state.func_177230_c().hasTileEntity(state)) {
            TileEntity tile = context.world().func_175625_s(pos);

            if (tile instanceof IInventory) {
                IInventory inv = (IInventory) tile;

                ArrayList<ItemStack> rqs = new ArrayList<ItemStack>();

                for (int i = 0; i < inv.func_70302_i_(); ++i) {
                    if (inv.func_70301_a(i) != null) {
                        rqs.add(inv.func_70301_a(i));
                    }
                }

                storedRequirements = JavaTools.concat(storedRequirements, rqs.toArray(new ItemStack[rqs.size()]));
            }
        }
    }

    @Override
    public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.writeSchematicToNBT(nbt, registry);

        nbt.func_74782_a("blockCpt", tileNBT);
    }

    @Override
    public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
        super.readSchematicFromNBT(nbt, registry);

        tileNBT = nbt.func_74775_l("blockCpt");
        onNBTLoaded();
    }

    @Override
    public int buildTime() {
        return 5;
    }
}
