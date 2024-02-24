package com.lynxdeer.lynxlib.utils.display.physics;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import com.lynxdeer.lynxlib.utils.display.LynxDisplay;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix3f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PhysicsObject extends LynxDisplay {
	
	private Vector3f velocity;
	private Vector3f torque;
	
	public Matrix3f mass;
	public boolean collision = true;
	public boolean collidesWithOtherPhysicsObjects = false; // add support for this later, too expensive for now
	
	public static float gravityRate = 0.49f; // Set in LynxLib#init, if not set it just defaults to 0.49f with no regard for server tick rate
	
	
	public PhysicsObject(Location loc, ItemStack item) {
		this(loc, item, 1);
	}
	
	public PhysicsObject(Location loc, ItemStack item, float mass) {
		super(loc, item);
		
		this.mass = new Matrix3f(mass, mass, mass, mass, mass, mass, mass, mass, mass); // TODO! Do some actual mass matrix calculations
		this.transformationTime = 1;
		this.velocity = new Vector3f(0, 0, 0);
		this.torque = new Vector3f(0, 0, 0);
		
		DisplayUtils.physicsObjects.add(this);
		this.tick();
		
		if (PhysicsUtils.physicsRunnable == null) {
			PhysicsUtils.startPhysicsRunnable();
		}
	}
	
	public void tick() {
		
		for (int i = 0; i < PhysicsUtils.collisionAccuracy; i++) {
			
			velocity.sub(0, gravityRate, 0); // TODO: simplify this equation somewhere by doing it beforehand
			
			move(velocity);
//			LL.debug(velocity);
			rotate(new Quaternionf().rotateXYZ(torque.x, torque.y, torque.z));
			
			if (isCollidingWithABlockEfficient()) {
				move(new Vector3f(velocity).div(-PhysicsUtils.collisionAccuracy));
				velocity.mul(-0.6f);
			}
			
			if (this.debugMode)
				updateDebugDisplay();
			
		}
		update();
//		LL.debug(velocity);
//		LL.debug(torque);
//		LL.debug(beforeRotation);
//		LL.debug(afterRotation);
		if (this.getLocation().getY() < -128) {
			this.display.remove();
			DisplayUtils.physicsObjects.remove(this);
		}
	}
	
	public TextDisplay debugDisplay = null;
	public void updateDebugDisplay() {
		if (debugDisplay == null) {
			this.debugDisplay = baseLocation.getWorld().spawn(getLocation(), TextDisplay.class, t -> {
				t.setBillboard(Display.Billboard.CENTER);
				t.setAlignment(TextDisplay.TextAlignment.LEFT);
				t.setSeeThrough(true);
			});
		} else {
			Location loc = getLocation();
			this.debugDisplay.text(
//					Component.text("§7Position: §c" + loc.getX() + "§8, §a" + loc.getY() + "§8, §9" + loc.getZ()).append(Component.newline()).append(
					Component.text("§7Velocity: §c" + velocity.x + "§8, §a" + velocity.y + "§8, §9" + velocity.z).append(Component.newline()).append(
					Component.text("§7Torque: §c" + torque.x + "§8, §a" + torque.y + "§8, §9" + torque.z))
			);
		}
	}
	
	public void applySimpleVelocity(Vector3f velocity) {
		velocity.add(velocity);
	}
	
	public void applyVelocity(float power, Location source) {
		Vector3f dir = DisplayUtils.vectortovector3f(getLocation().subtract(source).toVector());
		velocity.add( dir.normalize().mul(power) );
		
		Vector3f offset = DisplayUtils.vectortovector3f(getLocation().subtract(source).toVector());
		torque.add(PhysicsUtils.calculateTorque(offset, new Vector3f(offset).mul(power) ));
	}
	
}
