package com.corruption.entity.ai;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public class FollowEntityGoal extends Goal {
   private final MobEntity thisEntity;
   private LivingEntity target;
   private Class<?> targetClass;
   private final double moveSpeed;
   private int delayCounter;

   public FollowEntityGoal(MobEntity thisEntity,Class<?> target, double speed) {
      this.thisEntity = thisEntity;
      this.moveSpeed = speed;
      this.targetClass = target; 
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      
         List<MobEntity> list = this.thisEntity.world.getEntitiesWithinAABB((Class<? extends MobEntity>) this.targetClass, this.thisEntity.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
         LivingEntity livingE = null;
         double d0 = Double.MAX_VALUE;

         for(LivingEntity lEntity : list) {
        	 double d1 = this.thisEntity.getDistanceSq(lEntity);
             if (!(d1 > d0)) {
                d0 = d1;
                livingE = lEntity;
            }
         }

         if (livingE == null) {
            return false;
         } else if (d0 < 9.0D) {
            return false;
         } else {
            this.target = livingE;
            return true;
         }
      
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
     if (!this.target.isAlive()) {
         return false;
      } else {
         double d0 = this.thisEntity.getDistanceSq(this.target);
         return !(d0 < 9.0D) && !(d0 > 256.0D);
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.delayCounter = 0;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.target = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = 10;
         this.thisEntity.getNavigator().tryMoveToEntityLiving(this.target, this.moveSpeed);
      }
   }
}