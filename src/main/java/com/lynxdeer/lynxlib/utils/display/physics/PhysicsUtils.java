package com.lynxdeer.lynxlib.utils.display.physics;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class PhysicsUtils {
	
	// This method was generated using Bard.
	public static Vector3f calculateTorque(Vector3f velocity, Vector3f appliedPoint, Matrix3f inverseInertia) {
		// 1. Cross product with the velocity and a vector representing the offset from the origin
		Vector3f torque = new Vector3f();
		torque.cross(velocity, appliedPoint); // Replace X with the actual offset vector based on your scenario
		
		// 2. Multiply by the inverse inertia tensor to account for the object's resistance to rotation
		torque.mul(inverseInertia);
		
		return torque;
	}
	
	
}
