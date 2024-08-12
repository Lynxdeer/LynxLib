package com.lynxdeer.lynxlib.utils.display.physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import org.bukkit.entity.ItemDisplay;

public interface PhysicsObject {
	
	PhysicsRigidBody getRigidBody();
	
	void updateEntity();
	
	ItemDisplay getDisplay();
	
	void destroy();
	
	
}
