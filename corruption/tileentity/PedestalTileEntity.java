package com.corruption.tileentity;

import com.corruption.Corruption;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PedestalTileEntity extends TileEntity implements ITickableTileEntity{

	protected NonNullList<ItemStack> inv = NonNullList.withSize(1, ItemStack.EMPTY);
	protected double currentRotation = 0;
	protected double prevRotation = 0;
	
	public PedestalTileEntity() {
		super(TileEntityRegistry.PEDESTAL);
	}

	
	@Override
	public void tick() {
		this.prevRotation = this.currentRotation;
        this.currentRotation = (this.currentRotation + (double)(50.0F / ((float)20 + 200.0F))) % 360.0D;
        Corruption.LOGGER.info(inv.get(0));
	}
	
	
	public CompoundNBT write(CompoundNBT compound) {	
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, inv);
		return compound;
	}
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.inv = NonNullList.withSize(1, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, inv);
	}
	
	
	public boolean AddItemToInventory(Item item, CompoundNBT itemNbt) {
		if(inv.get(0).isEmpty() && item!=null) {
			inv.clear();
			ItemStack i = new ItemStack(item,1,itemNbt);
			i.setTag(itemNbt);
			inv = NonNullList.withSize(1,i);
			return true;
		}
		else {
			DropItems();
			return false;
		}
	}
	
	
	
	public void DropItems() {
		if(!inv.get(0).isEmpty()) {
			ItemEntity i = new ItemEntity(this.world,(double)this.pos.getX()+0.5D,(double)this.pos.getY()+1.0D, (double)this.pos.getZ()+0.5D,inv.get(0));
			this.world.addEntity(i);
			this.inv.clear();
			this.inv = NonNullList.withSize(1, ItemStack.EMPTY);
		}
	}
	
	public ItemStack GetContainedItem() {
		return inv.get(0);
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public double GetCurrentRotation() {
		return this.currentRotation;
	}
	@OnlyIn(Dist.CLIENT)
	public double GetPrevRotation() {
		return this.prevRotation;
	}

}
