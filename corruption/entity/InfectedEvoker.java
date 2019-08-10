package com.corruption.entity;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.corruption.Corruption;
import com.corruption.config.CorruptionSpreadConfig;
import com.corruption.entity.ai.CreatureAttributes;
import com.corruption.entity.ai.FollowEntityGoal;
import com.corruption.entity.ai.InfectionHandler;
import com.corruption.potion.EffectRegistry;
import com.corruption.util.SoundRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfectedEvoker extends SpellcastingCreature {

	
	protected int failedTPAttempts = 0;

   public InfectedEvoker(EntityType<? extends InfectedEvoker> p_i50207_1_, World p_i50207_2_) {
      super(p_i50207_1_, p_i50207_2_);
      this.experienceValue = 10;
   }
   private static final Predicate<LivingEntity> NOT_EBONIC_OR_INFECTED = (predicate) -> {
	      return predicate.getCreatureAttribute() != CreatureAttributes.EBONIC && predicate.getCreatureAttribute() != CreatureAttributes.INFESTED && predicate.getActivePotionEffect(EffectRegistry.EBONIC)==null;
	   };

	   
   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(0, new SwimGoal(this));
      this.goalSelector.addGoal(1, new InfectedEvoker.CastingSpellGoal());
      this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, MobEntity.class, 8.0F, 0.6D, 1.0D,NOT_EBONIC_OR_INFECTED));
      this.goalSelector.addGoal(6, new InfectedEvoker.TeleportSpellGoal());
      this.goalSelector.addGoal(4, new InfectedEvoker.SummonSpellGoal());
      this.goalSelector.addGoal(5, new InfectedEvoker.AttackSpellGoal());
      this.goalSelector.addGoal(6, new FollowEntityGoal(this, InfectedBiomassPod.class, 0.75d));
      this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
      this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class,0,true,false,NOT_EBONIC_OR_INFECTED).setUnseenMemoryTicks(300));
	    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false,false, NOT_EBONIC_OR_INFECTED).setUnseenMemoryTicks(300));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
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
		         this.world.playSound((PlayerEntity)null,infected.getPosition(), SoundRegistry.MISC_INFECTED_CONVERT, SoundCategory.HOSTILE, 1.0f, 1.0f);//((PlayerEntity)null, 1026, new BlockPos(this), 0);
		      }

		   }
   protected void registerData() {
      super.registerData();
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if(compound.contains("FailedTeleports"))
    	  this.failedTPAttempts = compound.getInt("FailedTeleports");
   }


   public void writeAdditional(CompoundNBT compound) {
      super.writeAdditional(compound);
      compound.putInt("FailedTeleports", this.failedTPAttempts);
   }

   protected void updateAITasks() {
      super.updateAITasks();
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
   }

 

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_EVOKER_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_EVOKER_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_EVOKER_HURT;
   }

  

   protected SoundEvent getSpellSound() {
      return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
   }

   public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
   }
   class TeleportSpellGoal extends SpellcastingCreature.UseSpellGoal {
	      private TeleportSpellGoal() {
	      }

	      protected int getCastingTime() {
	         return 40;
	      }

	      protected int getCastingInterval() {
	         return 500;
	      }

	      protected void castSpell() {
	    	  BlockPos prevPos = InfectedEvoker.this.getPosition();
	    	  BlockPos target = FindBlock(40);
	    	  if(target != null) {
		    	  boolean flag = InfectedEvoker.this.attemptTeleport((double)target.getX(), (double)target.getY(), (double)target.getZ(), true);
		          if (flag) {
		        	 InfectedEvoker.this.world.playSound((PlayerEntity)null, prevPos.getX(), prevPos.getY(), prevPos.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F);
		             InfectedEvoker.this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
		             InfectedEvoker.this.failedTPAttempts = 0;
		          }
		          else {
		        	  InfectedEvoker.this.failedTPAttempts++;
		        	  if(InfectedEvoker.this.failedTPAttempts>10) {
		        		  InfectedEvoker.this.remove();
		        	  }
		          }
	    	  }
	      }
	      
	      
	      private BlockPos FindBlock(int range) {
	    	  Set<BlockPos> set = Sets.newHashSet();
	   	   List<BlockPos> possibleTeleportlocations = Lists.newArrayList();
	   	      int i = 16;
	   	      for(int j = 0; j < 16; ++j) {
	   	         for(int k = 0; k < 16; ++k) {
	   	            for(int l = 0; l < 16; ++l) {
	   	               if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
	   	                  double d0 = (double)((float)j / 15.0F * 2.0F - 1.0F);
	   	                  double d1 = (double)((float)k / 15.0F * 2.0F - 1.0F);
	   	                  double d2 = (double)((float)l / 15.0F * 2.0F - 1.0F);
	   	                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
	   	                  d0 = d0 / d3;
	   	                  d1 = d1 / d3;
	   	                  d2 = d2 / d3;
	   	                  float f = range * (0.7F + InfectedEvoker.this.world.rand.nextFloat() * 0.6F);
	   	                  double d4 = InfectedEvoker.this.posX;
	   	                  double d6 = InfectedEvoker.this.posY;
	   	                  double d8 = InfectedEvoker.this.posZ;

	   	                  for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
	   	                     BlockPos blockpos = new BlockPos(d4, d6, d8);
	   	                     BlockState blockstate = InfectedEvoker.this.world.getBlockState(blockpos);

	   	                     if(InfectedEvoker.this.world.getBlockState(blockpos).getBlock() != Blocks.AIR && 
	   	                    		InfectedEvoker.this.world.getBlockState(blockpos.up()).getBlock() == Blocks.AIR
	   	                    		&& InfectedEvoker.this.world.getBlockState(blockpos.up().up()).getBlock() == Blocks.AIR);
	   	                    	 set.add(blockpos);

	   	                     d4 += d0 * (double)0.3F;
	   	                     d6 += d1 * (double)0.3F;
	   	                     d8 += d2 * (double)0.3F;
	   	                  }
	   	               }
	   	            }
	   	         }
	   	      }
	   	   possibleTeleportlocations.addAll(set);
	   	   return possibleTeleportlocations.get(InfectedEvoker.this.world.rand.nextInt(possibleTeleportlocations.size())).up();
	      }

	      

	      protected SoundEvent getSpellPrepareSound() {
	         return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
	      }

	      protected SpellcastingCreature.SpellType getSpellType() {
	         return SpellcastingCreature.SpellType.TELEPORT;
	      }
   }
   
   class AttackSpellGoal extends SpellcastingCreature.UseSpellGoal {
      private AttackSpellGoal() {
      }

      protected int getCastingTime() {
         return 40;
      }

      protected int getCastingInterval() {
         return 100;
      }

      protected void castSpell() {
         LivingEntity livingentity = InfectedEvoker.this.getAttackTarget();
         if (InfectedEvoker.this.getDistanceSq(livingentity) < 6.0D) {
        	 List<InfectedBiomassPod> list = InfectedEvoker.this.world.getEntitiesWithinAABB(InfectedBiomassPod.class, InfectedEvoker.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
        	 for(InfectedBiomassPod pod : list) {
        		 pod.Hatch();
        	 }
         } 
         else {
            InfectedEvoker.this.addPotionEffect(new EffectInstance(Effects.INSTANT_HEALTH,1,2,false,true));
         }

      }

      

      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
      }

      protected SpellcastingCreature.SpellType getSpellType() {
         return SpellcastingCreature.SpellType.RAPID_INCUBATION;
      }
   }

   class CastingSpellGoal extends SpellcastingCreature.CastingASpellGoal {
      private CastingSpellGoal() {
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         if (InfectedEvoker.this.getAttackTarget() != null) {
            InfectedEvoker.this.getLookController().setLookPositionWithEntity(InfectedEvoker.this.getAttackTarget(), (float)InfectedEvoker.this.getHorizontalFaceSpeed(), (float)InfectedEvoker.this.getVerticalFaceSpeed());
         }

      }
   }

   class SummonSpellGoal extends SpellcastingCreature.UseSpellGoal {
      private final EntityPredicate field_220843_e = (new EntityPredicate()).setDistance(16.0D).setLineOfSiteRequired().setUseInvisibilityCheck().allowInvulnerable().allowFriendlyFire();

      private SummonSpellGoal() {
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!super.shouldExecute()) {
            return false;
         } else {
            int i = InfectedEvoker.this.world.getTargettableEntitiesWithinAABB(InfectedBiomassPod.class, this.field_220843_e, InfectedEvoker.this, InfectedEvoker.this.getBoundingBox().grow(16.0D)).size();
            return InfectedEvoker.this.rand.nextInt(8) + 1 > i;
         }
      }

      protected int getCastingTime() {
         return 100;
      }

      protected int getCastingInterval() {
         return 340;
      }

      protected void castSpell() {
         for(int i = 0; i < rand.nextInt(3)+3 ; ++i) {
            BlockPos blockpos = (new BlockPos(InfectedEvoker.this)).add(-2 + InfectedEvoker.this.rand.nextInt(5), 1, -2 + InfectedEvoker.this.rand.nextInt(5));
            Entity bioPod = EntityRegistry.INFECTED_BIOMASS_POD.create(InfectedEvoker.this.world);
            bioPod.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
            ((MobEntity) bioPod).onInitialSpawn(InfectedEvoker.this.world, InfectedEvoker.this.world.getDifficultyForLocation(blockpos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);
            InfectedEvoker.this.world.addEntity(bioPod);
         }

      }

      protected SoundEvent getSpellPrepareSound() {
         return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
      }

      protected SpellcastingCreature.SpellType getSpellType() {
         return SpellcastingCreature.SpellType.SUMMON_EGGS_BASIC;
      }
   }


}