package com.lynxdeer.lynxlib.utils.sound;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MultiSound {
	
	public LLSound[] sounds;
	
	public MultiSound(LLSound... sounds) {
		this.sounds = sounds;
	}
	
	public void play(Location loc) {
		for (LLSound sound : this.sounds) {
			sound.play(loc);
		}
	}
	
	public void play(Player p) {
		for (LLSound sound : this.sounds) {
			sound.playMono(p);
		}
	}
	
}
