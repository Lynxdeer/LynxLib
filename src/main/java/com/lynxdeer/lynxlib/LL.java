package com.lynxdeer.lynxlib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.logging.Level;

public class LL {
	
	// General methods I use in every project anyway.
	
	public static void debug(Object s) {
		if (s instanceof Location) s = "x" + ((Location) s).getX() + " y" + ((Location) s).getY() + " z" + ((Location) s).getZ();
		if (s instanceof Vector) s = "x" + ((Vector) s).getX() + " y" + ((Vector) s).getY() + " z" + ((Vector) s).getZ();
		for (Player p : Bukkit.getOnlinePlayers()) if (p.getName().equalsIgnoreCase("Lynxdeer")) p.sendMessage("[Debug] " + s);
		Bukkit.getLogger().log(Level.INFO, "[Debug] " + s);
	}
	
}
