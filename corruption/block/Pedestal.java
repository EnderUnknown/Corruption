package com.corruption.block;

import com.corruption.tileentity.PedestalTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Pedestal extends Block implements ITileEntityProvider{

	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
	
	public Pedestal(Properties properties) {
		super(properties);
	}
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new PedestalTileEntity();
	}
	
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
	      TileEntity tileentity = worldIn.getTileEntity(pos);
	      if (tileentity instanceof PedestalTileEntity) {
	    	  PedestalTileEntity t = (PedestalTileEntity)tileentity;
	    	  boolean success = t.AddItemToInventory(player.getHeldItemMainhand().getItem(),player.getHeldItemMainhand().getTag());
	        	 if(success)
	        		 player.getHeldItemMainhand().shrink(1);
	      }

	   }
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	      if (!worldIn.isRemote) {
	         this.interactWith(worldIn, pos, player);
	      }

	      return true;
	   }
	
	
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
	      if (state.getBlock() != newState.getBlock()) {
	         TileEntity tileentity = worldIn.getTileEntity(pos);
	         if (tileentity instanceof PedestalTileEntity) {
	        	((PedestalTileEntity) tileentity).DropItems();
	            worldIn.updateComparatorOutputLevel(pos, this);
	         }

	         super.onReplaced(state, worldIn, pos, newState, isMoving);
	      }
	   }
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
	      return SHAPE;
	   }

	
}
