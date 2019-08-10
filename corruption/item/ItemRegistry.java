package com.corruption.item;

import com.corruption.Corruption;
import com.corruption.block.BlockRegistry;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class ItemRegistry {

	@ObjectHolder("corruption:boon_entropy")
	public static BoonOfEntropy BOON_ENTROPY;
	@ObjectHolder("corruption:cursed_slime")
	public static CursedSlime CURSED_SLIME;
	@ObjectHolder("corruption:void_iron")
	public static VoidIron VOID_IRON;
	@ObjectHolder("corruption:blighted_gold")
	public static BlightedGold BLIGHTED_GOLD;
	@ObjectHolder("corruption:nullstone")
	public static NullStone NULLSTONE;
	@ObjectHolder("corruption:abyssal_diamond")
	public static AbyssalDiamond ABYSSAL_DIAMOND;
	@ObjectHolder("corruption:arcane_emerald")
	public static ArcaneEmerald ARCANE_EMERALD;
	
	
	public static void RegisterItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new BoonOfEntropy(new Item.Properties().group(Corruption.CORRUPTION_BASE).maxStackSize(5)).setRegistryName("boon_entropy"));
		event.getRegistry().register(new CursedSlime(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("cursed_slime"));
		event.getRegistry().register(new VoidIron(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("void_iron"));
		event.getRegistry().register(new BlightedGold(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("blighted_gold"));
		event.getRegistry().register(new NullStone(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("nullstone"));
		event.getRegistry().register(new AbyssalDiamond(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("abyssal_diamond"));
		event.getRegistry().register(new ArcaneEmerald(new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("arcane_emerald"));
		
	}
	
	public static void RegisterBlockItems(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new BlockItem(BlockRegistry.MAGMAPISTON, new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("magmapiston"));
		event.getRegistry().register(new BlockItem(BlockRegistry.MAGMASTICKY_PISTON, new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("magmasticky_piston"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONGRASS, new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebongrass"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONOAK_LOG,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebonoak"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONOAK_LEAVES,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebonoak_leaves"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONOAK_SAPLING,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebonoak_sapling"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONOAK_BARK, new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebonoak_bark"));
		event.getRegistry().register(new BlockItem(BlockRegistry.EBONOAK_PLANKS, new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("ebonoak_planks"));
		event.getRegistry().register(new BlockItem(BlockRegistry.VOID_IRON_ORE,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("void_iron_ore"));
		event.getRegistry().register(new BlockItem(BlockRegistry.BLIGHTED_GOLD_ORE,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("blighted_gold_ore"));
		event.getRegistry().register(new BlockItem(BlockRegistry.NULLSTONE_ORE,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("nullstone_ore"));
		event.getRegistry().register(new BlockItem(BlockRegistry.ABYSSAL_DIAMOND_ORE,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("abyssal_diamond_ore"));
		event.getRegistry().register(new BlockItem(BlockRegistry.ARCANE_EMERALD_ORE,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("arcane_emerald_ore"));
		event.getRegistry().register(new BlockItem(BlockRegistry.VOID_IRON_BLOCK,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("void_iron_block"));
		event.getRegistry().register(new BlockItem(BlockRegistry.BLIGHTED_GOLD_BLOCK,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("blighted_gold_block"));
		event.getRegistry().register(new BlockItem(BlockRegistry.NULLSTONE_BLOCK,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("nullstone_block"));
		event.getRegistry().register(new BlockItem(BlockRegistry.ABYSSAL_DIAMOND_BLOCK,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("abyssal_diamond_block"));
		event.getRegistry().register(new BlockItem(BlockRegistry.ARCANE_EMERALD_BLOCK,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("arcane_emerald_block"));
		event.getRegistry().register(new BlockItem(BlockRegistry.PEDESTAL,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("pedestal"));
		event.getRegistry().register(new BlockItem(BlockRegistry.INFUSER,new Item.Properties().group(Corruption.CORRUPTION_BASE)).setRegistryName("infuser"));
		
	}
}
