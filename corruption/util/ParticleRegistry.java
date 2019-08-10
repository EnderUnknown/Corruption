package com.corruption.util;

import com.corruption.client.particle.BreakParticle;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleRegistry {

	
	private final static Int2ObjectMap<IParticleFactory<?>> factories = new Int2ObjectOpenHashMap<>();
	
	public static BasicParticleType ENTITY_INFECTED_SLIME_HOP;
	
	public static void RegisterParticleTypes(final RegistryEvent.Register<ParticleType<?>> event) {
		ENTITY_INFECTED_SLIME_HOP = register("entity_infected_slime_hop",false);
		registerFactory(ENTITY_INFECTED_SLIME_HOP, new BreakParticle.InfectedSlimeFactory());
		
	}
	
	public static BasicParticleType register(String name,boolean bool ) {
		BasicParticleType event = new BasicParticleType(bool);
		event.setRegistryName(name);
		ForgeRegistries.PARTICLE_TYPES.register(event);
		return event;
	}
	
	
	public static <T extends IParticleData> void registerFactory(ParticleType<T> particleTypeIn, IParticleFactory<T> particleFactoryIn) {
	      factories.put(Registry.PARTICLE_TYPE.getId(particleTypeIn), particleFactoryIn);
		
	   }
	 
	
	
}
