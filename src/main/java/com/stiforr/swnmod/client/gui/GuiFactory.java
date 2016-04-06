package com.stiforr.swnmod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;


//TODO - What does this even do?
public class GuiFactory implements IModGuiFactory{

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }


    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return null;
    }


    //TODO - Do these even do anything? IModGuiFactory seems to think so.
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
