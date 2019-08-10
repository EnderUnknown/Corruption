package com.corruption.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;

public class Ebongrass extends Block{

	public Ebongrass(Properties properties) {
		super(properties);
		
	}
	
	public void tick(BlockState state, World world, BlockPos pos, Random rand) {
		 if (!world.isRemote) {
			 BlockState up = world.getBlockState(pos.up());
	         if (!world.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
	         if(up.getOpacity(world, pos.up())>14 && up.getBlock()!=Blocks.WATER) {
	 			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	         } else {
	            if (world.getLight(pos.up()) >= 9) {
	               BlockState blockstate = this.getDefaultState();

	               for(int i = 0; i < 4; ++i) {
	                  BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
	                  if (world.getBlockState(blockpos).getBlock() == Blocks.DIRT && LightEngine.func_215613_a(world, blockstate, blockpos, world.getBlockState(blockpos.up()), blockpos.up(), Direction.UP, world.getBlockState(blockpos.up()).getOpacity(world, blockpos.up()))<world.getMaxLightLevel()&&world.getBlockState(blockpos.up()).getBlock()!=Blocks.WATER) {
	                	  world.setBlockState(blockpos, BlockRegistry.EBONGRASS.getDefaultState());
	                  }
	               }
	            }

	         }
	      }
	}
}
