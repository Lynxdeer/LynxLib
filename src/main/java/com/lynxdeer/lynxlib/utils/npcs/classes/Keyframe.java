package com.lynxdeer.lynxlib.utils.npcs.classes;

import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartType;
import org.joml.Vector3f;

public class Keyframe {
	
	public int startTick;
	public int duration;
	
	public BodyPartType bodyPart;
	
	public Vector3f position;
	public Vector3f rotation;
	
	public Keyframe(int startTick, int duration, BodyPartType bodyPart, Vector3f position, Vector3f rotation) {
	
	}
	
}
