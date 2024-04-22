package com.lynxdeer.lynxlib.utils.misc;

import org.bukkit.Material;
import org.bukkit.Sound;

public class BlockUtils {
	
	public static Sound getMaterialSound(Material material) {
		return (material.createBlockData().getSoundGroup().getBreakSound());
	}
	
	
}
