package com.corruption.entity;

import com.corruption.util.SoundRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class InfectedPigman extends InfectedZombie{

	public InfectedPigman(EntityType<? extends InfectedZombie> type, World world) {
		super(type, world);
	}
	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.23F);
	    this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}
	@Override
	protected SoundEvent getAmbientSound() {
	      return SoundRegistry.ENTITY_INFECTED_PIGMAN_AMBIENT;
   }
	@Override
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundRegistry.ENTITY_INFECTED_PIGMAN_HURT;
   }
	@Override
   protected SoundEvent getDeathSound() {
      return SoundRegistry.ENTITY_INFECTED_PIGMAN_DEATH;
   }
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	   }
	@Override
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		setEquipmentBasedOnDifficulty(difficultyIn);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}
   

}
