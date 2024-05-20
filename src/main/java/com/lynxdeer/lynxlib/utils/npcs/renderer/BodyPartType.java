package com.lynxdeer.lynxlib.utils.npcs.renderer;

import org.joml.Vector3f;

public enum BodyPartType {
	HEAD,
	
	CHEST,
	WAIST,
	HIP,
	
	LEFT_ARM,
	LEFT_FOREARM,
	
	RIGHT_ARM,
	RIGHT_FOREARM,
	
	LEFT_LEG,
	LEFT_FORELEG,
	
	RIGHT_LEG,
	RIGHT_FORELEG;
	
	public Vector3f getTransform() {
		return switch (this) {
			case HEAD ->         new Vector3f(0, 1.9f, 0);
			case CHEST ->        new Vector3f(0, 1.4f, 0);
			case WAIST ->        new Vector3f(0, 0.9f, 0);
			case HIP ->          new Vector3f(0 ,0.7f, 0);
			case LEFT_ARM ->     new Vector3f(0.4f, 1.4f, 0);
			case LEFT_FOREARM -> new Vector3f(0.4f, 0.9f, 0);
			case RIGHT_ARM ->    new Vector3f(-0.4f, 1.4f, 0);
			case RIGHT_FOREARM ->new Vector3f(-0.4f, 0.9f, 0);
			case LEFT_LEG ->     new Vector3f(0.125f, 0.625f, 0);
			case LEFT_FORELEG -> new Vector3f(0.125f, 0.125f, 0);
			case RIGHT_LEG ->    new Vector3f(-0.125f, 0.625f, 0);
			case RIGHT_FORELEG ->new Vector3f(-0.125f, 0.125f, 0);
		};
	}
	
	public Vector3f getPivotPoint() {
		return switch (this) {
			case HEAD -> new Vector3f(0, 0.5f, 0);
			case CHEST -> new Vector3f(0, 0.5f, 0);
			case WAIST -> new Vector3f(0, 0.25f, 0);
			case HIP -> null;
			case LEFT_ARM -> new Vector3f(0.125f, 0, 0);
			case RIGHT_ARM -> new Vector3f(-0.125f, 0, 0);
			case LEFT_FOREARM -> new Vector3f(0.125f, -0.5f, 0);
			case RIGHT_FOREARM -> new Vector3f(-0.125f, -0.5f, 0);
			case LEFT_LEG, LEFT_FORELEG, RIGHT_LEG, RIGHT_FORELEG ->  new Vector3f(0, -0.25f, 0);
		};
	}
	
	
	public Vector3f getScale() {
		return switch (this) {
			case HEAD -> new Vector3f(1, 1, 1);
			case CHEST -> new Vector3f(1, 1, 0.5f);
			case WAIST -> new Vector3f(1, 0.5f, 0.5f);
			case HIP -> null;
			case LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG -> new Vector3f(0.5f, 1, 0.5f);
			case LEFT_FOREARM, RIGHT_FOREARM, LEFT_FORELEG, RIGHT_FORELEG -> new Vector3f(0.5f, 0.5f, 0.5f);
		};
	}
}
