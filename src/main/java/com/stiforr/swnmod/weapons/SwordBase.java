package com.stiforr.swnmod.weapons;

import com.stiforr.swnmod.creativetab.CreativeTabSWN;
import com.stiforr.swnmod.reference.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class SwordBase extends ItemSword{
    /*public static String setInfo;
    public static String setColor;*/

    public SwordBase(ToolMaterial material) {
        super(material);
        this.setCreativeTab(CreativeTabSWN.SWN_Tab);
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(TextFormatting.GREEN.toString() + I18n.translateToLocal(References.MOD_ID + ".tooltip." + "lightSaber_dark"));
        tooltip.add(I18n.translateToLocal(References.MOD_ID + ".tooltip." + "test_sword"));

    }

    /*public static String setTooltip(){
        return setColor + setInfo;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced){
        tooltip.add(setTooltip());
    }*/
}
