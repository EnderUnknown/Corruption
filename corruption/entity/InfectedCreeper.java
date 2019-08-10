package com.corruption.entity;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.corruption.Corruption;
import com.corruption.config.CorruptionSpreadConfig;
import com.corruption.entity.ai.CreatureAttributes;
import com.corruption.entity.ai.InfectionHandler;
import com.corruption.entity.ai.goals.InfectedCreeperSwellGoal;
import com.corruption.potion.EffectRegistry;
import com.corruption.util.SoundRegistry;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfectedCreeper extends MonsterEntity{

	private static final DataParameter<Integer> STATE = EntityDataManager.createKey(InfectedCreeper.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(InfectedCreeper.class,DataSerializers.BOOLEAN);
	
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 2;
	
	public InfectedCreeper(EntityType<? extends InfectedCreeper> type, World world) {
		super(type,world);
	}
	
	private static final Predicate<LivingEntity> NOT_EBONIC_OR_INFECTED = (predicate) -> {
	      return predicate.getCreatureAttribute() != CreatureAttributes.EBONIC && predicate.getCreatureAttribute() != CreatureAttributes.INFESTED && predicate.getActivePotionEffect(EffectRegistry.EBONIC)==null;
	   };
	   private static final EntityPredicate ebonicinfestedFilter = (new EntityPredicate()).setCustomPredicate(NOT_EBONIC_OR_INFECTED);
	 
	
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new InfectedCreeperSwellGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this,OcelotEntity.class, 6.0f,1.0D,1.2D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0f, 1.0D, 1.2D));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
	    this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
	    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false,false, NOT_EBONIC_OR_INFECTED));
	}
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
	}
	public int getMaxFallHeight() {
		return this.getAttackTarget() == null ? 3 : 3+(int)(this.getHealth()-1.0f);
	}
	
	public void fall(float distance, float multiplier) {
		super.fall(distance, multiplier);
		this.timeSinceIgnited = (int)((float)this.timeSinceIgnited+distance * 1.5F);
		if(this.timeSinceIgnited > this.fuseTime - 5)
			 this.timeSinceIgnited = this.fuseTime-5;
}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(STATE, -1);
		this.dataManager.register(IGNITED, false);
	}
	public void writeAdditional(CompoundNBT compound) {
	      super.writeAdditional(compound);

	      compound.putShort("Fuse", (short)this.fuseTime);
	      compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
	      compound.putBoolean("ignited", this.hasIgnited());
	   }

	   
	   public void readAdditional(CompoundNBT compound) {
	      super.readAdditional(compound);
	      if (compound.contains("Fuse", 99)) {
	         this.fuseTime = compound.getShort("Fuse");
	      }

	      if (compound.contains("ExplosionRadius", 99)) {
	         this.explosionRadius = compound.getByte("ExplosionRadius");
	      }

	      if (compound.getBoolean("ignited")) {
	         this.ignite();
	      }

	   }
	public void tick() {
		if(this.isAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;
			if(this.hasIgnited())
				this.setCreeperState(1);
			int i = this.getCreeperState();
			if(i>0&&this.timeSinceIgnited == 0)
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5f);
			this.timeSinceIgnited += i;
			if(this.timeSinceIgnited<0)
				this.timeSinceIgnited = 0;
			
			if(this.timeSinceIgnited>=this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}
		super.tick();
	}
	public CreatureAttribute getCreatureAttribute() {
	      return CreatureAttributes.INFESTED;
	   }
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_CREEPER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_CREEPER_DEATH;
   }
   public boolean attackEntityAsMob(Entity entity) {
	   return true;
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
   
   
   @OnlyIn(Dist.CLIENT)
   public float getCreeperFlashIntensity(float partialTicks) {
	   return MathHelper.lerp(partialTicks, (float)this.lastActiveTime, (float)this.timeSinceIgnited) / (float)(this.fuseTime-2);
   }
   
   public void  setCreeperState(int state) {
	   this.dataManager.set(STATE, state);
   }
   public int getCreeperState() {
	   return this.dataManager.get(STATE);
   }
   
   protected boolean processInteract(PlayerEntity player, Hand hand) {
	   ItemStack stack = player.getHeldItem(hand);
	   if(stack.getItem()==Items.FLINT_AND_STEEL) { //Needs to be changed to flint and void steel
		   this.world.playSound(player, this.posX, this.posY,this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE,this.getSoundCategory(),1.0F,this.rand.nextFloat() * 0.4F + 0.8F);
		   player.swingArm(hand);
		   if(!this.world.isRemote) {
			   this.ignite();
			   stack.damageItem(1, player, (item) ->{
				   item.sendBreakAnimation(hand);
			   });
			   return true;
		   }
	   }
	   return super.processInteract(player, hand);
   }
   private void explode() {
	   if(!this.world.isRemote) {
		   //Explosion.Mode mode = Explosion.Mode.NONE;
		   this.dead = true;
		  // this.world.createExplosion(this, this.posX, this.posY, this.posZ, 0f, mode);
		   this.playSound(SoundRegistry.MISC_INFECTED_CONVERT, 1F, 1.0F);
		   Contaminate();
		   this.remove();
	   }
	   if(this.world.isRemote) {
		   Particles();
	   }
   }
   
   @OnlyIn(Dist.CLIENT)
   private void Particles() {
	   this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
   }
   
   private void Contaminate() {
	   Set<BlockPos> set = Sets.newHashSet();
	   List<BlockPos> contaminatedBlockPos = Lists.newArrayList();
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
	                  float f = this.explosionRadius * (0.7F + this.world.rand.nextFloat() * 0.6F);
	                  double d4 = this.posX;
	                  double d6 = this.posY;
	                  double d8 = this.posZ;

	                  for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
	                     BlockPos blockpos = new BlockPos(d4, d6, d8);
	                     BlockState blockstate = this.world.getBlockState(blockpos);

	                     set.add(blockpos);

	                     d4 += d0 * (double)0.3F;
	                     d6 += d1 * (double)0.3F;
	                     d8 += d2 * (double)0.3F;
	                  }
	               }
	            }
	         }
	      }
	      contaminatedBlockPos.addAll(set);
	      for(BlockPos blockPos: contaminatedBlockPos) {
	    	  BlockState state = this.world.getBlockState(blockPos);
	    	  //Block block = state.getBlock();
	    	  if(!state.isAir(this.world,blockPos)) {
	    		  if(CorruptionSpreadConfig.corruption_tilespread.get() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
	    			  BlockState corruptState = InfectionHandler.GetEbonicVariant(state);
	    			  if(corruptState!=null) {
	    				  this.world.setBlockState(blockPos, corruptState);
	    			  }
	    		  }
	    	  }
	      }
	      float f3 = this.explosionRadius * 2.0F;
	      int k1 = MathHelper.floor(this.posX - (double)f3 - 1.0D);
	      int l1 = MathHelper.floor(this.posX + (double)f3 + 1.0D);
	      int i2 = MathHelper.floor(this.posY - (double)f3 - 1.0D);
	      int i1 = MathHelper.floor(this.posY + (double)f3 + 1.0D);
	      int j2 = MathHelper.floor(this.posZ - (double)f3 - 1.0D);
	      int j1 = MathHelper.floor(this.posZ + (double)f3 + 1.0D);
	      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
	      Vec3d vec = new Vec3d(this.posX,this.posY,this.posZ);
	      
	      for(int z = 0; z < list.size(); z++) {
	    	  Entity entity = list.get(z);
	    	  double d = (double)(MathHelper.sqrt(entity.getDistanceSq(new Vec3d(this.posX,this.posY,this.posZ)))/f3);
	    	  if(d<1.0D) {
	    		  double d5 = entity.posX - this.posX;
	               double d7 = entity.posY + (double)entity.getEyeHeight() - this.posY;
	               double d9 = entity.posZ - this.posZ;
	               double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
	               if (d13 != 0.0D) {
	                  d5 = d5 / d13;
	                  d7 = d7 / d13;
	                  d9 = d9 / d13;
	                  double d14 = (double)Explosion.func_222259_a(vec, entity);
	                  double d10 = (1.0D - d) * d14;
	                  //Infect Code
	                  if(entity.isLiving()) {
	                  if (CorruptionSpreadConfig.corruption_entityspread.get()&&entity.getType()!=EntityType.PLAYER) {
	         	         
	                	  if(ebonicinfestedFilter.canTarget(this, (LivingEntity)entity)) {
		        	    	  EntityType<?> infectedVariant = InfectionHandler.GetInfectedVariant(entity.getType());
		        	    	  Entity infected = infectedVariant.create(this.world);
		        	          infected.copyLocationAndAnglesFrom(entity);
		        	          if(infected instanceof MobEntity) {
		        	          	  MobEntity I = (MobEntity)infected;
		        	          	  I.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(infected)), Corruption.INFECTION, (ILivingEntityData)null, (CompoundNBT)null);
		        	            }
		        	          entity.remove();
		        	    	  
		        	         this.world.addEntity(infected);
		        	         this.world.playSound((PlayerEntity)null,infected.getPosition(), SoundRegistry.MISC_INFECTED_CONVERT, SoundCategory.HOSTILE, 1.0f, 1.0f);
		                	  }
	                  }
	                  else if(entity instanceof PlayerEntity) {
	                	  PlayerEntity pEntity = (PlayerEntity)entity;
	                	  if(!pEntity.isSpectator()&&(!pEntity.isCreative() || !pEntity.abilities.isFlying)){
	                		  pEntity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
	                	  }
	                  }
	               }
	               }
	    	  }
	      }
   }
   public boolean hasIgnited() {
	   return this.dataManager.get(IGNITED);
   }
   public void ignite() {
	   this.dataManager.set(IGNITED, true);
   }
}