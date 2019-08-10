package com.corruption.client.render;

import com.corruption.client.models.InfectedSlimeModel;
import com.corruption.entity.InfectedSlime;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class InfectedSlimeRender extends MobRenderer<InfectedSlime, InfectedSlimeModel>{

	public InfectedSlimeRender(EntityRendererManager renderManagerIn) {
	      super(renderManagerIn, new InfectedSlimeModel(16), 0.25F);
	      this.addLayer(new LayerInfectedSlimeGel(this));
	   }

	   public void doRender(InfectedSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
	      this.shadowSize = 0.25F * (float)entity.getSlimeSize();
	      super.doRender(entity, x, y, z, entityYaw, partialTicks);
	   }

	   protected void preRenderCallback(InfectedSlime entitylivingbaseIn, float partialTickTime) {
	      float f = 0.999F;
	      GlStateManager.scalef(0.999F, 0.999F, 0.999F);
	      float f1 = (float)entitylivingbaseIn.getSlimeSize();
	      float f2 = MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevSquishFactor, entitylivingbaseIn.squishFactor) / (f1 * 0.5F + 1.0F);
	      float f3 = 1.0F / (f2 + 1.0F);
	      GlStateManager.scalef(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	   }

	   @Override
		protected ResourceLocation getEntityTexture(InfectedSlime entity) {
			return new ResourceLocation("corruption:textures/entity/infected_slime/infected_slime.png");
		}

		public static class RenderFactory implements IRenderFactory<InfectedSlime>{

			@Override
			public EntityRenderer<? super InfectedSlime> createRenderFor(EntityRendererManager manager) {
				return new InfectedSlimeRender(manager);
			}
			
		}
}
