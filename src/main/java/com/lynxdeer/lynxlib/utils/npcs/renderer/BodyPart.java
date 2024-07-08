package com.lynxdeer.lynxlib.utils.npcs.renderer;

import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import com.lynxdeer.lynxlib.utils.npcs.NPC;
import com.lynxdeer.lynxlib.utils.npcs.SkinUtils;
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
	
	public Matrix4f matrix;
	
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
			i.setInterpolationDuration(0);
			i.setInterpolationDelay(0);
			i.setTransformationMatrix(getMatrix());
		});
		
	}
	
	public void update() {
		display.setInterpolationDelay(0);
		display.setInterpolationDuration(1);
		display.setTransformationMatrix(matrix);
	}
	
	public Matrix4f getMatrix() {
		
		Matrix4f matrix = new Matrix4f();

		// Parent translation & rotation
		matrix.translate(parent.translation);
		matrix.rotateXYZ(parent.rotation);

		// Translate to apply the parent offset
		matrix.translate(DisplayUtils.clone(partOffset).sub(pivotOffset));

		// Rotate by part's rotation, then translate by pivot to apply pivot offset
		matrix.rotateZYX(rot);
		matrix.translate(pivotOffset);

		// Parent scale
		matrix.scale(parent.scale);


		// The part-specific scale
		matrix.scale(type.getScale());

		return matrix;
	}
	
	
}
