package com.lynxdeer.lynxlib.utils.display;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;


public class LynxDisplay {
	
	public ItemDisplay display;
	
	public Vector3f transform;
	public Quaternionf afterRotation; // (Left rotation)
	public Vector3f scale;
	public Quaternionf beforeRotation; // (Right Rotation)
	
	public HashMap<Transformation, Integer> transformationQueue = new HashMap<>();
	
	public LynxDisplay(Location loc, ItemStack item) {
		display = loc.getWorld().spawn(loc, ItemDisplay.class, i -> {
			i.setItemStack(item);
			// transform
		});
	}
	
	public void move(Location loc, Ease easing) {
	
	}
	
	public void move(Vector vector, Ease easing) {
	
	}
	
}
