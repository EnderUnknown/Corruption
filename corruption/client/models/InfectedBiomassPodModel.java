package com.corruption.client.models;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfectedBiomassPodModel<T extends Entity> extends EntityModel<T>  {
    public RendererModel base;
    public RendererModel head1;
    public RendererModel head2;
    public RendererModel head3;
    public RendererModel head4;

    public InfectedBiomassPodModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.base = new RendererModel(this, 24, 0);
        this.base.setRotationPoint(0.0F, 22.0F, 0.0F);
        this.base.addBox(-5.0F, 0.0F, -5.0F, 10, 2, 10, 0.0F);
        this.head4 = new RendererModel(this, 0, 0);
        this.head4.setRotationPoint(0.0F, 15.799999999999981F, 0.0F);
        this.head4.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(head4, -0.39783246858665794F, -0.6544985773522438F, 0.0F);
        this.head3 = new RendererModel(this, 0, 0);
        this.head3.setRotationPoint(0.0F, 15.799999999999981F, 0.0F);
        this.head3.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(head3, 0.7853981633974483F, 0.7853981633974483F, 0.7853981633974483F);
        this.head1 = new RendererModel(this, 0, 0);
        this.head1.setRotationPoint(0.0F, 15.799999999999981F, 0.0F);
        this.head1.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(head1, 0.7853981633974483F, 0.7853981633974483F, 0.0F);
        this.head2 = new RendererModel(this, 0, 0);
        this.head2.setRotationPoint(0.0F, 15.799999999999981F, 0.0F);
        this.head2.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
    }

    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.base.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translatef(this.head4.offsetX, this.head4.offsetY, this.head4.offsetZ);
        GlStateManager.translatef(this.head4.rotationPointX * f5, this.head4.rotationPointY * f5, this.head4.rotationPointZ * f5);
        GlStateManager.scaled(1.3324818608640008D, 1.6039750400150414D, 1.374121919016001D);
        GlStateManager.translatef(-this.head4.offsetX, -this.head4.offsetY, -this.head4.offsetZ);
        GlStateManager.translatef(-this.head4.rotationPointX * f5, -this.head4.rotationPointY * f5, -this.head4.rotationPointZ * f5);
        this.head4.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(this.head3.offsetX, this.head3.offsetY, this.head3.offsetZ);
        GlStateManager.translatef(this.head3.rotationPointX * f5, this.head3.rotationPointY * f5, this.head3.rotationPointZ * f5);
        GlStateManager.scaled(1.3324818608640008D, 1.6027758063402635D, 1.374121919016001D);
        GlStateManager.translatef(-this.head3.offsetX, -this.head3.offsetY, -this.head3.offsetZ);
        GlStateManager.translatef(-this.head3.rotationPointX * f5, -this.head3.rotationPointY * f5, -this.head3.rotationPointZ * f5);
        this.head3.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(this.head1.offsetX, this.head1.offsetY, this.head1.offsetZ);
        GlStateManager.translatef(this.head1.rotationPointX * f5, this.head1.rotationPointY * f5, this.head1.rotationPointZ * f5);
        GlStateManager.scaled(1.3324818608640008D, 1.6027758063402635D, 1.374121919016001D);
        GlStateManager.translatef(-this.head1.offsetX, -this.head1.offsetY, -this.head1.offsetZ);
        GlStateManager.translatef(-this.head1.rotationPointX * f5, -this.head1.rotationPointY * f5, -this.head1.rotationPointZ * f5);
        this.head1.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(this.head2.offsetX, this.head2.offsetY, this.head2.offsetZ);
        GlStateManager.translatef(this.head2.rotationPointX * f5, this.head2.rotationPointY * f5, this.head2.rotationPointZ * f5);
        GlStateManager.scaled(1.3324818608640008D, 1.6027758063402635D, 1.374121919016001D);
        GlStateManager.translatef(-this.head2.offsetX, -this.head2.offsetY, -this.head2.offsetZ);
        GlStateManager.translatef(-this.head2.rotationPointX * f5, -this.head2.rotationPointY * f5, -this.head2.rotationPointZ * f5);
        this.head2.render(f5);
        GlStateManager.popMatrix();
    }

    
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
