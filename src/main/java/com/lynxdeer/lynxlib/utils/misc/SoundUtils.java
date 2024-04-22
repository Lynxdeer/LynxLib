package com.lynxdeer.lynxlib.utils.misc;


import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.Random;

public class SoundUtils {
	
	public static void globalSound(Location loc, String sound, float volume, float pitch) {
		loc.getWorld().playSound(loc, sound, volume, pitch);
	}
	
	public static void globalSound(Location loc, Sound sound, float volume, float pitch) {
		loc.getWorld().playSound(loc, sound, volume, pitch);
	}
	
	/**
	 * Returns a number between (origin - deviance/2) and (origin + deviance/2).
	 * Specifically useful for getting a random pitch.
	 * For example, with an origin of 1 and deviance of 0.1, it can return a number between 0.95 and 1.05.
	 */
	public static float randomDeviation(float origin, float deviance) {
		return origin + new Random().nextFloat()*deviance - deviance/2;
	}
	
	/**
	 * Returns a number between (origin - deviance/2) and (origin + deviance/2).
	 * Specifically useful for getting a random pitch.
	 * For example, with an origin of 1 and deviance of 0.1, it can return a number between 0.95 and 1.05.
	 */
	public static float randomDeviation(float deviance) {
		return randomDeviation(1, deviance);
	}
	
	
}
