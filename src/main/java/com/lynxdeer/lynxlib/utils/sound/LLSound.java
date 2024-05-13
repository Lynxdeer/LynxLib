package com.lynxdeer.lynxlib.utils.sound;


import com.lynxdeer.lynxlib.LynxLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LLSound {
	
	private final String sound;
	private final float pitch, volume;
	private final int delay;
	
	public LLSound(String sound, float volume, float pitch, int delay) {
		this.sound = sound;
		this.pitch = pitch;
		this.volume = volume;
		this.delay = delay;
	}
	public LLSound(String sound, float volume, float pitch) {
		this(sound, volume, pitch, 0);
	}
	public LLSound(String sound, float volume) {
		this(sound, 1, volume, 0);
	}
	
	
	public void play(Location loc) {
		if (this.delay > 0) Bukkit.getScheduler().runTaskLater(LynxLib.getLLPlugin(), () -> loc.getWorld().playSound(loc, sound, volume, pitch), delay);
		else loc.getWorld().playSound(loc, sound, volume, pitch);
	}
	
	public void playMono(Player p) {
		if (this.delay > 0) Bukkit.getScheduler().runTaskLater(LynxLib.getLLPlugin(), () -> p.playSound(p, sound,1000, pitch), delay);
		else p.playSound(p, sound, 1000, pitch);
	}
	
	
	
}
