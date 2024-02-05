package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class PhysicsUtils {
	
	public static BukkitTask physicsRunnable;
	
	public static int collisionAccuracy = 3;
	
	// This method was generated using Bard.
	public static Vector3f calculateTorque(Vector3f velocity, Vector3f appliedPoint, Matrix3f inverseInertia) {
		// 1. Cross product with the velocity and a vector representing the offset from the origin
		Vector3f torque = new Vector3f();
		torque.cross(velocity, appliedPoint);
		
		// 2. Multiply by the inverse inertia tensor to account for the object's resistance to rotation
		torque.mul(inverseInertia);
		
		return torque;
	}
	
	public static void startPhysicsRunnable() {
		if (physicsRunnable == null) {
			physicsRunnable = new BukkitRunnable() {
				@Override
				public void run() {
				for (PhysicsObject object : DisplayUtils.physicsObjects) {
					if (object != null)
						object.tick();
				}
			}}.runTaskTimer(LynxLib.getCurrentPlugin(), 1L, 1L);
		}
	}
	
	
}
