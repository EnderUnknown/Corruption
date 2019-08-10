package com.corruption.network;

import com.corruption.network.packets.PacketPedestal;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public final class PacketHandler {

	private static final String PROTICOL_VERSION = "1";
    public static SimpleChannel channel = NetworkRegistry.newSimpleChannel(
    		new ResourceLocation("corruption","main")
    		,() -> PROTICOL_VERSION
    		, PROTICOL_VERSION::equals
    		, PROTICOL_VERSION::equals);
    
    public static void register() {
    	int id = 0;
    	
    	channel.registerMessage(id++, PacketPedestal.class, PacketPedestal::encode, PacketPedestal::decode, PacketPedestal::handle);
    }
}