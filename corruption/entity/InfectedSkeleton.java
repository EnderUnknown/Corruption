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
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class InfectedSkeleton extends MonsterEntity implements IRangedAttackMob{

	private static final Predicate<LivingEntity> NOT_EBONIC_OR_INFECTED = (predicate) -> {
	      return predicate.getCreatureAttribute() != CreatureAttributes.EBONIC && predicate.getCreatureAttribute() != CreatureAttributes.INFESTED && predicate.getActivePotionEffect(EffectRegistry.EBONIC)==null;
	   };
	
	private final RangedBowAttackGoal<InfectedSkeleton> aiArrowAttack = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
	   private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
		   public void resetTask() {
			   super.resetTask();
			   InfectedSkeleton.this.setAggroed(false);
		   }
		   public void startExecuting() {
			   super.startExecuting();
			   InfectedSkeleton.this.setAggroed(true);
		   }
		   
	   };
	   protected InfectedSkeleton(EntityType<? extends InfectedSkeleton> type, World  world) {
		   super(type,world);
		   this.setCombatTask();
	   }
	   protected void registerGoals() {
		      this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0D, 1.2D));
		      this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		      this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		      this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		      this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0,true,false,NOT_EBONIC_OR_INFECTED));
		      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false,false, NOT_EBONIC_OR_INFECTED));
		   }

		   protected void registerAttributes() {
		      super.registerAttributes();
		      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
		   }
		   protected void playStepSound(BlockPos pos, BlockState blockIn) {
			      this.playSound(SoundRegistry.ENTITY_INFECTED_SKELETON_STEP, 0.15F, 1.0F);
			   }


		   protected SoundEvent getAmbientSound() {
			      return SoundRegistry.ENTITY_INFECTED_SKELETON_AMBIENT;
			   }

		   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		      return SoundRegistry.ENTITY_INFECTED_SKELETON_HURT;
		   }

		   protected SoundEvent getDeathSound() {
		      return SoundRegistry.ENTITY_INFECTED_SKELETON_DEATH;
		   }

			   public CreatureAttribute getCreatureAttribute() {
			      return CreatureAttributes.INFESTED;
			   }
		   public void updateRidden() {
			      super.updateRidden();
			      if (this.getRidingEntity() instanceof CreatureEntity) {
			         CreatureEntity creatureentity = (CreatureEntity)this.getRidingEntity();
			         this.renderYawOffset = creatureentity.renderYawOffset;
			      }

			   }
		   @Nullable
		   public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
			spawnDataIn =  super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
			this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
			this.setCombatTask();
			return spawnDataIn;
		   }
		 

		   
		   public void setCombatTask() {
			      if (this.world != null && !this.world.isRemote) {
			         this.goalSelector.removeGoal(this.aiAttackOnCollide);
			         this.goalSelector.removeGoal(this.aiArrowAttack);
			         ItemStack itemstack = this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW));
			         if (itemstack.getItem() instanceof net.minecraft.item.BowItem) {
			            int i = 20;
			            if (this.world.getDifficulty() != Difficulty.HARD) {
			               i = 40;
			            }

			            this.aiArrowAttack.setAttackCooldown(i);
			            this.goalSelector.addGoal(4, this.aiArrowAttack);
			         } else {
			            this.goalSelector.addGoal(4, this.aiAttackOnCollide);
			         }

			      }
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
		   public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
			      ItemStack itemstack = this.findAmmo(this.getHeldItem(ProjectileHelper.getHandWith(this, Items.BOW)));
			      AbstractArrowEntity abstractarrowentity = this.func_213624_b(itemstack, distanceFactor);
			      if (this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.BowItem)
			         abstractarrowentity = ((net.minecraft.item.BowItem)this.getHeldItemMainhand().getItem()).customeArrow(abstractarrowentity);
			      double d0 = target.posX - this.posX;
			      double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - abstractarrowentity.posY;
			      double d2 = target.posZ - this.posZ;
			      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
			      abstractarrowentity.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
			      this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			      this.world.addEntity(abstractarrowentity);
			   }

			   protected AbstractArrowEntity func_213624_b(ItemStack p_213624_1_, float p_213624_2_) {
			      return ProjectileHelper.func_221272_a(this, p_213624_1_, p_213624_2_);
			   }
			   public void readAdditional(CompoundNBT compound) {
				      super.readAdditional(compound);
				      this.setCombatTask();
				   }

				   public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
				      super.setItemStackToSlot(slotIn, stack);
				      if (!this.world.isRemote) {
				         this.setCombatTask();
				      }

				   }

				   protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
				      return 1.74F;
				   }

				   public double getYOffset() {
				      return -0.6D;
				   }
}
