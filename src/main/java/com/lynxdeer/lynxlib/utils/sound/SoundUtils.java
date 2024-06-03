package com.lynxdeer.lynxlib.utils.sound;


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
	 * Returns a number between (origin - deviance) and (origin + deviance).
	 * Specifically useful for getting a random pitch.
	 * For example, with an origin of 1 and deviance of 0.1, it can return a number between 0.9 and 1.1.
	 */
	public static float randomDeviation(float origin, float deviance) {
		return origin + new Random().nextFloat()*(deviance*2) - deviance;
	}
	
	/**
	 * Returns a number between (origin - deviance) and (origin + deviance).
	 * Specifically useful for getting a random pitch.
	 * For example, with an origin of 1 and deviance of 0.1, it can return a number between 0.9 and 1.1.
	 */
	public static float randomDeviation(float deviance) {
		return randomDeviation(1, deviance);
	}
	
	
}
