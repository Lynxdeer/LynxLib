package com.lynxdeer.lynxlib.utils.misc;


import org.bukkit.Location;

public class LocationUtils {
	
	public static Location stripRotation(Location loc) {
		return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
	}
	
	
}
