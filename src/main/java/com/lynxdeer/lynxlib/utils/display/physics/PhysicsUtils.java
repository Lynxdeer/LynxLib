package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Vector3f;

public class PhysicsUtils {
	
	public static BukkitTask physicsRunnable;
	
	public static int collisionAccuracy = 3;
	
	public static Vector3f calculateTorque(Vector3f distance, Vector3f force) {
		
		// Surely it's more complicated than this.
		// I honestly have 0 idea, torque is so confusing
		return new Vector3f(distance).cross(force);
	}
	
	public static void setCollisionAccuracy(int accuracy) {
		collisionAccuracy = accuracy;
		PhysicsObject.gravityRate = 9.8f/LynxLib.tickRate/collisionAccuracy;
	}
	
	public static void startPhysicsRunnable() {
		if (physicsRunnable == null) {
			physicsRunnable = new BukkitRunnable() {
				@Override
				public void run() {
				try {
					
					for (PhysicsObject object : DisplayUtils.physicsObjects)
						if (object != null)
							object.tick();
					
				} catch (Exception ignored) {}
			}}.runTaskTimer(LynxLib.getLLPlugin(), 1L, 1L);
		}
	}
	
	
}
