package com.corruption.client.render;

import com.corruption.client.models.InfectedSkeletonModel;
import com.corruption.entity.InfectedSkeleton;

import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedSkeletonRender extends BipedRenderer<InfectedSkeleton, InfectedSkeletonModel>{
	public InfectedSkeletonRender(EntityRendererManager manager) {
		super(manager, new InfectedSkeletonModel(), 0.5f);
		this.addLayer(new HeldItemLayer<>(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(InfectedSkeleton entity) {
		return new ResourceLocation("corruption:textures/entity/infected_skeleton/infected_skeleton.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedSkeleton>{

		@Override
		public EntityRenderer<? super InfectedSkeleton> createRenderFor(EntityRendererManager manager) {
			return new InfectedSkeletonRender(manager);
		}
		
	}
}
