package com.corruption.client.render;

import com.corruption.client.models.InfectedPigmanModel;
import com.corruption.entity.InfectedPigman;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class InfectedPigmanRender extends MobRenderer<InfectedPigman,InfectedPigmanModel>{

	public InfectedPigmanRender(EntityRendererManager manager) {
		super(manager, new InfectedPigmanModel(), 0.5f);
		this.addLayer(new HeldItemLayer<>(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(InfectedPigman entity) {
		return new ResourceLocation("corruption:textures/entity/infected_pigman/infected_pigman.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedPigman>{

		@Override
		public EntityRenderer<? super InfectedPigman> createRenderFor(EntityRendererManager manager) {
			return new InfectedPigmanRender(manager);
		}
		
	}
}
