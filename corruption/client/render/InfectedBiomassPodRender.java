package com.corruption.client.render;

import com.corruption.client.models.InfectedBiomassPodModel;
import com.corruption.entity.InfectedBiomassPod;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedBiomassPodRender extends MobRenderer<InfectedBiomassPod, InfectedBiomassPodModel<InfectedBiomassPod>>{

	public InfectedBiomassPodRender(EntityRendererManager manager) {
		super(manager, new InfectedBiomassPodModel<>(), 0.5f);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(InfectedBiomassPod entity) {
		return new ResourceLocation("corruption:textures/entity/infected_biomass_pod/infected_biomass_pod.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedBiomassPod>{

		@Override
		public EntityRenderer<? super InfectedBiomassPod> createRenderFor(EntityRendererManager manager) {
			return new InfectedBiomassPodRender(manager);
		}
		
	}

}
