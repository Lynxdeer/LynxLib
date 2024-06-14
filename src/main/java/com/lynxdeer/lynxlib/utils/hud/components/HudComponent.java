package com.lynxdeer.lynxlib.utils.hud.components;

import com.lynxdeer.lynxlib.utils.hud.PlayerHudContainer;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;

public abstract class HudComponent {
	
	public PlayerHudContainer parent;
	
	// A range between -8-8 and -4.5-4.5 to determine where the object is on screen.
	public float x, y = 0;
	public float scaleX, scaleY = 1;
	public float rot = 0;
	public float pivotX, pivotY = 0;
	// Higher values are further away and therefore further from the player.
	public int z;
	
	public abstract Display getDisplay();
	public abstract void specificUpdate();
	
	/**
	 * Adds to the display's location. For setting its location, use @link HudComponent#teleport.
	 * @param deltaX Change in X position.
	 * @param deltaY Change in Y position.
	 */
	public void move(float deltaX, float deltaY) {
		this.x += deltaX;
		this.y += deltaY;
//		LL.debug("Moving. x: {}, y: {}", x, y);
	}
	
	public void teleport(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Adds to the display's rotation. For setting its rotation, use @link HudComponent#setRotation.
	 * @param deltaRot Change in rotation.
	 */
	public void rotate(float deltaRot) {
		this.rot += deltaRot;
	}
	
	public void setRotation(float rotation) {
		this.rot = rotation;
	}
	
	public void update() {
		getDisplay().setInterpolationDelay(0);
		getDisplay().setInterpolationDuration(1);
		getDisplay().setTransformationMatrix(
				new Matrix4f()
						.translate(x, y, z * -100f)
						.rotateXYZ(0, 0, (float) Math.toDegrees(rot))
						.translate(pivotX, pivotY, 0)
						.scale(scaleX * 100f, scaleY * 100f, 0.01f)
		);
		this.specificUpdate();
	}
	
	public void halfRemove() {
		this.getDisplay().remove();
	}
	
	public void remove() {
		this.halfRemove();
		this.parent.components.remove(this);
	}
	
}