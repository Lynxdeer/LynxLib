package com.lynxdeer.lynxlib.utils.npcs.renderer;

/**
 * This contains all the body parts that can be transformed and their parents,
 * while BodyPartType contains all the body parts that should be rendered.
 *
 * The difference is mainly that rotating by this will rotate the part's children, too.
 * If you were, to example, rotate LEFT_ARM, it would rotate LEFT_ARM_TOP and LEFT_ARM_BOTTOM.
 */
public enum BodyPartParent {
	
	HEAD,
	BODY,
	
	LEFT_ARM,
	RIGHT_ARM,
	
	LEFT_LEG,
	RIGHT_LEG;
	
	
	public BodyPartType[] getChildren() {
		return switch (this) {
			case HEAD ->      new BodyPartType[]{BodyPartType.HEAD};
			case BODY ->      new BodyPartType[]{BodyPartType.WAIST, BodyPartType.CHEST, BodyPartType.HEAD, BodyPartType.LEFT_FOREARM, BodyPartType.LEFT_ARM, BodyPartType.RIGHT_FOREARM, BodyPartType.LEFT_FOREARM};
			case LEFT_ARM ->  new BodyPartType[]{BodyPartType.LEFT_ARM, BodyPartType.LEFT_FOREARM};
			case RIGHT_ARM -> new BodyPartType[]{BodyPartType.RIGHT_ARM, BodyPartType.RIGHT_FOREARM};
			case LEFT_LEG ->  new BodyPartType[]{BodyPartType.LEFT_LEG, BodyPartType.LEFT_FORELEG};
			case RIGHT_LEG -> new BodyPartType[]{BodyPartType.RIGHT_LEG, BodyPartType.RIGHT_FORELEG};
		};
	}
	
	
	
}
