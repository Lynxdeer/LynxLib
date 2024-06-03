package com.lynxdeer.lynxlib;

import com.lynxdeer.lynxlib.commands.LynxLibCommand;
import com.lynxdeer.lynxlib.commands.WorldsCommand;
import com.lynxdeer.lynxlib.events.LLEvents;
import com.lynxdeer.lynxlib.utils.display.physics.PhysicsObject;
import com.lynxdeer.lynxlib.utils.display.physics.PhysicsUtils;
import com.lynxdeer.lynxlib.utils.npcs.Skin;
import com.lynxdeer.lynxlib.utils.packets.Glowing;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineskin.MineskinClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class LynxLib extends JavaPlugin {
	
	public static MineskinClient mineskinClient;
	
	private static JavaPlugin currentPlugin;
	public static int tickRate = 20;
	public static int tickRateMillis = 50;
	
	@Override
	public void onEnable() {
		
		tickRate = 20;
		tickRateMillis = 1000/tickRate;
		PhysicsObject.gravityRate = (float) (9.8/Math.pow(tickRate, 2)/PhysicsUtils.collisionAccuracy);
		
		currentPlugin = this;
		Glowing.registerGlowPacketHandler();
		
		Path dir = Paths.get("plugins/LynxLib/skins/");
		if (!Files.exists(dir)) try {         Files.createDirectories(dir);          }catch(Exception e){e.printStackTrace();}
		Skin.loadSkins();
		
		getCommand("lynxlib").setExecutor(new LynxLibCommand());
		getCommand("worlds").setExecutor(new WorldsCommand());
		getCommand("worlds").setTabCompleter(new WorldsCommand());
		
		
		Bukkit.getPluginManager().registerEvents(new LLEvents(), this);
		
		try {
			mineskinClient = new MineskinClient("lynxsApiKey", "6174144f5b199c0565c1ad6577536d96a1bb73ff74614ed182380e1755a18e96");
		} catch (NoClassDefFoundError e) {
			Bukkit.getLogger().severe("Failed to register mineskin client. Oh well!");
		}
	}
	
	
	public static JavaPlugin getLLPlugin() {
		return currentPlugin;
	}
	
}
