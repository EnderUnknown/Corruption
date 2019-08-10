package com.corruption.client.models;

import com.corruption.entity.InfectedPigman;

import net.minecraft.client.renderer.entity.model.AbstractZombieModel;

public class InfectedPigmanModel extends AbstractZombieModel<InfectedPigman> {

	public InfectedPigmanModel() {
	      this(0.0F, false);
	   }

	   public InfectedPigmanModel(float modelSize, boolean bool) {
	      super(modelSize, 0.0F, 64, bool ? 32 : 64);
	   }

	   protected InfectedPigmanModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
	      super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
	   }

	   public boolean func_212850_a_(InfectedPigman zombie) {
			return zombie.isAggressive();
		}

}
