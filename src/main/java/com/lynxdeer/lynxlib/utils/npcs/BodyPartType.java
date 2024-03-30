package com.lynxdeer.lynxlib.utils.npcs;

import org.joml.Vector3f;

public enum BodyPartType {
	HEAD,
	
	BODY_TOP,
	BODY_BOTTOM,
	
	LEFT_ARM_TOP,
	LEFT_ARM_BOTTOM,
	
	RIGHT_ARM_TOP,
	RIGHT_ARM_BOTTOM,
	
	LEFT_LEG_TOP,
	LEFT_LEG_BOTTOM,
	
	RIGHT_LEG_TOP,
	RIGHT_LEG_BOTTOM;
	
	public Vector3f getTransform() {
		return switch (this) {
			case HEAD ->            new Vector3f(0, 1.90f, 0);
			case BODY_TOP ->        new Vector3f(0, 1.4f, 0);
			case BODY_BOTTOM ->     new Vector3f(0, 1.75f, 0);
			case LEFT_ARM_TOP ->    new Vector3f(.8f, 1.4f, 0);
			case LEFT_ARM_BOTTOM -> new Vector3f(.8f, 1.8f, 0);
			case RIGHT_ARM_TOP ->   new Vector3f(-.8f, 1.4f, 0);
			case RIGHT_ARM_BOTTOM ->new Vector3f(-.8f, 1.8f, 0);
			case LEFT_LEG_TOP ->    new Vector3f(.25f, .65f, 0);
			case LEFT_LEG_BOTTOM -> new Vector3f(.25f, .3f, 0);
			case RIGHT_LEG_TOP ->   new Vector3f(-.25f, .65f, 0);
			case RIGHT_LEG_BOTTOM ->new Vector3f(-.25f, .3f, 0);
		};
	}
	
	public Vector3f getPivotPoint() {
		return switch (this) {
			case HEAD -> new Vector3f(0, 0.5f, 0);
			case BODY_TOP -> new Vector3f(0, 0.5f, 0);
			case BODY_BOTTOM -> new Vector3f(0, 0.25f, 0);
			case LEFT_ARM_TOP, LEFT_ARM_BOTTOM, RIGHT_ARM_TOP, RIGHT_ARM_BOTTOM ->  new Vector3f(0, -0.5f, 0);
			case LEFT_LEG_TOP, LEFT_LEG_BOTTOM, RIGHT_LEG_TOP, RIGHT_LEG_BOTTOM ->  new Vector3f(0, -0.25f, 0);
			default -> new Vector3f(0, 0, 0); // placeholder for now
		};
	}
	
	public Vector3f getScale() {
		return switch (this) {
			case HEAD -> new Vector3f(1, 1, 1);
			case BODY_TOP -> new Vector3f(1, 1, 0.5f);
			case BODY_BOTTOM -> new Vector3f(1, 0.5f, 0.5f);
			case LEFT_ARM_TOP, RIGHT_ARM_TOP, LEFT_LEG_TOP, RIGHT_LEG_TOP -> new Vector3f(0.5f, 1, 0.5f);
			case LEFT_ARM_BOTTOM, RIGHT_ARM_BOTTOM, LEFT_LEG_BOTTOM, RIGHT_LEG_BOTTOM -> new Vector3f(0.5f, 0.5f, 0.5f);
		};
	}
}
