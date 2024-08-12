package com.lynxdeer.lynxlib.utils.display.physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class RagdollPart implements PhysicsObject {
	
	public Player player;
	public BodyPartType part;
	public ItemDisplay display;
	
	public RagdollPart(Player player, BodyPartType part) {
		
		PhysicsHandler.objects.add(this);
		
		this.player = player;
		this.part = part;
		
	}
	
	@Override
	public PhysicsRigidBody getRigidBody() {
		return null;
	}
	
	@Override
	public void updateEntity() {
	
	
	
	}
	
	@Override
	public ItemDisplay getDisplay() {
		return null;
	}
	
	@Override
	public void destroy() {
	
	}
	
}
