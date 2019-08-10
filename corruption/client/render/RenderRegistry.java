package com.corruption.client.render;

import com.corruption.entity.InfectedBiomassPod;
import com.corruption.entity.InfectedCreeper;
import com.corruption.entity.InfectedEvoker;
import com.corruption.entity.InfectedPigman;
import com.corruption.entity.InfectedSkeleton;
import com.corruption.entity.InfectedSlime;
import com.corruption.entity.InfectedSpider;
import com.corruption.entity.InfectedZombie;
import com.corruption.tileentity.PedestalTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {

	public static void RegisterEntityRegisters() {
		RenderingRegistry.registerEntityRenderingHandler(InfectedZombie.class, new InfectedZombieRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedSpider.class, new InfectedSpiderRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedSkeleton.class, new InfectedSkeletonRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedCreeper.class, new InfectedCreeperRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedPigman.class,new InfectedPigmanRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedSlime.class, new InfectedSlimeRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedBiomassPod.class, new InfectedBiomassPodRender.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(InfectedEvoker.class,new InfectedEvokerRender.RenderFactory());
		
	}
	public static void RegisterTileEntityRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(PedestalTileEntity.class, new PedestalRender());
	}
	
}
