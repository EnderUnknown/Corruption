package com.corruption.client.render;

import com.corruption.client.models.InfectedSpiderModel;
import com.corruption.entity.InfectedSpider;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedSpiderRender extends MobRenderer<InfectedSpider,InfectedSpiderModel>{

	public InfectedSpiderRender(EntityRendererManager manager) {
		super(manager, new InfectedSpiderModel(), 0.5f);
		this.addLayer(new LayerInfectedSpiderEyes(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(InfectedSpider entity) {
		return new ResourceLocation("corruption:textures/entity/infected_spider/infected_spider.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedSpider>{

		@Override
		public EntityRenderer<? super InfectedSpider> createRenderFor(EntityRendererManager manager) {
			return new InfectedSpiderRender(manager);
		}
		
	}
	
}
