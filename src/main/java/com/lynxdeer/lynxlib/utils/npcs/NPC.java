package com.lynxdeer.lynxlib.utils.npcs;

import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartParent;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPart;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartType;
import org.bukkit.Location;
import org.joml.Matrix4f;
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
			if (type != BodyPartType.PLAYER_ROOT)
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
	
	public void movePart(BodyPartParent part, Vector3f offset) {
		
		BodyPart[] partsToRotate = Arrays.stream(part.getChildren()).map(this::getBodyPart).toArray(BodyPart[]::new);
		for (BodyPart bodyPart : partsToRotate) {
			bodyPart.partOffset = offset;
			bodyPart.update();
		}
		
	}

	public BodyPart getBodyPart(BodyPartType type) {
		return bodyParts.stream().filter(bp -> bp.type == type).findFirst().orElse(null);
	}
	
	public void update() {
		for (BodyPart part : bodyParts) {
			part.matrix = new Matrix4f();
		}
		
		for (BodyPart part : bodyParts) {
			for (BodyPartType b : part.type.getChildren()) {
				BodyPart childPart = getBodyPart(b);
				Vector3f offset = childPart.pivotOffset.sub(part.pivotOffset, new Vector3f());
				
				childPart.matrix.rotateZYX(childPart.rot); // Child's rotation
				childPart.matrix.translate(offset); // Translate from child's pivot to parent's pivot
				
				childPart.matrix.rotateZYX(part.rot); // Parent's rotation
				childPart.matrix.translate(part.partOffset); // Parent's translation
				
				childPart.matrix.translate(offset.negate()); // Unapply offset
				
				childPart.matrix.rotateXYZ(this.rotation); // Root rotation
				childPart.matrix.translate(this.translation); // Root translation
				
				childPart.matrix.scale(b.getScale()); // Child's scale
				childPart.matrix.scale(this.scale); // Root scale
			}
			part.update();
		}
	}
	
	
}
