package com.corruption.tileentity;

import com.corruption.Corruption;
import com.corruption.fluids.FluidStack;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class InfuserTileEntity extends TileEntity implements ITickableTileEntity{

	protected int fluidCapacity = 10000;
	protected int currentFluid = 0;
	protected String fluid = "empty";
	protected boolean infusing = false;
	
	public InfuserTileEntity() {
		super(TileEntityRegistry.INFUSER);
	}

	@Override
	public void tick() {
		
	}
	
	
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("FluidCapacity", this.fluidCapacity);
		compound.putInt("CurrentFluidValue", this.currentFluid);
		compound.putBoolean("Infusing", this.infusing);
		compound.putString("FluidType", fluid);
		return compound;
	}
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.fluidCapacity = compound.getInt("FluidCapacity");
		this.currentFluid = compound.getInt("CurrentFluidValue");
		this.infusing = compound.getBoolean("Infusing");
		this.fluid = compound.getString("FluidType");
	}
	
	/**
	 * Adds fluid to the tank.
	 * @param fluid the fluid to add.
	 * @param simulation if set to true, the remainder is returned but the fluid isn't added;
	 * @return the remainder that cannot be stored in the tank. -1 if the fluid types aren't compatible.
	 */
	public int FillTank(FluidStack fluid,boolean simulation) {
		if(FluidStack.areFluidStackTagsEqual(fluid, new FluidStack(this.fluid,this.currentFluid))) {//(fluid.fluid == this.fluid || this.fluid=="empty") {
			int r = 0;
			if(fluid.amount +this.currentFluid>this.fluidCapacity)
				r = fluid.amount+this.currentFluid-this.fluidCapacity;
			if(!simulation) {
				this.currentFluid += (fluid.amount-r);
				this.fluid = fluid.fluid;
			}
			return r;
			
		}
		return -1;
	}
	/**
	 * Removes fluid from the tank.
	 * @param amount the target amount to remove.
	 * @param simulation if set to true, the fluid isn't taken
	 * @return the fluid amount and type that can/was be taken from the tank
	 */
	public FluidStack DrainTank(int amount,boolean simulation) {
		if(CheckIfEmpty())
			return null;
		int a = amount;
		if(amount > this.currentFluid)
			a = this.currentFluid;
		if(!simulation) {
			this.currentFluid -= a;
			CheckIfEmpty();
		}
		return new FluidStack(this.fluid,a);
	}
	
	public void SetEmpty() {
		this.currentFluid=0;
		this.fluid = "empty";
	}
	public boolean CheckIfEmpty() {
		if(this.fluid=="empty"||this.currentFluid==0) {
			SetEmpty();
			return true;
		}
		return false;
	}
	public FluidStack GetFluidStack() {
		if(CheckIfEmpty())
			return null;
		else 
			return new FluidStack(this.fluid,this.currentFluid);
	}

}
