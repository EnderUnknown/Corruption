package com.corruption.client.render;

import com.corruption.client.models.InfectedSpiderModel;
import com.corruption.entity.InfectedSpider;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerInfectedSpiderEyes<T extends InfectedSpider> extends LayerRenderer<InfectedSpider, InfectedSpiderModel>{//SpiderEyesLayer<InfectedSpider, InfectedSpiderModel>{

	private final InfectedSpiderRender spiderRenderer;
	public LayerInfectedSpiderEyes(InfectedSpiderRender entityRenderer) {
		super(entityRenderer);

		this.spiderRenderer = entityRenderer;
	}

	
	
	
	@Override
	public void render(InfectedSpider entity, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6,
			float arg7) {
	
		this.bindTexture(new ResourceLocation("corruption:textures/entity/infected_spider/infected_spider_eyes.png"));
	      GlStateManager.enableBlend();
	      GlStateManager.disableAlphaTest();
	      GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
	      if (entity.isInvisible()) {
	         GlStateManager.depthMask(false);
	      } else {
	         GlStateManager.depthMask(true);
	      }

	      int i = 61680;
	      int j = i % 65536;
	      int k = i / 65536;
	      GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
	      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
	      gamerenderer.setupFogColor(true);
	      this.spiderRenderer.getEntityModel().render(entity, arg1, arg2, arg4, arg5, arg6, arg7);
	      gamerenderer.setupFogColor(false);
	      i = entity.getBrightnessForRender();
	      j = i % 65536;
	      k = i / 65536;
	      GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
	      this.func_215334_a(entity);
	      GlStateManager.depthMask(true);
	      GlStateManager.disableBlend();
	      GlStateManager.enableAlphaTest();
		
	}
	

	@Override
	public boolean shouldCombineTextures() {
		
		return false;
	}

}
