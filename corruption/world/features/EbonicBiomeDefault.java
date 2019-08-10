package com.corruption.world.features;

import com.corruption.block.BlockRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class EbonicBiomeDefault {

	public static void addOres(Biome biomeIn) {
	      //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.VOID_IRON_ORE.getDefaultState(), 9), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 64)));
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.BLIGHTED_GOLD_ORE.getDefaultState(), 9), Placement.COUNT_RANGE, new CountRangeConfig(2, 0, 0, 32)));
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.NULLSTONE_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(8, 0, 0, 16)));
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.ABYSSAL_DIAMOND_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(1, 0, 0, 16)));
	      //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7), Placement.COUNT_DEPTH_AVERAGE, new DepthAverageConfig(1, 16, 16)));
	   }

	   public static void addExtraGoldOre(Biome biomeIn) {
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.BLIGHTED_GOLD_ORE.getDefaultState(), 9), Placement.COUNT_RANGE, new CountRangeConfig(20, 32, 32, 80)));
	   }

	   public static void addExtraEmeraldOre(Biome biomeIn) {
	      biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.EMERALD_ORE, new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), BlockRegistry.ARCANE_EMERALD_ORE.getDefaultState()), Placement.EMERALD_ORE, IPlacementConfig.NO_PLACEMENT_CONFIG));
	   }
	
}
