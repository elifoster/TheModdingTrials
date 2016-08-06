package com.gamepedia.ftb.bunnytech.container;

import com.gamepedia.ftb.bunnytech.BunnyTech;
import com.gamepedia.ftb.bunnytech.tileentity.TileEntityBasicGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBasicGenerator extends GuiContainer {
    private static final ResourceLocation RL = new ResourceLocation(BunnyTech.MODID, "textures/gui/container/basic_generator.png");

    public GuiBasicGenerator(InventoryPlayer invPlayer, TileEntityBasicGenerator tile) {
        super(new ContainerBasicGenerator(invPlayer, tile));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(RL);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        GlStateManager.enableBlend();
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
        GlStateManager.disableBlend();
    }
}
