package com.corruption.network.packets;

import com.google.common.base.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketPedestal {
	private final ItemStack item;
	
	public PacketPedestal(ItemStack stack) {
		this.item = stack;
	}
	public static void encode(PacketPedestal msg, PacketBuffer buf) {
		buf.writeItemStack(msg.item);
	}
	public static PacketPedestal decode(PacketBuffer buf) {
		return new PacketPedestal(buf.readItemStack());
	}
	public static void handle(PacketPedestal msg, Supplier<NetworkEvent.Context> context) {
		
	}
}