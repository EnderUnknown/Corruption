package com.corruption.item;

import com.corruption.potion.EffectRegistry;
import com.corruption.world.biome.BiomeRegister;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class BoonOfEntropy extends Item{

	public BoonOfEntropy(Properties properties) {
		super(properties);
		
	}
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, worldIn, entity, itemSlot, isSelected);
		if(entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)entity;
			ItemStack hand = player.getHeldItemOffhand();
			if(hand.getItem()==ItemRegistry.BOON_ENTROPY){
				player.addPotionEffect(new EffectInstance(EffectRegistry.EBONIC,1,0,false,false));
				if(worldIn.getBiome(player.getPosition())==BiomeRegister.CORRUPTION_FOREST) {
					if(hand.getCount()>1)
						player.addPotionEffect(new EffectInstance(EffectRegistry.CONTAGIOUS_STRENGTH,1,0,false,false));
				}
			}
		}
	}

}
