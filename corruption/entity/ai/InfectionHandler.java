package com.corruption.entity.ai;

import java.util.Dictionary;
import java.util.Random;

import com.corruption.block.BlockRegistry;
import com.corruption.entity.EntityRegistry;
import com.corruption.entity.InfectedZombie;
import com.corruption.world.biome.BiomeRegister;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;

public class InfectionHandler {

	public static Biome[] corruptBiomes = new Biome[] {BiomeRegister.CORRUPTION_FOREST};
	public static Dictionary<EntityType<?>, EntityType<?>> infectionDict;
	public static Random rand = new Random();
	
	/**Registers an entity pair to the infection dictionary. The dictionary should be registered after both entities are registered. 
	 * @param victum The entity killed by the infected.
	 * @param infectedVariant The entity spawned as a result of the victum being killed by an infected
	 */
	public static void RegisterInfectionDictionaryItem(EntityType<?> victum, EntityType<?> infectedVariant) {
		infectionDict.put(victum, infectedVariant);
	}
	//public static void RegisterInfectionDictionary() {
	//	RegisterInfectionDictionaryItem(EntityType.ZOMBIE, EntityRegistry.INFECTED_ZOMBIE);
	//	RegisterInfectionDictionaryItem(EntityType.SPIDER, EntityRegistry.INFECTED_SPIDER);
	//}
	
	
	public static EntityType<?> GetInfectedVariant(EntityType<?> target) {
		if(target == EntityType.ZOMBIE)
			return EntityRegistry.INFECTED_ZOMBIE;
		else if(target == EntityType.SPIDER)
			return EntityRegistry.INFECTED_SPIDER;
		else if(target == EntityType.SKELETON) 
			return EntityRegistry.INFECTED_SKELETON;
		else if(target == EntityType.CREEPER)
			return EntityRegistry.INFECTED_CREEPER;
		else if(target == EntityType.ZOMBIE_PIGMAN)
			return EntityRegistry.INFECTED_PIGMAN;
		else if(target == EntityType.EVOKER)
			return EntityRegistry.INFECTED_EVOKER;
		else if(target == EntityType.BAT)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.CAT)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.CHICKEN)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.COW)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.COD)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.DOLPHIN)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.DONKEY)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.FOX)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.HORSE)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.LLAMA)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.SLIME)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.MOOSHROOM)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.MULE)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.OCELOT)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.PIG)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.PANDA)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.POLAR_BEAR)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.PUFFERFISH)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.RABBIT)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.PARROT)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.SALMON)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.SHEEP)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.SKELETON_HORSE)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.SQUID)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.TRADER_LLAMA)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.TROPICAL_FISH)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.TURTLE)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.WOLF)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		else if(target == EntityType.ZOMBIE_HORSE)
			return EntityRegistry.INFECTED_BIOMASS_POD;
		//else if(target == EntityType.SLIME)
		//	return EntityRegistry.INFECTED_SLIME;
		//if(infectionDict.get(target)!=null)
		//	return infectionDict.get(target);

		else
			return EntityRegistry.INFECTED_ZOMBIE;
	}
	
	public static BlockState GetEbonicVariant(BlockState block) {
		if(block== Blocks.GRASS_BLOCK.getDefaultState())
			return BlockRegistry.EBONGRASS.getDefaultState();
		else return null;
	}
	
	public static boolean InfectionTarget(Class<?> c) {
		Class<?>[] friendly = new Class[] {InfectedZombie.class};
		for(int i=0;i<friendly.length;i++) {
			if(friendly[i]==c)
				return false;
		}
		return true;
	}
	
	
	public static boolean IsCorruptBiome(Biome biome) {
		for(int i = 0; i<corruptBiomes.length;i++) {
			if(biome==corruptBiomes[i])
				return true;
		}
		return false;
	}
	/**
	 * 
	 * @param pool options: corruption or infected.
	 * @return Random EntityType based on the selected pool of entities.
	 */
	public static EntityType<?> GetRandomEntity(String pool){
		if(pool == "pod_basic") {
			EntityType<?>[] entities = new EntityType<?>[] {EntityRegistry.INFECTED_CREEPER,EntityRegistry.INFECTED_PIGMAN,
				EntityRegistry.INFECTED_SKELETON, EntityRegistry.INFECTED_SPIDER,EntityRegistry.INFECTED_ZOMBIE};
			int r = rand.nextInt(entities.length);
			return entities[r];
		}
		return EntityRegistry.INFECTED_ZOMBIE;
	}
	
}
