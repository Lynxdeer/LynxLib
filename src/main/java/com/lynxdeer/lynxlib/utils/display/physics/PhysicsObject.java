package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PhysicsObject extends LynxDisplay {
	
	private Vector3f velocity;
	private Quaternionf torque;
	
	public double mass = 0;
	
	
	public PhysicsObject(Location loc, ItemStack item, double mass) {
		super(loc, item);
		this.mass = mass;
		this.velocity = new Vector3f(0, 0, 0);
		this.torque = new Quaternionf(0, 0, 0, 1);
		DisplayUtils.physicsObjects.add(this);
	}
	
	public void tick() {
		velocity.sub(0, 0.49f, 0);
		
	}
	
	public void applyVelocity(Vector3f vector) {
		velocity.add(vector);
	}
	
}
