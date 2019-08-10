package com.corruption;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.corruption.block.BlockRegistry;
import com.corruption.client.render.RenderRegistry;
import com.corruption.config.Config;
import com.corruption.entity.EntityRegistry;
import com.corruption.item.ItemRegistry;
import com.corruption.network.PacketHandler;
import com.corruption.potion.EffectRegistry;
import com.corruption.tileentity.TileEntityRegistry;
import com.corruption.util.Features;
import com.corruption.util.ParticleRegistry;
import com.corruption.util.SoundRegistry;
import com.corruption.world.biome.BiomeRegister;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("corruption")
public class Corruption
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup CORRUPTION_BASE = new CorruptionBaseCreativeTab();
    public static SpawnReason INFECTION;
    
    

    public Corruption() {
    	
    	ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.server_config,"corruption-server.toml");
    	ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.client_config,"corruption-client.toml");
    	
    	PacketHandler.register();
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        Config.LoadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve("corruption-client.toml").toString());
        Config.LoadConfig(Config.server_config, FMLPaths.CONFIGDIR.get().resolve("corruption-server.toml").toString());
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    	
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        RenderRegistry.RegisterEntityRegisters();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
        
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
        
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
    	@SubscribeEvent
    	public static void onEffectRegistry(final RegistryEvent.Register<Effect> event) {
    		EffectRegistry.RegisterEffects(event);
    	}
    	@SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
    		EntityRegistry.registerEntity(event);
        }
    	
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            // register a new block here
        	SoundRegistry.RegisterSounds();
            BlockRegistry.registerBlocks(event);
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        	ItemRegistry.RegisterBlockItems(event);
        	ItemRegistry.RegisterItems(event);
        	EntityRegistry.registerEntitySpawnEgg(event);
        }
        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        	TileEntityRegistry.registerBlocks(event);
        	RenderRegistry.RegisterTileEntityRenders();
        }
        
        @SubscribeEvent
        public static void onBiomeRegistry(final RegistryEvent.Register<Biome> event) {
        	Features.RegisterFeatures();
        	BiomeRegister.registerBiomes();	
        }
        @SubscribeEvent
        public static void onParticleRegistry(final RegistryEvent.Register<ParticleType<?>> event) {
        	ParticleRegistry.RegisterParticleTypes(event);
        }
    }
    
    
}
