package com.corruption.entity;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import com.corruption.Corruption;
import com.corruption.config.CorruptionSpreadConfig;
import com.corruption.entity.ai.CreatureAttributes;
import com.corruption.entity.ai.InfectionHandler;
import com.corruption.potion.EffectRegistry;
import com.corruption.util.SoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class InfectedSpider extends MonsterEntity{

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(InfectedSpider.class, DataSerializers.BYTE);
	protected EntityType<?> jockey = null;
	protected int chance = 100;
	
	public InfectedSpider(EntityType<? extends InfectedSpider> type, World world) {
		super(type, world);
		
	}
	private static final Predicate<LivingEntity> NOT_EBONIC_OR_INFECTED = (predicate) -> {
	      return predicate.getCreatureAttribute() != CreatureAttributes.EBONIC && predicate.getCreatureAttribute() != CreatureAttributes.INFESTED && predicate.getActivePotionEffect(EffectRegistry.EBONIC)==null;
	   };
	
	
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
	    this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
	    this.goalSelector.addGoal(4, new InfectedSpider.AttackGoal(this));
	    this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
	    this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	    this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	    this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class,0,true,false,NOT_EBONIC_OR_INFECTED));
	    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false,false, NOT_EBONIC_OR_INFECTED));
	}
	public double getMountedYOffset() {
		return (double)(this.getHeight()*0.5);
	}
	protected PathNavigator createNavigator(World worldIn) {
	      return new ClimberPathNavigator(this, worldIn);
	   }

    protected void registerData() {
      super.registerData();
      this.dataManager.register(CLIMBING, (byte)0);
    }
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
           this.setBesideClimbableBlock(this.collidedHorizontally);
        }

     }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
    	spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        if (worldIn.getRandom().nextInt(chance) == 0 && jockey!=null) {
           Entity jockeyentity = jockey.create(this.world);
           jockeyentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
           ((MobEntity) jockeyentity).onInitialSpawn(worldIn, difficultyIn, reason, (ILivingEntityData)null, (CompoundNBT)null);
           worldIn.addEntity(jockeyentity);
           jockeyentity.startRiding(this);
        }
        return spawnDataIn;
    }
    
     protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
     }

     protected SoundEvent getAmbientSound() {
        return SoundRegistry.ENTITY_INFECTED_SPIDER_AMBIENT;
     }

     protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundRegistry.ENTITY_INFECTED_SPIDER_AMBIENT;
     }

     protected SoundEvent getDeathSound() {
        return SoundRegistry.ENTITY_INFECTED_SPIDER_DEATH;
     }

     protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundRegistry.ENTITY_INFECTED_SPIDER_WALK, 0.15F, 1.0F);
     }
     public boolean isOnLadder() {
         return this.isBesideClimbableBlock();
      }

      public void setMotionMultiplier(BlockState p_213295_1_, Vec3d p_213295_2_) {
         if (p_213295_1_.getBlock() != Blocks.COBWEB) {
            super.setMotionMultiplier(p_213295_1_, p_213295_2_);
         }

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
      

      public boolean isPotionApplicable(EffectInstance potioneffectIn) {
         if (potioneffectIn.getPotion() == Effects.POISON) {
            net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
         }
         return super.isPotionApplicable(potioneffectIn);
      }
      public boolean isBesideClimbableBlock() {
          return (this.dataManager.get(CLIMBING) & 1) != 0;
       }
      public void setBesideClimbableBlock(boolean climbing) {
          byte b0 = this.dataManager.get(CLIMBING);
          if (climbing) {
             b0 = (byte)(b0 | 1);
          } else {
             b0 = (byte)(b0 & -2);
          }

          this.dataManager.set(CLIMBING, b0);
       }
      protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
          return 0.65F;
       }
      
      
      static class AttackGoal extends MeleeAttackGoal {
          public AttackGoal(InfectedSpider spider) {
             super(spider, 1.0D, true);
          }

          /**
           * Returns whether the EntityAIBase should begin execution.
           */
          public boolean shouldExecute() {
             return super.shouldExecute() && !this.attacker.isBeingRidden();
          }

          /**
           * Returns whether an in-progress EntityAIBase should continue executing
           */
          public boolean shouldContinueExecuting() {
             float f = this.attacker.getBrightness();
             if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget((LivingEntity)null);
                return false;
             } else {
                return super.shouldContinueExecuting();
             }
          }

          protected double getAttackReachSqr(LivingEntity attackTarget) {
             return (double)(4.0F + attackTarget.getWidth());
          }
       }
	
}
