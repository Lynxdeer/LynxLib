package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PhysicsObject extends LynxDisplay {
	
	private Vector3f velocity;
	private Vector3f torque;
	
	public Matrix3f mass;
	public boolean collision = true;
	public boolean collidesWithOtherPhysicsObjects = false; // add support for this later, too expensive for now
	
	public static float gravityRate = 0.49f; // Set in LynxLib#init, if not set it just defaults to 0.49f with no regard for server tick rate
	
	
	public PhysicsObject(Location loc, ItemStack item) {
		this(loc, item, 1);
	}
	
	public PhysicsObject(Location loc, ItemStack item, float mass) {
		super(loc, item);
		
		this.mass = new Matrix3f(mass, mass, mass, mass, mass, mass, mass, mass, mass); // TODO! Do some actual mass matrix calculations
		this.velocity = new Vector3f(0, 0, 0);
		this.torque = new Vector3f(0, 0, 0);
		
		DisplayUtils.physicsObjects.add(this);
		this.tick();
		
		if (PhysicsUtils.physicsRunnable == null) {
			PhysicsUtils.startPhysicsRunnable();
		}
	}
	
	public void tick() {
		for (int i = 0; i < PhysicsUtils.collisionAccuracy; i++) {
			
			velocity.sub(0, gravityRate, 0);
			beforeRotation = new Quaternionf(afterRotation); // I hope I don't have to clone the quaternionf. That would be stupid asf, I doubt I do, but it needs testing.
			afterRotation.add( new Quaternionf().rotateXYZ(torque.x, torque.y, torque.z));
			
			move(velocity, LynxLib.tickRateMillis);
			rotate(new Quaternionf().rotateXYZ(torque.x, torque.y, torque.z), LynxLib.tickRateMillis);
			
			checkCollisions();
		}
		if (this.getLocation().getY() < -128) {
			this.display.remove();
			DisplayUtils.physicsObjects.remove(this);
		}
	}
	
	public void checkCollisions() {
	
	}
	
	public void applySimpleVelocity(Vector3f velocity) {
		velocity.add(velocity);
	}
	
	public void applyVelocity(float power, Location source) {
		Vector3f dir = source.subtract(getLocation()).toVector().toVector3f();
		velocity.add( dir.normalize().mul(power) );
		torque.add(PhysicsUtils.calculateTorque(velocity, dir, mass));
	}
	
}
