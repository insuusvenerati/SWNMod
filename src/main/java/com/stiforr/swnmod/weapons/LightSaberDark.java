package com.stiforr.swnmod.weapons;

import com.stiforr.swnmod.creativetab.CreativeTabSWN;
import com.stiforr.swnmod.reference.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class LightSaberDark extends ItemSword{

    public LightSaberDark(ToolMaterial material) {
        super(material);
        setCreativeTab(CreativeTabSWN.SWN_Tab);
        setUnlocalizedName("lightSaber_dark");
        setRegistryName("lightSaber_dark");
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(EnumChatFormatting.GREEN.toString() + StatCollector.translateToLocal(References.MOD_ID + ".tooltip." + "lightSaber_dark"));
    }

}
