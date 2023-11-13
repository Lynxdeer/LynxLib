package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.joml.Vector3f;

public class PhysicsObject extends LynxDisplay {
	
	public Vector3f velocity;
	
	public PhysicsObject(Location loc, ItemStack item) {
		super(loc, item);
	}
	
	public void tick() {
	
	}
	
	public void applyVelocity(Vector3f vector) {
	
	}
	
}
