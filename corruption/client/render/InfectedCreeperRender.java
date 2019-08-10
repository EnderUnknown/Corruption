package com.corruption.client.render;

import com.corruption.client.models.InfectedCreeperModel;
import com.corruption.entity.InfectedCreeper;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedCreeperRender extends MobRenderer<InfectedCreeper, InfectedCreeperModel>{

	public InfectedCreeperRender(EntityRendererManager manager) {
	      super(manager, new InfectedCreeperModel(), 0.5F);
	}
	
	protected void preRenderCallback(InfectedCreeper entitylivingbaseIn, float partialTickTime) {
	      float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
	      float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
	      f = MathHelper.clamp(f, 0.0F, 1.0F);
	      f = f * f;
	      f = f * f;
	      float f2 = (1.0F + f * 0.4F) * f1;
	      float f3 = (1.0F + f * 0.1F) / f1;
	      GlStateManager.scalef(f2, f3, f2);
	   }

	   protected int getColorMultiplier(InfectedCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
	      float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
	      if ((int)(f * 10.0F) % 2 == 0) {
	         return 0;
	      } else {
	         int i = (int)(f * 0.2F * 255.0F);
	         i = MathHelper.clamp(i, 0, 255);
	         return i << 24 | 822083583;
	      }
	   }
	   @Override
		protected ResourceLocation getEntityTexture(InfectedCreeper entity) {
			return new ResourceLocation("corruption:textures/entity/infected_creeper/infected_creeper.png");
		}

		public static class RenderFactory implements IRenderFactory<InfectedCreeper>{

			@Override
			public EntityRenderer<? super InfectedCreeper> createRenderFor(EntityRendererManager manager) {
				return new InfectedCreeperRender(manager);
			}
			
		}
}
