package com.lynxdeer.lynxlib.utils.npcs;

import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartParent;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPart;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartType;
import org.bukkit.Location;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;

public class NPC {
	
	public Location loc;
	public String name;
	public Skin skin;
	
	public Vector3f translation;
	public Vector3f rotation;
	public Vector3f scale;
	
	public Vector3f baseTransform;
	
	public ArrayList<BodyPart> bodyParts = new ArrayList<>();
	
	public NPC(String name, Location loc, Skin skin) {
		this.name = name;
		this.loc = loc;
		this.skin = skin;
		
		this.translation = new Vector3f(0);
		this.rotation = new Vector3f(0);
		this.scale = new Vector3f(1);
	}
	
	public boolean spawn() {
	
		if (!skin.ready) return false;
		
		for (BodyPartType type : BodyPartType.values()) {
			bodyParts.add(new BodyPart(this, type));
		}
		
		return true;
	
	}
	
	public void rotatePart(BodyPartParent part, Vector3f rotation) {

		BodyPart[] partsToRotate = Arrays.stream(part.getChildren()).map(this::getBodyPart).toArray(BodyPart[]::new);
		for (BodyPart bodyPart : partsToRotate) {
			bodyPart.rot = rotation;
			bodyPart.update();
		}

	}

	public BodyPart getBodyPart(BodyPartType type) {
		return bodyParts.stream().filter(bp -> bp.type == type).findFirst().orElse(null);
	}
	
	public void update() {
		for (BodyPart part : bodyParts) {
			part.update();
		}
	}
	
	
}
