package com.corruption.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class EffectRegistry {

	 public static final Effect CONTAGIOUS_STRENGTH = register(0, "contagious_strength", (new StatusEffect(EffectType.BENEFICIAL, 5582978,null)).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 1.0D, AttributeModifier.Operation.ADDITION).setRegistryName("contagious_strength"));
	 public static final Effect EBONIC = register(1,"ebonic",(new StatusEffect(EffectType.BENEFICIAL,5582978,null)).setRegistryName("ebonic"));
	 
	public static void RegisterEffects(final RegistryEvent.Register<Effect> event) {
		//event.getRegistry().register(CONTAGIOUS_STRENGTH);
	}
	
	private static Effect register(int id, String key, Effect effectIn) {
	      return Registry.register(Registry.EFFECTS, id, key, effectIn);
	   }
}
