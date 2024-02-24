package com.lynxdeer.lynxlib;

import com.lynxdeer.lynxlib.commands.LynxLibCommand;
import com.lynxdeer.lynxlib.utils.display.physics.PhysicsObject;
import com.lynxdeer.lynxlib.utils.display.physics.PhysicsUtils;
import com.lynxdeer.lynxlib.utils.packets.Glowing;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Collections;

public final class LynxLib {
	
	private static JavaPlugin currentPlugin;
	public static int tickRate = 20;
	public static int tickRateMillis = 50;
	
	public static void init(JavaPlugin plugin) {
		tickRate = 20;
		tickRateMillis = 1000/tickRate;
		PhysicsObject.gravityRate = (float) (9.8/Math.pow(tickRate, 2)/PhysicsUtils.collisionAccuracy);
		currentPlugin = plugin;
		Glowing.registerGlowPacketHandler();
		try {
			final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			
			commandMap.register("lynxlib", new LynxLibCommand("lynxlib").setDescription("General LynxLib command.").setAliases(Collections.singletonList("ll")));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static JavaPlugin getCurrentPlugin() {
		return currentPlugin;
	}
	
}
