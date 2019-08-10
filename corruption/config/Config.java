package com.corruption.config;

import java.io.File;

import com.corruption.Corruption;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {

	private static final ForgeConfigSpec.Builder server_builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec server_config;
	
	private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec client_config;
	
	static 
	{
		CorruptionSpreadConfig.init(server_builder, client_builder);
		
		server_config = server_builder.build();
		client_config = client_builder.build();
	}
	
	public static void LoadConfig(ForgeConfigSpec config, String path) {
		Corruption.LOGGER.info("Loading config: "+path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		Corruption.LOGGER.info("Build config @: "+path);
		file.load();
		Corruption.LOGGER.info("Config loaded @ "+path );
		config.setConfig(file);
	}
	
}
