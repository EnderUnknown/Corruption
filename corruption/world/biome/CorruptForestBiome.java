package com.corruption.world.biome;

import com.corruption.entity.EntityRegistry;
import com.corruption.util.Features;
import com.corruption.world.features.EbonicBiomeDefault;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CorruptForestBiome extends Biome{
	public CorruptForestBiome() {
		super((new Biome.Builder())
				.surfaceBuilder(SurfaceBuilder.DEFAULT,BiomeRegister.EBONGRASS_DIRT_GRAVEL)
				.precipitation(Biome.RainType.RAIN)
				.category(Biome.Category.FOREST)
				.depth(0.2f)
				.scale(0.2f)
				.temperature(0.7f)
				.downfall(0.1f)
				.waterColor(8126719)
				.waterFogColor(6422783)
				.parent((String)null));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addSedimentDisks(this);
		EbonicBiomeDefault.addOres(this);
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Features.EBONLAKE_DEFAULT, new LakesConfig(Blocks.WATER.getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(4)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,createDecoratedFeature(Features.EBONOAK_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(5,0.2f,1)));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistry.INFECTED_ZOMBIE, 100, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistry.INFECTED_SPIDER, 100, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistry.INFECTED_SKELETON, 100, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistry.INFECTED_CREEPER, 100, 1, 4));
	}
	
	
	
	
	
	
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		
		return 0x553082;
	}
	@OnlyIn(Dist.CLIENT)

    @Override

    public int getGrassColor(BlockPos pos)

    {

    	return 0x553082;

    }



    @OnlyIn(Dist.CLIENT)

    @Override

    public int getFoliageColor(BlockPos pos)

    {

    	return 0x553082;

    }
	
	
}
