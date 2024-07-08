package com.lynxdeer.lynxlib.utils.npcs.renderer;

import org.joml.Vector3f;

public enum BodyPartType {
	
	PLAYER_ROOT,
	
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
	// Foreleg is not the correct word anatomically (should be calves), but that's what it's called internally in modelengine, so that's what I'm gonna stick with :P
	
	RIGHT_LEG,
	RIGHT_FORELEG;
	
	public Vector3f getTransform() {
		return switch (this) {
			case PLAYER_ROOT -> new Vector3f(0, 0, 0);
			case HEAD ->         new Vector3f(0, 2f, 0);
			case CHEST ->        new Vector3f(0, 1.5f, 0);
			case WAIST ->        new Vector3f(0, 1.25f, 0);
			case HIP ->          new Vector3f(0 ,1.0f, 0);
			case LEFT_ARM ->     new Vector3f(0.4f, 1.5f, 0);
			case LEFT_FOREARM -> new Vector3f(0.4f, 1.0f, 0);
			case RIGHT_ARM ->    new Vector3f(-0.4f, 1.5f, 0);
			case RIGHT_FOREARM ->new Vector3f(-0.4f, 1.0f, 0);
			case LEFT_LEG ->     new Vector3f(0.125f, 0.75f, 0);
			case LEFT_FORELEG -> new Vector3f(0.125f, 0.25f, 0);
			case RIGHT_LEG ->    new Vector3f(-0.125f, 0.75f, 0);
			case RIGHT_FORELEG ->new Vector3f(-0.125f, 0.25f, 0);
		};
	}
	
	public Vector3f getPivotPoint() {
		return switch (this) {
			case PLAYER_ROOT -> new Vector3f(0, 0, 0);
			case HEAD -> new Vector3f(0, 0.5f, 0);
			case CHEST -> new Vector3f(0, 0, 0);
			case WAIST -> new Vector3f(0, 0, 0);
			case HIP -> new Vector3f(0, 0, 0);
			case LEFT_ARM -> new Vector3f(0.125f, 0, 0);
			case RIGHT_ARM -> new Vector3f(-0.125f, 0, 0);
			case LEFT_FOREARM -> new Vector3f(0.125f, -0.5f, 0);
			case RIGHT_FOREARM -> new Vector3f(-0.125f, -0.5f, 0);
			case LEFT_LEG, LEFT_FORELEG, RIGHT_LEG, RIGHT_FORELEG ->  new Vector3f(0, -0.25f, 0);
		};
	}
	
	
	public Vector3f getScale() {
		return switch (this) {
			case PLAYER_ROOT -> new Vector3f(0, 0, 0);
			case HEAD -> new Vector3f(1, 1, 1);
			case CHEST, WAIST, HIP -> new Vector3f(1, 0.5f, 0.5f);
			case LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG -> new Vector3f(0.5f, 1, 0.5f);
			case LEFT_FOREARM, RIGHT_FOREARM, LEFT_FORELEG, RIGHT_FORELEG -> new Vector3f(0.5f, 0.5f, 0.5f);
		};
	}
	
	public BodyPartType[] getChildren() {
		return switch (this) {
			
			case PLAYER_ROOT -> BodyPartType.values();
			case HIP ->         new BodyPartType[] { HIP, WAIST, CHEST, HEAD, LEFT_ARM, LEFT_FOREARM, RIGHT_ARM, RIGHT_FOREARM };
			case WAIST ->       new BodyPartType[] { WAIST, CHEST, HEAD, LEFT_ARM, LEFT_FOREARM, RIGHT_ARM, RIGHT_FOREARM };
			case CHEST ->       new BodyPartType[] { CHEST, HEAD, LEFT_ARM, LEFT_FOREARM, RIGHT_ARM, RIGHT_FOREARM };
			case HEAD ->        new BodyPartType[] { HEAD };
			case LEFT_ARM ->    new BodyPartType[] { LEFT_ARM, LEFT_FOREARM };
			case LEFT_FOREARM ->new BodyPartType[] { LEFT_FOREARM };
			case RIGHT_ARM ->   new BodyPartType[] { RIGHT_ARM, RIGHT_FOREARM };
			case RIGHT_FOREARM->new BodyPartType[] { RIGHT_FOREARM };
			case LEFT_LEG ->    new BodyPartType[] { LEFT_LEG, LEFT_FORELEG };
			case LEFT_FORELEG ->new BodyPartType[] { LEFT_FORELEG };
			case RIGHT_LEG ->   new BodyPartType[] { RIGHT_LEG, RIGHT_FORELEG };
			case RIGHT_FORELEG->new BodyPartType[] { RIGHT_FORELEG };
			
		};
	}
}
