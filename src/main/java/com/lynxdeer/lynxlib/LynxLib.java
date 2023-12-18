package com.lynxdeer.lynxlib;

import com.lynxdeer.lynxlib.commands.LynxLibCommand;
import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import com.lynxdeer.lynxlib.utils.display.physics.PhysicsObject;
import com.lynxdeer.lynxlib.utils.packets.Glowing;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class LynxLib {
	
	private static JavaPlugin currentPlugin;
	public static int tickRate;
	
	public static void init(JavaPlugin plugin) {
		tickRate = 20;
		PhysicsObject.gravityRate = 9.8f/tickRate;
		currentPlugin = plugin;
		Glowing.registerGlowPacketHandler();
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			
			commandMap.register("lynxlib", new LynxLibCommand("lynxlib").setDescription("General LynxLib command."));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static JavaPlugin getCurrentPlugin() {
		return currentPlugin;
	}
	
}
