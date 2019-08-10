package com.corruption.block;



import com.corruption.tileentity.MagmaPistonTileEntity;
import com.corruption.util.Materials;
import com.corruption.world.trees.EbonoakTree;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.DarkOakTree;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class BlockRegistry {

	@ObjectHolder("corruption:magmapiston")
	public static MagmaPiston MAGMAPISTON;
	@ObjectHolder("corruption:magmasticky_piston")
	public static MagmaPiston MAGMASTICKY_PISTON;
	@ObjectHolder("corruption:magmapiston_head")
	public static MagmaPistonHeadBlock MAGMAPISTON_HEAD;
	@ObjectHolder("corruption:magmamoving_piston")
	public static MagmaMovingPiston MAGMAMOVINGPISTON;
	@ObjectHolder("corruption:ebongrass")
	public static Ebongrass EBONGRASS;
	@ObjectHolder("corruption:ebonoak")
	public static EbonoakLog EBONOAK_LOG;
	@ObjectHolder("corruption:ebonoak_leaves")
	public static EbonoakLeaves EBONOAK_LEAVES;
	@ObjectHolder("corruption:ebonoak_sapling")
	public static EbonicSapling EBONOAK_SAPLING;
	@ObjectHolder("corruption:ebonoak_bark")
	public static EbonoakBark EBONOAK_BARK;
	@ObjectHolder("corruption:ebonoak_planks")
	public static  EbonoakPlanks EBONOAK_PLANKS;
	@ObjectHolder("corruption:void_iron_ore")
	public static VoidIronOre VOID_IRON_ORE;
	@ObjectHolder("corruption:blighted_gold_ore")
	public static BlightedGoldOre BLIGHTED_GOLD_ORE;
	@ObjectHolder("corruption:nullstone_ore")
	public static NullstoneOre NULLSTONE_ORE;
	@ObjectHolder("corruption:abyssal_diamond_ore")
	public static AbyssalDiamondOre ABYSSAL_DIAMOND_ORE;
	@ObjectHolder("corruption:arcane_emerald_ore")
	public static ArcaneEmeraldOre ARCANE_EMERALD_ORE;
	@ObjectHolder("corruption:void_iron_block")
	public static VoidIronBlock VOID_IRON_BLOCK;
	@ObjectHolder("corruption:blighted_gold_block")
	public static BlightedGoldBlock BLIGHTED_GOLD_BLOCK;
	@ObjectHolder("corruption:nullstone_block")
	public static NullstoneBlock NULLSTONE_BLOCK;
	@ObjectHolder("corruption:abyssal_diamond_block")
	public static AbyssalDiamondBlock ABYSSAL_DIAMOND_BLOCK;
	@ObjectHolder("corruption:arcane_emerald_block")
	public static ArcaneEmeraldBlock ARCANE_EMERALD_BLOCK;
	@ObjectHolder("corruption:pedestal")
	public static Pedestal PEDESTAL;
	@ObjectHolder("corruption:infuser")
	public static Infuser INFUSER;
	
	@ObjectHolder("corruption:magmapiston")
	public static TileEntityType<MagmaPistonTileEntity> MAGMAPISTON_TE;
	
	
	
	public static void registerBlocks(final RegistryEvent.Register<Block> event) 
	{
		event.getRegistry().register(new MagmaPiston(false, Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0f)).setRegistryName("magmapiston"));
		event.getRegistry().register(new MagmaPiston(true,Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(2.0f)).setRegistryName("magmasticky_piston"));
		event.getRegistry().register(new MagmaPistonHeadBlock(Properties.create(Material.PISTON).sound(SoundType.STONE).hardnessAndResistance(2.0f)));
		event.getRegistry().register(new MagmaMovingPiston(Properties.create(Material.PISTON).sound(SoundType.STONE).hardnessAndResistance(2.0f)));
		event.getRegistry().register(new Ebongrass(Properties.create(Materials.EBONICGROUND).sound(SoundType.PLANT).hardnessAndResistance(0.8f).tickRandomly().harvestTool(ToolType.SHOVEL)).setRegistryName("ebongrass"));
		event.getRegistry().register(new EbonoakLog(MaterialColor.PURPLE_TERRACOTTA, Properties.create(Materials.EBONWOOD).sound(SoundType.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE)).setRegistryName("ebonoak"));
		event.getRegistry().register(new EbonoakLeaves(Properties.create(Materials.EBONLEAVES).hardnessAndResistance(0.4f).tickRandomly().sound(SoundType.PLANT)).setRegistryName("ebonoak_leaves"));
		event.getRegistry().register(new EbonicSapling(new EbonoakTree(), Properties.create(Materials.EBONICPLANT).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0f).sound(SoundType.PLANT)).setRegistryName("ebonoak_sapling"));
		event.getRegistry().register(new EbonoakBark(Properties.create(Materials.EBONWOOD).sound(SoundType.WOOD).hardnessAndResistance(3.0f).harvestTool(ToolType.AXE)).setRegistryName("ebonoak_bark"));
		event.getRegistry().register(new EbonoakPlanks(Properties.create(Materials.EBONWOOD).sound(SoundType.WOOD).hardnessAndResistance(3.0f,4.0f).harvestTool(ToolType.AXE)).setRegistryName("ebonoak_planks"));
		event.getRegistry().register(new VoidIronOre(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("void_iron_ore"));
		event.getRegistry().register(new BlightedGoldOre(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("blighted_gold_ore"));
		event.getRegistry().register(new NullstoneOre(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("nullstone_ore"));
		event.getRegistry().register(new AbyssalDiamondOre(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("abyssal_diamond_ore"));
		event.getRegistry().register(new ArcaneEmeraldOre(Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(4.0f).harvestTool(ToolType.PICKAXE).harvestLevel(3)).setRegistryName("arcane_emerald_ore"));
		event.getRegistry().register(new VoidIronBlock(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1)).setRegistryName("void_iron_block"));
		event.getRegistry().register(new BlightedGoldBlock(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(4.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)).setRegistryName("blighted_gold_block"));
		event.getRegistry().register(new NullstoneBlock(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE)).setRegistryName("nullstone_block"));
		event.getRegistry().register(new AbyssalDiamondBlock(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)).setRegistryName("abyssal_diamond_block"));
		event.getRegistry().register(new ArcaneEmeraldBlock(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE).harvestLevel(2)).setRegistryName("arcane_emerald_block"));
		event.getRegistry().register(new Pedestal(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE)).setRegistryName("pedestal"));
		event.getRegistry().register(new Infuser(Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(6.0F, 7.0F).harvestTool(ToolType.PICKAXE)).setRegistryName("infuser"));
		
	}
}
