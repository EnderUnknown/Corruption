package com.corruption.tileentity;

import com.corruption.block.BlockRegistry;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntityRegistry {

	@ObjectHolder ("corruption:pedestal")
	public static TileEntityType<PedestalTileEntity> PEDESTAL;
	@ObjectHolder("corruption:infuser")
	public static TileEntityType<InfuserTileEntity> INFUSER;
	
	public static void registerBlocks(final RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().register(TileEntityType.Builder.create(MagmaPistonTileEntity::new, BlockRegistry.MAGMAMOVINGPISTON).build(null).setRegistryName("magmapiston"));
		event.getRegistry().register(TileEntityType.Builder.create(PedestalTileEntity::new, BlockRegistry.PEDESTAL).build(null).setRegistryName("pedestal"));
		event.getRegistry().register(TileEntityType.Builder.create(InfuserTileEntity::new, BlockRegistry.INFUSER).build(null).setRegistryName("infuser"));
		
	}
}
