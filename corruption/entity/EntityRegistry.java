package com.corruption.entity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.RegistryEvent;

public class EntityRegistry {

	public static EntityType<?> INFECTED_ZOMBIE = EntityType.Builder.create(InfectedZombie::new,EntityClassification.MONSTER).size(EntityType.ZOMBIE.getWidth(), EntityType.ZOMBIE.getHeight()).immuneToFire().build("corruption:infected_zombie").setRegistryName("infected_zombie");
	public static EntityType<?> INFECTED_SPIDER = EntityType.Builder.create(InfectedSpider::new,EntityClassification.MONSTER).size(EntityType.SPIDER.getWidth(), EntityType.SPIDER.getHeight()).immuneToFire().build("corruption:infected_spider").setRegistryName("infected_spider");
	public static EntityType<?> INFECTED_SKELETON = EntityType.Builder.create(InfectedSkeleton::new,EntityClassification.MONSTER).size(EntityType.SKELETON.getWidth(), EntityType.SKELETON.getHeight()).immuneToFire().build("corruption:infected_skeleton").setRegistryName("infected_skeleton");
	public static EntityType<?> INFECTED_CREEPER = EntityType.Builder.create(InfectedCreeper::new,EntityClassification.MONSTER).size(EntityType.CREEPER.getWidth(), EntityType.CREEPER.getHeight()).immuneToFire().build("corruption:infected_creeper").setRegistryName("infected_creeper");
	public static EntityType<?> INFECTED_PIGMAN = EntityType.Builder.create(InfectedPigman::new,EntityClassification.MONSTER).size(EntityType.ZOMBIE_PIGMAN.getWidth(), EntityType.ZOMBIE_PIGMAN.getHeight()).immuneToFire().build("corruption:infected_pigman").setRegistryName("infected_pigman");
	public static EntityType<?> INFECTED_SLIME = EntityType.Builder.create(InfectedSlime::new,EntityClassification.MONSTER).size(EntityType.SLIME.getWidth(), EntityType.SLIME.getHeight()).immuneToFire().build("corruption:infected_slime").setRegistryName("infected_slime");
	public static EntityType<?> INFECTED_BIOMASS_POD = EntityType.Builder.create(InfectedBiomassPod::new,EntityClassification.MISC).size(0.5f, 1f).immuneToFire().build("corruption:infected_biomass_pod").setRegistryName("infected_biomass_pod");
	public static EntityType<?> INFECTED_EVOKER = EntityType.Builder.create(InfectedEvoker::new,EntityClassification.MONSTER).size(EntityType.EVOKER.getWidth(),EntityType.EVOKER.getHeight()).immuneToFire().build("corruption:infected_evoker").setRegistryName("infected_evoker");
	
	public static void registerEntity(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(INFECTED_ZOMBIE);
		event.getRegistry().register(INFECTED_SPIDER);
		event.getRegistry().register(INFECTED_SKELETON);
		event.getRegistry().register(INFECTED_CREEPER);
		event.getRegistry().register(INFECTED_PIGMAN);
		event.getRegistry().register(INFECTED_SLIME);
		event.getRegistry().register(INFECTED_BIOMASS_POD);
		event.getRegistry().register(INFECTED_EVOKER);
		
		registerEntityWorldSpawns(); 
		
	}
	
	public static void registerEntityWorldSpawns() {   //Only use to add an entity to a vanilla biome. Entities can be added via the biome itself for modded biomes
		
	}
	
	public static void registerEntitySpawnEgg(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_ZOMBIE,0x553082,0x66e0ff,"infected_zombie_spawn_egg"));
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_SPIDER,0x553082,0x910000,"infected_spider_spawn_egg"));
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_SKELETON, 0x553082, 0xBABABA, "infected_skeleton_spawn_egg"));
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_CREEPER, 0x553082, 0x009C00, "infected_creeper_spawn_egg"));
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_PIGMAN, 0x553082, 0xFFA2C0, "infected_pigman_spawn_egg"));
		event.getRegistry().register(registerEntitySpawnEgg(INFECTED_EVOKER, 0x553082, 0xA5AFBE, "infected_evoker_spawn_egg"));
		//event.getRegistry().register(registerEntitySpawnEgg(INFECTED_SLIME, 0x553082, 0x92E28C, "infected_slime_spawn_egg"));
	}
	
	public static Item registerEntitySpawnEgg(EntityType<?> type, int primaryColor, int secondaryColor, String name) {
		SpawnEggItem item = new SpawnEggItem(type, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC));
		item.setRegistryName(name);
		return item;
	}
	
	
	public static void registerEntityWorldSpawn(EntityType<?> entity, int weight, int groupMin, int groupMax,  Biome... biomes) {
		for(Biome biome : biomes) {
			if(biome!=null) {
				biome.getSpawns(entity.getClassification()).add(new SpawnListEntry(entity,weight,groupMin, groupMax));
			}
		}
	}
	
}
