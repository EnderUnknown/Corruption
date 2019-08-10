package com.corruption.world.biome;

import com.corruption.block.BlockRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeRegister {

	public static Biome CORRUPTION_FOREST;
	
	public static final SurfaceBuilderConfig EBONGRASS_DIRT_GRAVEL = new SurfaceBuilderConfig(GetBlockState(BlockRegistry.EBONGRASS), GetBlockState(Blocks.DIRT), GetBlockState(Blocks.GRAVEL));
	
	public static void registerBiomes() {
		CORRUPTION_FOREST = register(0, "corrupt_forest", new CorruptForestBiome(), BiomeType.WARM, Type.FOREST, Type.SPOOKY, Type.MAGICAL, Type.FOREST);
	}
	
	private static BlockState GetBlockState(Block block) {
		return block.getDefaultState();
	}
	
	private static Biome register(int id, String name, Biome biome, BiomeType biomeType, net.minecraftforge.common.BiomeDictionary.Type... types) {
		  biome.setRegistryName(name);
		  ForgeRegistries.BIOMES.register(biome);
		  BiomeDictionary.addTypes(biome, types);
		  BiomeManager.addBiome(biomeType, new BiomeEntry(biome,100));
		  BiomeManager.addSpawnBiome(biome);
		  return biome;
	      
	   }
	
	
}
