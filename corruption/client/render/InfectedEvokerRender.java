package com.corruption.client.render;

import com.corruption.client.models.InfectedEvokerModel;
import com.corruption.entity.InfectedEvoker;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedEvokerRender extends MobRenderer<InfectedEvoker, InfectedEvokerModel<InfectedEvoker>>{
	
	public InfectedEvokerRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new InfectedEvokerModel<>(0.0F, 0.0F, 64, 64), 0.5f);
		this.addLayer(new HeldItemLayer<InfectedEvoker, InfectedEvokerModel<InfectedEvoker>>(this) {
	         public void render(InfectedEvoker entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
	            if (entityIn.isSpellcasting()) {
	               super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
	            }

	         }
	      });
		
	}
	@Override
	protected ResourceLocation getEntityTexture(InfectedEvoker entity) {
		return new ResourceLocation("corruption:textures/entity/infected_evoker/infected_evoker.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedEvoker>{

		@Override
		public EntityRenderer<? super InfectedEvoker> createRenderFor(EntityRendererManager manager) {
			return new InfectedEvokerRender(manager);
		}
		
	}
}
