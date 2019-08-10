package com.corruption.client.models;

import com.corruption.entity.InfectedZombie;

import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class InfectedZombieModel extends AbstractZombieModel<InfectedZombie> {

	public InfectedZombieModel() {
	      this(0.0F, false);
	   }

	   public InfectedZombieModel(float modelSize, boolean bool) {
	      super(modelSize, 0.0F, 64, bool ? 32 : 64);
	   }

	   protected InfectedZombieModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
	      super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
	   }

	   
	   public boolean func_212850_a_(InfectedZombie zombie) {
		return zombie.isAggressive();
	}
	
}
   
