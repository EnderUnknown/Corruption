package com.corruption.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegistry {

	public static SoundEvent BLOCK_MAGMAPISTON_EXTEND;
	public static SoundEvent BLOCK_MAGMAPISTON_RETRACT; 
	public static SoundEvent ENTITY_INFECTED_SPIDER_AMBIENT;
	public static SoundEvent ENTITY_INFECTED_SPIDER_DEATH;
	public static SoundEvent ENTITY_INFECTED_SPIDER_WALK;
	public static SoundEvent MISC_INFECTED_CONVERT;
	public static SoundEvent ENTITY_INFECTED_ZOMBIE_AMBIENT;
	public static SoundEvent ENTITY_INFECTED_ZOMBIE_DEATH;
	public static SoundEvent ENTITY_INFECTED_ZOMBIE_HURT;
	public static SoundEvent ENTITY_INFECTED_SKELETON_AMBIENT;
	public static SoundEvent ENTITY_INFECTED_SKELETON_DEATH;
	public static SoundEvent ENTITY_INFECTED_SKELETON_HURT;
	public static SoundEvent ENTITY_INFECTED_SKELETON_STEP;
	public static SoundEvent ENTITY_INFECTED_PIGMAN_AMBIENT;
	public static SoundEvent ENTITY_INFECTED_PIGMAN_HURT;
	public static SoundEvent ENTITY_INFECTED_PIGMAN_DEATH;

	
	public static void RegisterSounds() {
		BLOCK_MAGMAPISTON_EXTEND = register("block.magmapiston.extend");
		BLOCK_MAGMAPISTON_RETRACT = register("block.magmapiston.retract");
		
		ENTITY_INFECTED_SPIDER_AMBIENT = register("entity.infected_spider.ambient");
		ENTITY_INFECTED_SPIDER_DEATH = register("entity.infected_spider.death");
		ENTITY_INFECTED_SPIDER_WALK = register("entity.infected_spider.walk");
		
		ENTITY_INFECTED_ZOMBIE_AMBIENT = register("entity.infected_zombie.ambient");
		ENTITY_INFECTED_ZOMBIE_DEATH = register("entity.infected_zombie.death");
		ENTITY_INFECTED_ZOMBIE_HURT = register("entity.infected_zombie.hurt");
		
		ENTITY_INFECTED_SKELETON_AMBIENT = register("entity.infected_skeleton.ambient");
		ENTITY_INFECTED_SKELETON_DEATH = register("entity.infected_skeleton.death");
		ENTITY_INFECTED_SKELETON_HURT = register("entity.infected_skeleton.hurt");
		ENTITY_INFECTED_SKELETON_STEP = register("entity.infected_skeleton.step");
		
		ENTITY_INFECTED_PIGMAN_AMBIENT = register("entity.infected_pigman.ambient");
		ENTITY_INFECTED_PIGMAN_DEATH = register("entity.infected_pigman.death");
		ENTITY_INFECTED_PIGMAN_HURT = register("entity.infected_pigman.hurt");

		MISC_INFECTED_CONVERT = register("misc.infected_convert");
		
		
	}
	//private static SoundEvent register(String key) {
   //   return Registry.register(Registry.SOUND_EVENT, key, new SoundEvent(new ResourceLocation("corruption",key)));
    //}
	
	public static SoundEvent register(String name) {
		ResourceLocation loc = new ResourceLocation("corruption",name);
		SoundEvent event = new SoundEvent(loc);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
