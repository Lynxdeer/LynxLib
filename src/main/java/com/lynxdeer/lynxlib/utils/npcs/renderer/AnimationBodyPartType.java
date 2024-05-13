package com.lynxdeer.lynxlib.utils.npcs.renderer;

/**
 * This contains all the body parts that can be transformed and their parents,
 * while BodyPartType contains all the body parts that should be rendered
 */
public enum AnimationBodyPartType {
	
	HEAD,
	BODY,
	
	LEFT_ARM,
	RIGHT_ARM,
	
	LEFT_LEG,
	RIGHT_LEG;
	
	
	public BodyPartType[] getChildren() {
		return switch (this) {
			case HEAD -> new BodyPartType[]{BodyPartType.HEAD};
			case BODY -> new BodyPartType[]{BodyPartType.BODY_BOTTOM, BodyPartType.BODY_TOP, BodyPartType.HEAD, BodyPartType.LEFT_ARM_BOTTOM, BodyPartType.LEFT_ARM_TOP, BodyPartType.RIGHT_ARM_BOTTOM, BodyPartType.LEFT_ARM_BOTTOM};
			case LEFT_ARM -> new BodyPartType[]{BodyPartType.LEFT_ARM_TOP, BodyPartType.LEFT_ARM_BOTTOM};
			case RIGHT_ARM -> new BodyPartType[] {BodyPartType.RIGHT_ARM_TOP, BodyPartType.RIGHT_ARM_BOTTOM};
			case LEFT_LEG -> new BodyPartType[] {BodyPartType.LEFT_LEG_TOP, BodyPartType.LEFT_LEG_BOTTOM};
			case RIGHT_LEG -> new BodyPartType[] {BodyPartType.RIGHT_LEG_TOP, BodyPartType.RIGHT_LEG_BOTTOM};
		};
	}
	
	
	
}