/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.blueprints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import buildcraft.api.core.BCLog;

public class MappingRegistry {

    public HashMap<Block, Integer> blockToId = new HashMap<Block, Integer>();
    public ArrayList<Block> idToBlock = new ArrayList<Block>();

    public HashMap<Item, Integer> itemToId = new HashMap<Item, Integer>();
    public ArrayList<Item> idToItem = new ArrayList<Item>();

    public HashMap<Class<? extends Entity>, Integer> entityToId = new HashMap<Class<? extends Entity>, Integer>();
    public ArrayList<Class<? extends Entity>> idToEntity = new ArrayList<Class<? extends Entity>>();

    private void registerItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot register a null item!");
        if (!itemToId.containsKey(item)) {
            idToItem.add(item);
            itemToId.put(item, idToItem.size() - 1);
        }
    }

    private void registerBlock(Block block) {
        if (block == null) throw new IllegalArgumentException("Cannot register a null block!");
        if (!blockToId.containsKey(block)) {
            idToBlock.add(block);
            blockToId.put(block, idToBlock.size() - 1);
        }
    }

    private void registerEntity(Class<? extends Entity> entityClass) {
        if (entityClass == null) throw new IllegalArgumentException("Cannot register a null entityClass!");
        if (!entityToId.containsKey(entityClass)) {
            idToEntity.add(entityClass);
            entityToId.put(entityClass, idToEntity.size() - 1);
        }
    }

    public Item getItemForId(int id) throws MappingNotFoundException {
        if (id >= idToItem.size()) {
            throw new MappingNotFoundException("no item mapping at position " + id);
        }

        Item result = idToItem.get(id);

        if (result == null) {
            throw new MappingNotFoundException("no item mapping at position " + id);
        } else {
            return result;
        }
    }

    public int getIdForItem(Item item) {
        if (item == null) throw new NullPointerException("item");
        if (!itemToId.containsKey(item)) {
            registerItem(item);
        }

        return itemToId.get(item);
    }

    public int itemIdToRegistry(int id) {
        Item item = Item.func_150899_d(id);

        return getIdForItem(item);
    }

    public ResourceLocation itemIdToWorld(int id) throws MappingNotFoundException {
        Item item = getItemForId(id);

        return Item.field_150901_e.func_177774_c(item);
    }

    public Block getBlockForId(int id) throws MappingNotFoundException {
        if (id >= idToBlock.size()) {
            throw new MappingNotFoundException("no block mapping at position " + id);
        }

        Block result = idToBlock.get(id);

        if (result == null) {
            throw new MappingNotFoundException("no block mapping at position " + id);
        } else {
            return result;
        }
    }

    public int getIdForBlock(Block block) {
        if (!blockToId.containsKey(block)) {
            registerBlock(block);
        }

        return blockToId.get(block);
    }

    public int blockIdToRegistry(int id) {
        Block block = Block.func_149729_e(id);

        return getIdForBlock(block);
    }

    public ResourceLocation blockIdToWorld(int id) throws MappingNotFoundException {
        Block block = getBlockForId(id);

        return Block.field_149771_c.func_177774_c(block);
    }

    public Class<? extends Entity> getEntityForId(int id) throws MappingNotFoundException {
        if (id >= idToEntity.size()) {
            throw new MappingNotFoundException("no entity mapping at position " + id);
        }

        Class<? extends Entity> result = idToEntity.get(id);

        if (result == null) {
            throw new MappingNotFoundException("no entity mapping at position " + id);
        } else {
            return result;
        }
    }

    public int getIdForEntity(Class<? extends Entity> entity) {
        if (!entityToId.containsKey(entity)) {
            registerEntity(entity);
        }

        return entityToId.get(entity);
    }

    /** Relocates a stack nbt from the registry referential to the world referential. */
    public void stackToWorld(NBTTagCompound nbt) throws MappingNotFoundException {
        // 1.7.10 back-compat
        if (nbt.func_150297_b("id", Constants.NBT.TAG_SHORT)) {
            Item item = getItemForId(nbt.func_74765_d("id"));
            nbt.func_74778_a("id", (Item.field_150901_e.func_177774_c(item).toString()));
        }
    }

    // versions before 1.8 saved stacks with an Item ID as a short
    private boolean isOldStackLayout(NBTTagCompound nbt) {
        return nbt.func_74764_b("id") && nbt.func_74764_b("Count") && nbt.func_74764_b("Damage") && nbt.func_74781_a("id") instanceof NBTTagShort && nbt.func_74781_a(
                "Count") instanceof NBTTagByte && nbt.func_74781_a("Damage") instanceof NBTTagShort;
    }

    // 1.7.10 Back compat
    public void scanAndTranslateStacksToWorld(NBTTagCompound nbt) throws MappingNotFoundException {
        // First, check if this nbt is itself a stack

        if (isOldStackLayout(nbt)) {
            stackToWorld(nbt);
        }

        // Then, look at the nbt compound contained in this nbt (even if it's a
        // stack) and checks for stacks in it.
        for (String key : (Collection<String>) nbt.func_150296_c()) {
            if (nbt.func_74781_a(key) instanceof NBTTagCompound) {
                try {
                    scanAndTranslateStacksToWorld(nbt.func_74775_l(key));
                } catch (MappingNotFoundException e) {
                    nbt.func_82580_o(key);
                }
            }

            if (nbt.func_74781_a(key) instanceof NBTTagList) {
                NBTTagList list = (NBTTagList) nbt.func_74781_a(key);

                if (list.func_150303_d() == Constants.NBT.TAG_COMPOUND) {
                    for (int i = list.func_74745_c() - 1; i >= 0; --i) {
                        try {
                            scanAndTranslateStacksToWorld(list.func_150305_b(i));
                        } catch (MappingNotFoundException e) {
                            list.func_74744_a(i);
                        }
                    }
                }
            }
        }
    }

    public void write(NBTTagCompound nbt) {
        NBTTagList blocksMapping = new NBTTagList();

        for (Block b : idToBlock) {
            NBTTagCompound sub = new NBTTagCompound();
            if (b != null) {
                Object obj = Block.field_149771_c.func_177774_c(b);
                if (obj == null) {
                    BCLog.logger.error("Block " + b.func_149739_a() + " (" + b.getClass().getName()
                        + ") does not have a registry name! This is a bug!");
                } else {
                    String name = obj.toString();
                    if (name == null || name.length() == 0) {
                        BCLog.logger.error("Block " + b.func_149739_a() + " (" + b.getClass().getName()
                            + ") has an empty registry name! This is a bug!");
                    } else {
                        sub.func_74778_a("name", name);
                    }
                }
            } else {
                throw new IllegalArgumentException("Found a null block!");
            }
            blocksMapping.func_74742_a(sub);
        }

        nbt.func_74782_a("blocksMapping", blocksMapping);

        NBTTagList itemsMapping = new NBTTagList();

        for (Item i : idToItem) {
            NBTTagCompound sub = new NBTTagCompound();
            if (i != null) {
                ResourceLocation obj = Item.field_150901_e.func_177774_c(i);
                if (obj == null) {
                    BCLog.logger.error("Item " + i.func_77658_a() + " (" + i.getClass().getName()
                        + ") does not have a registry name! This is a bug!");
                } else {
                    String name = obj.toString();
                    if (name == null || name.length() == 0) {
                        BCLog.logger.error("Item " + i.func_77658_a() + " (" + i.getClass().getName()
                            + ") has an empty registry name! This is a bug!");
                    } else {
                        sub.func_74778_a("name", name);
                    }
                }
            } else {
                throw new IllegalArgumentException("Found a null item!");
            }
            itemsMapping.func_74742_a(sub);
        }

        nbt.func_74782_a("itemsMapping", itemsMapping);

        NBTTagList entitiesMapping = new NBTTagList();

        for (Class<? extends Entity> e : idToEntity) {
            NBTTagCompound sub = new NBTTagCompound();
            sub.func_74778_a("name", e.getCanonicalName());
            entitiesMapping.func_74742_a(sub);
        }

        nbt.func_74782_a("entitiesMapping", entitiesMapping);

        // System.out.println("[W] idToItem size : " + idToItem.size());
        // for (Item i : idToItem) {
        // System.out.println("- " + (i != null ? i.toString() : "null"));
        // }
    }

    private Object getMissingMappingFromFML(boolean isBlock, String name, int i) {
        ResourceLocation location = new ResourceLocation(name);
        String modName = name.split(":")[0];
        if (Loader.isModLoaded(modName)) {
            try {
                FMLMissingMappingsEvent.MissingMapping mapping = new FMLMissingMappingsEvent.MissingMapping(isBlock ? GameRegistry.Type.BLOCK
                    : GameRegistry.Type.ITEM, location, i);
                ListMultimap<String, FMLMissingMappingsEvent.MissingMapping> missingMapping = ArrayListMultimap.create();
                missingMapping.put(modName, mapping);
                FMLMissingMappingsEvent event = new FMLMissingMappingsEvent(missingMapping);
                for (ModContainer container : Loader.instance().getModList()) {
                    if (container instanceof FMLModContainer) {
                        event.applyModContainer(container);
                        ((FMLModContainer) container).handleModStateEvent(event);
                        if (mapping.getAction() != FMLMissingMappingsEvent.Action.DEFAULT) {
                            break;
                        }
                    }
                }
                if (mapping.getAction() == FMLMissingMappingsEvent.Action.REMAP) {
                    return mapping.getTarget();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void read(NBTTagCompound nbt) {
        NBTTagList blocksMapping = nbt.func_150295_c("blocksMapping", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < blocksMapping.func_74745_c(); ++i) {
            NBTTagCompound sub = blocksMapping.func_150305_b(i);
            if (!sub.func_74764_b("name")) {
                // Keeping the order correct
                idToBlock.add(null);
                BCLog.logger.log(Level.WARN, "Can't load a block - corrupt blueprint!");
                continue;
            }
            String name = sub.func_74779_i("name");
            ResourceLocation location = new ResourceLocation(name);
            Block b = null;

            if (!Block.field_149771_c.func_148741_d(location) && name.contains(":")) {
                b = (Block) getMissingMappingFromFML(true, name, i);
                if (b != null) {
                    BCLog.logger.info("Remapped " + name + " to " + Block.field_149771_c.func_177774_c(b));
                }
            }

            if (b == null && Block.field_149771_c.func_148741_d(location)) {
                b = (Block) Block.field_149771_c.func_82594_a(location);
            }

            if (b != null) {
                registerBlock(b);
            } else {
                // Keeping the order correct
                idToBlock.add(null);
                BCLog.logger.log(Level.WARN, "Can't load block " + name);
            }
        }

        NBTTagList itemsMapping = nbt.func_150295_c("itemsMapping", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < itemsMapping.func_74745_c(); ++i) {
            NBTTagCompound sub = itemsMapping.func_150305_b(i);
            if (!sub.func_74764_b("name")) {
                // Keeping the order correct
                idToItem.add(null);
                BCLog.logger.log(Level.WARN, "Can't load an item - corrupt blueprint!");
                continue;
            }

            String name = sub.func_74779_i("name");
            ResourceLocation location = new ResourceLocation(name);
            Item item = null;

            if (!Item.field_150901_e.func_148741_d(location) && name.contains(":")) {
                item = (Item) getMissingMappingFromFML(false, name, i);
                if (item != null) {
                    BCLog.logger.info("Remapped " + name + " to " + Item.field_150901_e.func_177774_c(item));
                }
            }

            if (item == null && Item.field_150901_e.func_148741_d(location)) {
                item = (Item) Item.field_150901_e.func_82594_a(location);
            }

            if (item != null) {
                registerItem(item);
            } else {
                // Keeping the order correct
                idToItem.add(null);
                BCLog.logger.log(Level.WARN, "Can't load item " + name);
            }
        }

        NBTTagList entitiesMapping = nbt.func_150295_c("entitiesMapping", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < entitiesMapping.func_74745_c(); ++i) {
            NBTTagCompound sub = entitiesMapping.func_150305_b(i);
            String name = sub.func_74779_i("name");
            Class<? extends Entity> e = null;

            try {
                e = (Class<? extends Entity>) Class.forName(name);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (e != null) {
                registerEntity(e);
            } else {
                // Keeping the order correct
                idToEntity.add(null);
                BCLog.logger.log(Level.WARN, "Can't load entity " + name);
            }
        }

        // System.out.println("[R] idToItem size : " + idToItem.size());
        // for (Item i : idToItem) {
        // System.out.println("- " + (i != null ? i.toString() : "null"));
        // }
    }

    public void addToCrashReport(CrashReportCategory cat) {
        cat.func_71507_a("Item Map Count", itemToId.size());
        for (Entry<Item, Integer> e : itemToId.entrySet()) {
            cat.func_71507_a("  - ID " + e.getValue(), Item.field_150901_e.func_177774_c(e.getKey()));
        }

        cat.func_71507_a("Block Map Count", blockToId.size());
        for (Entry<Block, Integer> e : blockToId.entrySet()) {
            cat.func_71507_a("  - ID " + e.getValue(), Block.field_149771_c.func_177774_c(e.getKey()));
        }

        cat.func_71507_a("Entity Map Count", entityToId.size());
        for (Entry<Class<? extends Entity>, Integer> e : entityToId.entrySet()) {
            cat.func_71507_a("  - ID " + e.getValue(), e.getKey());
        }
    }
}
