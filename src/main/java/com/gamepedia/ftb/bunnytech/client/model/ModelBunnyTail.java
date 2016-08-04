package com.gamepedia.ftb.bunnytech.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ModelBunnyTail extends ModelBiped {
    private final ModelRenderer tail;
    private static final ResourceLocation RL = new ResourceLocation("textures/entity/rabbit/white.png");

    public ModelBunnyTail() {
        tail = new ModelRenderer(this, 52, 6);
        tail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
        tail.setRotationPoint(0.0F, 20.0F, 7.0F);
        tail.mirror = true;
        setRotationOffset(tail, -0.3490659F, 0.0F, 0.0F);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    @Override
    public void render(@Nullable Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity == null) {
            return;
        }
        GlStateManager.pushMatrix();
        if (entity.isSneaking()) {
            GlStateManager.translate(0, -1F / 2F, 0);
        } else {
            GlStateManager.rotate(4F, 1, 0, 0);
            GlStateManager.translate(0, -9F / 16F, -1F / 3F);
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(RL);
        tail.render(scale);
        GlStateManager.popMatrix();
    }
}
