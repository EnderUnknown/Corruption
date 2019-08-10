package com.corruption.entity;

import java.util.function.Predicate;

import com.corruption.Corruption;
import com.corruption.config.CorruptionSpreadConfig;
import com.corruption.entity.ai.CreatureAttributes;
import com.corruption.entity.ai.InfectionHandler;
import com.corruption.potion.EffectRegistry;
import com.corruption.util.SoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfectedZombie extends MonsterEntity{

	public InfectedZombie(EntityType<? extends InfectedZombie> type, World world) {
		super(type, world);
		
		
	}
	
	private static final Predicate<LivingEntity> NOT_EBONIC_OR_INFECTED = (predicate) -> {
	      return predicate.getCreatureAttribute() != CreatureAttributes.EBONIC && predicate.getCreatureAttribute() != CreatureAttributes.INFESTED && predicate.getActivePotionEffect(EffectRegistry.EBONIC)==null;
	   };
	//private static final EntityPredicate ebonicinfestedFilter = (new EntityPredicate()).setCustomPredicate(NOT_EBONIC_OR_INFECTED);

	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	    this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
	    this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0,true,false,NOT_EBONIC_OR_INFECTED));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false,false, NOT_EBONIC_OR_INFECTED));
	}
	@Override
	protected void registerAttributes() {
		  super.registerAttributes();
		  this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
	      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.23F);
	      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	      this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
	}
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
	      return 1.74F;
	   }
	protected SoundEvent getAmbientSound() {
	      return SoundRegistry.ENTITY_INFECTED_ZOMBIE_AMBIENT;
	   }

	   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
	      return SoundRegistry.ENTITY_INFECTED_ZOMBIE_HURT;
	   }

	   protected SoundEvent getDeathSound() {
	      return SoundRegistry.ENTITY_INFECTED_ZOMBIE_HURT;
	   }

	   protected SoundEvent getStepSound() {
	      return SoundEvents.ENTITY_ZOMBIE_STEP;
	   }

	   protected void playStepSound(BlockPos pos, BlockState blockIn) {
	      this.playSound(this.getStepSound(), 0.15F, 1.0F);
	   }

	   public CreatureAttribute getCreatureAttribute() {
	      return CreatureAttributes.INFESTED;
	   }
	 
	   protected boolean inCorruption(){
		   return (this.world.isRemote)?false:InfectionHandler.IsCorruptBiome(this.world.getBiome(this.getPosition()));
	   }
	   
	   @Override
	   public void livingTick() {
		   if(this.isAlive() && inCorruption()) {
			   this.addPotionEffect(new EffectInstance(EffectRegistry.CONTAGIOUS_STRENGTH));
		   }
		   super.livingTick();
	   }
	
	   public void onKillEntity(LivingEntity entityLivingIn) {
		      super.onKillEntity(entityLivingIn);
		      if (CorruptionSpreadConfig.corruption_entityspread.get()) {
		         
		    	  EntityType<?> infectedVariant = InfectionHandler.GetInfectedVariant(entityLivingIn.getType());
		    	  Entity infected = infectedVariant.create(this.world);
		          infected.copyLocationAndAnglesFrom(entityLivingIn);
		          if(infected instanceof MobEntity) {
		        	  MobEntity i = (MobEntity)infected;
		        	  i.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(infected)), Corruption.INFECTION, (ILivingEntityData)null, (CompoundNBT)null);
		          }
		          entityLivingIn.remove();
		    	  
		          
		         this.world.addEntity(infected);
		         this.world.playSound((PlayerEntity)null,infected.getPosition(), SoundRegistry.MISC_INFECTED_CONVERT, SoundCategory.HOSTILE, 1.0f, 1.0f);
		      }

		   }

}
