package com.lynxdeer.lynxlib;

import org.bukkit.plugin.java.JavaPlugin;

public final class LynxLib extends JavaPlugin {
	
	private static LynxLib plugin;
	@Override
	public void onEnable() {
		plugin = this;
	}
	
	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}
	
	public static LynxLib getLynxLibPlugin() {return plugin;}
	
}
