package com.corruption.util;

import com.corruption.block.BlockRegistry;
import com.corruption.world.features.Ebonlake;
import com.corruption.world.trees.EbonoakGen;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Features {

	public static Feature<NoFeatureConfig> EBONOAK_TREE;
	public static Feature<LakesConfig> EBONLAKE_DEFAULT;
	
	
	
	public static void RegisterFeatures() {
		EBONOAK_TREE = register("ebonoak_tree",new EbonoakGen(NoFeatureConfig::deserialize,false));
		EBONLAKE_DEFAULT = register("ebonlake_default", new Ebonlake(LakesConfig::deserialize,BlockRegistry.EBONGRASS.getDefaultState()));
	}
	
	
	
	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
	      return (F)(Registry.<Feature<?>>register(Registry.FEATURE, key, value));
	   }
}
