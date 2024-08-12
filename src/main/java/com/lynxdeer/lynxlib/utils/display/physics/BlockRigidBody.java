package com.lynxdeer.lynxlib.utils.display.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class BlockRigidBody implements PhysicsObject {
	
	private ItemDisplay display;
	public Vector3f size;
	
	private final PhysicsRigidBody rigidBody;
	
	private BlockRigidBody(Location loc, Vector3f size, float mass, ItemStack material) {
	
		rigidBody = new PhysicsRigidBody(new BoxCollisionShape(size), mass);
		PhysicsHandler.space.addCollisionObject(rigidBody);
		
		rigidBody.setAngularDamping(0.1f);
		rigidBody.setLinearDamping(0.3f);
		
		rigidBody.setPhysicsLocation(new Vector3f((float) loc.getX(), (float) loc.getY(), (float) loc.getZ()));
		
		display = loc.getWorld().spawn(loc, ItemDisplay.class);
	
	}
	
	public static void createBlockRigidBody(Location loc, ItemStack item) {
		new BlockRigidBody(
				loc,
				item.getType().isBlock() ? new Vector3f(1, 1, 1) : new Vector3f(0.5f, 1/16f, 0.5f),
				item.getType().getHardness(),
				item
		);
	}
	
	
	@Override
	public PhysicsRigidBody getRigidBody() {
		return null;
	}
	
	@Override
	public void updateEntity() {
		
		Transform transform = new Transform();
		rigidBody.getTransform(transform);
		
		Quaternion rotation = transform.getRotation();
		
		display.setInterpolationDuration(1);
		display.setInterpolationDelay(0);
		
		getDisplay().setTransformationMatrix(
				new Matrix4f()
						.scale(new org.joml.Vector3f(size.x, size.y, size.z))
						.rotate(new Quaternionf(rotation.getX(), rotation.getY(), rotation.getZ(), rotation.getW()))
						.translate(new org.joml.Vector3f(
								(float) (display.getLocation().getX() - transform.getTranslation().get(0)),
								(float) (display.getLocation().getY() - transform.getTranslation().get(1)),
								(float) (display.getLocation().getZ() - transform.getTranslation().get(2)))
						)
		);
		
	}
	
	@Override
	public ItemDisplay getDisplay() {
		return display;
	}
	
	@Override
	public void destroy() {
	
	}
	
}
