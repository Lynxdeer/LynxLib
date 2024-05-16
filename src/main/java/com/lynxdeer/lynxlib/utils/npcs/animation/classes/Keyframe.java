package com.lynxdeer.lynxlib.utils.npcs.animation.classes;

import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartParent;
import com.lynxdeer.lynxlib.utils.npcs.animation.enums.AnimationChannel;
import org.joml.Vector3f;

public class Keyframe {
	
	public int startTick;
	
	public BodyPartParent bodyPart;
	
	public AnimationChannel channel;
	public Vector3f value;
	
	public Keyframe(int startTick, BodyPartParent bodyPart, AnimationChannel channel, Vector3f value) {
		this.startTick = startTick;
		this.bodyPart = bodyPart;
		this.channel = channel;
		this.value = value;
	}
	
}
