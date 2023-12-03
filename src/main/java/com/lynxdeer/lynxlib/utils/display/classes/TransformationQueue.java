package com.lynxdeer.lynxlib.utils.display.classes;

import com.lynxdeer.lynxlib.utils.display.enums.EaseType;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TransformationQueue {
	
	public Vector3f position;
	public Quaternionf rotation;
	
	
	
	public int duration;
	public int ticksElapsed;
	public EaseType easing;
	
	
	
	
	public TransformationQueue(Vector3f change) {
	
	}
	
	/*public Vector3f getNextPosition() {
	
	}*/
	
}
