package com.corruption.entity.ai.goals;

import java.util.EnumSet;

import com.corruption.entity.InfectedCreeper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class InfectedCreeperSwellGoal extends Goal{

	private final InfectedCreeper entity;
	private LivingEntity livingEntity;
	
	public InfectedCreeperSwellGoal(InfectedCreeper entity) {
		this.entity = entity;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}
	public boolean shouldExecute() {
		LivingEntity l = this.entity.getAttackTarget();
		return this.entity.getCreeperState() > 0 || l != null && this.entity.getDistanceSq(l)<9.0D;
	}
	
	public void startExecuting() {
		this.entity.getNavigator().clearPath();
		this.livingEntity = this.entity.getAttackTarget();
	}
	
	public void resetTask() {
		this.livingEntity = null;
	}
	
	public void tick() {
		if(this.livingEntity==null) 
			this.entity.setCreeperState(-1);
		else if(this.entity.getDistanceSq(livingEntity)>49.0D)
			this.entity.setCreeperState(-1);
		else if(!this.entity.getEntitySenses().canSee(this.livingEntity))
			this.entity.setCreeperState(-1);
		else {
			this.entity.setCreeperState(1);
		}
	}
}
