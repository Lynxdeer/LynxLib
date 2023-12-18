package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class PhysicsObject extends LynxDisplay {
	
	private Vector3f velocity;
	private Vector3f torque;
	
	public Matrix3f mass;
	
	public static float gravityRate = 0.49f; // Set in LynxLib#init, if not set it just defaults to 0.49f with no regard for server tick rate
	
	
	public PhysicsObject(Location loc, ItemStack item) {
		this(loc, item, 1);
	}
	
	public PhysicsObject(Location loc, ItemStack item, float mass) {
		super(loc, item);
		this.mass = new Matrix3f(mass, mass, mass, mass, mass, mass, mass, mass, mass); // TODO! Do some actual mass matrix calculations lol
		this.velocity = new Vector3f(0, 0, 0);
		this.torque = new Vector3f(0, 0, 0);
		DisplayUtils.physicsObjects.add(this);
	}
	
	public void startTickUpdates() {
		tick();
	}
	
	public void tick() {
		velocity.sub(0, gravityRate, 0);
		beforeRotation = afterRotation; // I hope I don't have to clone the quaternion. That would be stupid asf, I doubt I do, but it needs testing.
		afterRotation.add(DisplayUtils.eulerToQuaternion(torque.x, torque.y, torque.z));
		
	}
	
	public void applyVelocity(Vector3f velocity) {
		velocity.add(velocity);
	}
	
	public void applyVelocity(Vector3f velocity, Location origin) {
		velocity.add(velocity);
		torque.add(PhysicsUtils.calculateTorque(velocity, origin.subtract(getLocation()).toVector().toVector3f(), mass));
	}
	
}
