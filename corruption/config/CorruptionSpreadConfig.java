package com.corruption.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CorruptionSpreadConfig {

	public static ForgeConfigSpec.BooleanValue corruption_tilespread;
	public static ForgeConfigSpec.BooleanValue corruption_entityspread;
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
		server.comment("Corruption Spread Config");
		corruption_tilespread = server
				.comment("Whether or not Ebonic blocks will spread to nearby blocks when loaded.")
				.define("corruptionspread.corruption_tilespread", true);
		corruption_entityspread = server
				.comment("Whether or not Ebonic Creatures will infect other mobs after killing them.")
				.define("corruptionspread.corruption_entityspread", true);
	}
	
}
