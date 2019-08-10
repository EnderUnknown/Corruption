package com.corruption.entity;

import com.corruption.Corruption;
import com.corruption.entity.ai.CreatureAttributes;
import com.corruption.entity.ai.InfectionHandler;
import com.corruption.potion.EffectRegistry;
import com.corruption.util.SoundRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InfectedBiomassPod extends MonsterEntity{

	protected static final DataParameter<Float> PROGRESS = EntityDataManager.createKey(InfectedBiomassPod.class, DataSerializers.FLOAT);
	protected float currentProgress = 0;
	protected int requiredProgress = 100;
	
	public InfectedBiomassPod(EntityType<? extends InfectedBiomassPod> type, World world) {
		super(type, world);
	}
	
	protected int decreaseAirSupply(int air) {
	      return air;
	   }

	public void SetProgress(float progress) {
		this.currentProgress = progress;
	}
	public float GetProgress() {
		return this.currentProgress;
	}
	public void AddProgress(float progress) {
		this.currentProgress += progress;
	}
	
	
	
	@Override
	protected void registerGoals() {
		
	    }
	
	protected void registerData() {
		super.registerData();
		this.dataManager.register(PROGRESS, 0f);
	}
	
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putShort("MaxProgress", (short)this.requiredProgress);
		compound.putShort("CurrentProgress", (short)this.currentProgress);
	
	}
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if(compound.contains("MaxProgress",99)) 
			this.requiredProgress = compound.getShort("MaxProgress");
		if(compound.contains("CurrentProgress",99))
			this.currentProgress = compound.getShort("CurrentProgress");
		
	}
	
	
	public void tick() {
		if(this.isAlive()) {
			if(this.currentProgress < this.requiredProgress) {
				this.currentProgress += (this.inCorruption())? 0.2 : 0.1;
			}
			else {
				this.Hatch();
			}
		}
		super.tick();
	}
	
	public void Hatch() {
		Entity infected = GetInfectedSpawn();
        infected.copyLocationAndAnglesFrom(this);
        if(infected instanceof MobEntity) {
      	  MobEntity i = (MobEntity)infected;
      	  i.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(infected)), Corruption.INFECTION, (ILivingEntityData)null, (CompoundNBT)null);
        }
        this.remove();
  	  
       this.world.addEntity(infected);
       this.world.playSound((PlayerEntity)null,infected.getPosition(), SoundRegistry.MISC_INFECTED_CONVERT, SoundCategory.HOSTILE, 1.0f, 1.0f);//((PlayerEntity)null, 1026, new BlockPos(this), 0);
       this.world.addParticle(ParticleTypes.POOF, infected.posX, infected.posY, infected.posZ, 0.1d, 0.1d, 0.1d);
	}
	
	public Entity GetInfectedSpawn() {
		return InfectionHandler.GetRandomEntity("pod_basic").create(this.world);
	}
	public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        if (potioneffectIn.getPotion() == Effects.WITHER || potioneffectIn.getPotion()==Effects.POISON) {
           net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
           net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
           return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
        }
        return super.isPotionApplicable(potioneffectIn);
     }

	
	
	@Override
	protected void registerAttributes() {
		  super.registerAttributes();
		  this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(0.0D);
	      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.0F);
	      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
	      this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
	      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
	      this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}
	protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
	      return 0.5f;
	   }
	protected SoundEvent getAmbientSound() {
	      return null;
	   }

	   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
	      return SoundEvents.ENTITY_SLIME_HURT;
	   }

	   protected SoundEvent getDeathSound() {
	      return SoundRegistry.MISC_INFECTED_CONVERT;
	   }

	   protected SoundEvent getStepSound() {
	      return SoundEvents.BLOCK_SLIME_BLOCK_STEP;
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
	
	   

}
