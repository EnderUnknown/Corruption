package com.corruption.block;

import com.corruption.Corruption;
import com.corruption.fluids.FluidStack;
import com.corruption.tileentity.InfuserTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Infuser extends Block implements ITileEntityProvider{

	public Infuser(Properties properties) {
		super(properties);
	}
	public TileEntity createNewTileEntity(IBlockReader world) {
		return new InfuserTileEntity();
	}
	
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
	      TileEntity tileentity = worldIn.getTileEntity(pos);
	      if (tileentity instanceof InfuserTileEntity) {
	    	  InfuserTileEntity t = (InfuserTileEntity)tileentity;
	    	  FluidStack f = FluidStack.GetVanillaValue(player.getHeldItemMainhand().getItem());
	    	  if(f==null) {
	    		  if(player.getHeldItemMainhand().getItem()==Items.BUCKET) {
	    			  FluidStack stack = t.DrainTank(1000, true);
	    			  if(stack!=null&&(stack.fluid == "lava"||stack.fluid=="milk"||stack.fluid=="water")&&stack.amount==1000) {
	    				  ItemStack i = new ItemStack(FluidStack.VanillaFluidToBucket(stack.fluid),1);
	    				  t.DrainTank(1000, false);
	    				  player.getHeldItemMainhand().shrink(1);
	    				  player.addItemStackToInventory(i);
	    				  worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 2.0f, 1.0f);
	    			  }
	    		  }
	    		  return;
	    	  }
	    	  int s = t.FillTank(f,true);
	        	 if(s != -1 && s == 0) {
	        		 t.FillTank(f, false);
	        		 player.getHeldItemMainhand().shrink(1);
	        		 player.addItemStackToInventory(new ItemStack(Items.BUCKET,1));
	        		 worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 2.0f, 1.0f);
	    	  }
	      }

	   }
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
	      if (!worldIn.isRemote) {
	         this.interactWith(worldIn, pos, player);
	      }

	      return true;
	   }
	
	
	

	
}
