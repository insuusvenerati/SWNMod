/** Copyright (c) 2011-2015, SpaceToad and the BuildCraft Team http://www.mod-buildcraft.com
 *
 * The BuildCraft API is distributed under the terms of the MIT License. Please check the contents of the license, which
 * should be located as "LICENSE.API" in the BuildCraft source code distribution. */
package buildcraft.api.statements;

import java.util.Objects;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StatementParameterItemStack implements IStatementParameter {

    protected ItemStack stack;

    @Override
    public TextureAtlasSprite getIcon() {
        return null;
    }

    @Override
    public ItemStack getItemStack() {
        return stack;
    }

    @Override
    public void onClick(IStatementContainer source, IStatement stmt, ItemStack stack, StatementMouseClick mouse) {
        if (stack != null) {
            this.stack = stack.func_77946_l();
            this.stack.field_77994_a = 1;
        } else {
            this.stack = null;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        if (stack != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            stack.func_77955_b(tagCompound);
            compound.func_74782_a("stack", tagCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        stack = ItemStack.func_77949_a(compound.func_74775_l("stack"));
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof StatementParameterItemStack) {
            StatementParameterItemStack param = (StatementParameterItemStack) object;

            return ItemStack.func_77989_b(stack, param.stack) && ItemStack.func_77970_a(stack, param.stack);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stack);
    }

    @Override
    public String getDescription() {
        if (stack != null) {
            return stack.func_82833_r();
        } else {
            return "";
        }
    }

    @Override
    public String getUniqueTag() {
        return "buildcraft:stack";
    }

    // @Override
    // public void registerIcons(TextureAtlasSpriteRegister iconRegister) {
    //
    // }

    @Override
    public IStatementParameter rotateLeft() {
        return this;
    }

    @Override
    public void registerIcons(TextureMap map) {
        
    }
}
