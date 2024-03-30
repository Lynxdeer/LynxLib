package com.lynxdeer.lynxlib.utils.npcs;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BodyPart {
	
	public NPC parent;
	
	public Location baseLocation;
	
	public BodyPartType type;
	public String texture;
	
	public ItemDisplay display;
	
	public Vector3f partOffset;
	public Vector3f rot;
	public Vector3f pivotOffset;
	
	public BodyPart(NPC parent, BodyPartType type) {
		this.parent = parent;
		this.type = type;
		this.baseLocation = parent.loc;
		this.texture = parent.skin.readyTextures.get(type);
		
		this.partOffset = type.getTransform();
		this.rot = new Vector3f(0);
		this.pivotOffset = type.getPivotPoint();
		
		this.display = parent.loc.getWorld().spawn(parent.loc, ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(this.texture));
			display.setInterpolationDuration(0);
			display.setInterpolationDelay(0);
			display.setTransformationMatrix(getMatrix());
		});
		
	}
	
	public void update() {
		display.setInterpolationDelay(0);
		display.setInterpolationDuration(1);
		display.setTransformationMatrix(getMatrix());
	}
	
	public Matrix4f getMatrix() {
		Matrix4f matrix = new Matrix4f();
		
		// Scale first
		matrix.scale(type.getScale());
		
		// Parent translation & rotation
		matrix.translate(parent.translation);
		matrix.rotateXYZ(parent.rotation);
		
		// Translate to apply the parent offset
		matrix.translate(partOffset.sub(pivotOffset));
		
		// Rotate by part's rotation, then translate by pivot to apply pivot offset
		matrix.rotateXYZ(rot);
		matrix.translate(pivotOffset);
		
		// Parent scale
		matrix.scale(parent.scale);
		return matrix;
	}
	
	
}
