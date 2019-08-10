package com.corruption.world.trees;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.trees.BigTree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EbonoakTree extends BigTree {
   @Nullable
   protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
      return null;
   }

   @Nullable
   protected AbstractTreeFeature<NoFeatureConfig> getBigTreeFeature(Random random) {
      return new EbonoakGen(NoFeatureConfig::deserialize, true);
   }
}