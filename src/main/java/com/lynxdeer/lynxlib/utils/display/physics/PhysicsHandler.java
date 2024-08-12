package com.lynxdeer.lynxlib.utils.display.physics;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.lynxdeer.lynxlib.LynxLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PhysicsHandler {
	
	public static PhysicsSpace space;
	private static PhysicsRigidBody floor; // Invisible floor (more efficient than checking blocks)
	
	public static ArrayList<PhysicsObject> objects = new ArrayList<>();
	
	/**
	 * To be registered in the utilizing plugin's main class. Should not be registered multiple times!
	 */
	public static void register(float floorLevel) {
		
		Bukkit.getScheduler().runTask(LynxLib.getLLPlugin(), () -> {
			
			space = new PhysicsSpace(PhysicsSpace.BroadphaseType.DBVT);
			
			// Default: -9.81f
			// Minecraft: -31.36f
			space.setGravity(new Vector3f(0, -17f, 0));
			
			floor = new PhysicsRigidBody(new PlaneCollisionShape(new Plane(Vector3f.UNIT_Y, floorLevel)), PhysicsRigidBody.massForStatic);
			space.addCollisionObject(floor);
		});
		
		new BukkitRunnable() {@Override public void run() {
			
			update(1/20f);
			
		}}.runTaskTimer(LynxLib.getLLPlugin(), 1L, 1L);
		
	}
	
	public static void update(float delta) {
		if (space == null) return;
		
		space.update(delta);
		objects.forEach(PhysicsObject::updateEntity);
	}
	
	public static PhysicsObject getFromDisplay(ItemDisplay display) {
		return objects.stream().filter(p -> p.getDisplay().equals(display)).findFirst().orElse(null);
	}
	

}
