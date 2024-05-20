package com.lynxdeer.lynxlib.utils.npcs.animation.enums;


/**
 *
 * Note: Not all of these are provided in every animation. If an animation has no keyframes for a bone, it will not be used!
 *
 */
public enum PlayerBone {
	
	PLAYER_ROOT,
	
	HIP,
	WAIST,
	CHEST,
	
	HEAD,
	
	LEFT_ARM,
	LEFT_FOREARM,
	
	RIGHT_ARM,
	RIGHT_FOREARM,
	
	LEFT_LEG,
	LEFT_FORELEG,
	
	RIGHT_LEG,
	RIGHT_FORELEG;
	
	
	public PlayerBone[] getChildren() {
		return switch (this) {
			
			case PLAYER_ROOT -> PlayerBone.values();
			case HIP -> null;
			case WAIST -> null;
			case CHEST -> null;
			case HEAD -> null;
			case LEFT_ARM -> null;
			case LEFT_FOREARM -> null;
			case RIGHT_ARM -> null;
			case RIGHT_FOREARM -> null;
			case LEFT_LEG -> null;
			case LEFT_FORELEG -> null;
			case RIGHT_LEG -> null;
			case RIGHT_FORELEG -> null;
			
		};
	}
	
}
