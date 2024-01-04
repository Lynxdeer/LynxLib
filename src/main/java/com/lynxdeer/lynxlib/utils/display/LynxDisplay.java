package com.lynxdeer.lynxlib.utils.display;

import com.lynxdeer.lynxlib.LL;
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
	
	public Location baseLocation;
	
	public Vector3f transform;
	public Quaternionf afterRotation; // (Left rotation)
	public Vector3f scale;
	public Quaternionf beforeRotation; // (Right Rotation)
	
	public HashMap<Transformation, Integer> transformationQueue = new HashMap<>();
	
	public LynxDisplay(Location loc, ItemStack item) {
		baseLocation = loc;
		display = loc.getWorld().spawn(loc, ItemDisplay.class, i -> {
			i.setItemStack(item);
			// transform
		});
	}
	
	public LynxDisplay(ItemDisplay originalDisplay) {
		display = originalDisplay;
		baseLocation = display.getLocation();
		Transformation transformation = display.getTransformation();
		transform = transformation.getTranslation();
		afterRotation = transformation.getLeftRotation();
		scale = transformation.getScale();
		beforeRotation = transformation.getRightRotation();
	}
	
	public Location getLocation() { // Gets the location added to the transform.
		return baseLocation.clone().add(transform.x, transform.y, transform.z);
	}
	
	public void teleport(Location loc) {
		baseLocation = loc;
		transform = new Vector3f(0, 0, 0);
	}
	
	public void move(Location loc, double duration, Ease easing) {
		Location original = this.getLocation();
		Vector v = original.subtract(loc).toVector();
		this.move(v.toVector3f(), duration, easing);
	}
	
	public void move(Vector3f vector, double duration, Ease easing) {
		
		// TODO: EASING, MAKE LINEAR NOT TICK
		
		this.display.setInterpolationDelay(0);
		this.display.setInterpolationDuration(LL.millisToTicks((int) duration * 1000));
		
		
		this.transform.add(vector);
		this.display.setTransformation(new Transformation(transform, afterRotation, scale, beforeRotation));
	}
	
}
