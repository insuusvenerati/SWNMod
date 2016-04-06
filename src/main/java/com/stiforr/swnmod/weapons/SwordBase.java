package com.stiforr.swnmod.weapons;

import com.stiforr.swnmod.creativetab.CreativeTabSWN;
import com.stiforr.swnmod.reference.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class SwordBase extends ItemSword{

    public SwordBase(ToolMaterial material) {
        super(material);
        this.setCreativeTab(CreativeTabSWN.SWN_Tab);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(StatCollector.translateToLocal(References.MOD_ID + ".tooltip." + "testSword"));
    }
}
