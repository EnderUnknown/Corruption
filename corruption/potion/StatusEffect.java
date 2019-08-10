package com.corruption.potion;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class StatusEffect extends Effect{

	protected StatusEffect(EffectType typeIn, int liquidColorIn, Item curativeItem) {
		super(typeIn, liquidColorIn);
		getCurativeItems().clear();
		if(curativeItem !=null)
			getCurativeItems().add(new ItemStack(curativeItem));
		
	}
}
