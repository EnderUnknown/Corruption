package com.corruption;

import com.corruption.block.BlockRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CorruptionBaseCreativeTab extends ItemGroup{

	public CorruptionBaseCreativeTab() {
		super("corruption_base");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Item.BLOCK_TO_ITEM.get(BlockRegistry.EBONOAK_BARK));
	}

}
