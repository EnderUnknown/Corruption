package com.corruption.client.render;

import com.corruption.client.models.InfectedZombieModel;
import com.corruption.entity.InfectedZombie;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class InfectedZombieRender extends MobRenderer<InfectedZombie, InfectedZombieModel>{

	
	
	public InfectedZombieRender(EntityRendererManager manager) {
		super(manager, new InfectedZombieModel(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(InfectedZombie entity) {
		return new ResourceLocation("corruption:textures/entity/infected_zombie/infected_zombie.png");
	}

	public static class RenderFactory implements IRenderFactory<InfectedZombie>{

		@Override
		public EntityRenderer<? super InfectedZombie> createRenderFor(EntityRendererManager manager) {
			return new InfectedZombieRender(manager);
		}
		
	}
	
}
