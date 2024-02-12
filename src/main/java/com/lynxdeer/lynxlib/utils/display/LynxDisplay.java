package com.lynxdeer.lynxlib.utils.display;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.display.enums.EaseType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;


public class LynxDisplay {
	
	public ItemDisplay display;
	
	public Location baseLocation;
	
	public Vector3f transform;
	public Quaternionf afterRotation; // (Left rotation)
	public Vector3f scale;
	public Quaternionf beforeRotation; // (Right Rotation)
	public int transformationStartingTick;
	public int transformationLength;
	
	public HashMap<Transformation, Integer> transformationQueue = new HashMap<>();
	
	public LynxDisplay(Location loc, ItemStack item) {
		this(loc.getWorld().spawn(loc, ItemDisplay.class, i -> {
			i.setItemStack(item);
			i.setTransformation(new Transformation(
					new Vector3f(0, 0, 0),
					new Quaternionf(0, 0, 0, 1),
					new Vector3f(1, 1, 1),
					new Quaternionf(0, 0, 0, 1)
			));
		}));
	}
	
	public LynxDisplay(ItemDisplay originalDisplay) {
		display = originalDisplay;
		baseLocation = display.getLocation();
		Transformation transformation = display.getTransformation();
		transform = transformation.getTranslation();
		afterRotation = transformation.getLeftRotation();
		scale = transformation.getScale();
		beforeRotation = transformation.getRightRotation();
	}
	
	public Location getLocation() { // Gets the location added to the transform.
		return baseLocation.clone().add(transform.x, transform.y, transform.z);
	}
	
//	public Vector3f getCenter() {
//
//		return DisplayUtils.vectortovector3f(getLocation().toVector()).add(DisplayUtils.convertQuaternionToVector(this.afterRotation));
//
//	}
	
	public Vector3f[] getVertices() {
		Vector3f[] vertices = {
				new Vector3f(0.5f, 0.5f, 0.5f),
				new Vector3f(-0.5f, 0.5f, 0.5f),
				new Vector3f(-0.5f, -0.5f, 0.5f),
				new Vector3f(0.5f, -0.5f, 0.5f),
				new Vector3f(0.5f, 0.5f, -0.5f),
				new Vector3f(-0.5f, 0.5f, -0.5f),
				new Vector3f(-0.5f, -0.5f, -0.5f),
				new Vector3f(0.5f, -0.5f, -0.5f)
		};
		Matrix4f rotationMatrix = new Matrix4f();
		this.afterRotation.get(rotationMatrix);
		
		for (Vector3f vertex : vertices)
			rotationMatrix.transformPosition(vertex);
		
		return vertices;
	}
	
	public void teleport(Location loc) {
		baseLocation = loc;
		transform = new Vector3f(0, 0, 0);
	}
	
	public void move(Location loc, double duration) { this.move(loc, duration, new Ease(EaseType.LINEAR)); }
	public void move(Vector3f vector, double duration) { this.move(vector, duration, new Ease(EaseType.LINEAR)); }
	
	public void move(Location loc, double duration, Ease easing) {
		Location original = this.getLocation();
		Vector v = original.subtract(loc).toVector();
		this.move(DisplayUtils.vectortovector3f(v), duration, easing);
	}
	
	public void move(Vector3f vector, double duration, Ease easing) {
		
		// TODO: EASING, MAKE LINEAR NOT TICK
		
		this.display.setInterpolationDelay(0);
		this.display.setInterpolationDuration(LL.millisToTicks((int) duration * 1000));
		
		
		this.transform.add(vector);
	}
	
	public void rotate(float x, float y, float z, double duration) {
		this.rotate(DisplayUtils.translateDegrees(new Quaternionf().rotateXYZ(x, y, z)), duration);
	}
	
	public void rotate(Quaternionf quaternion, double duration) { // ADD EASING LATER!
		
		int ticks = LL.millisToTicks((int) (duration * 1000));
		this.display.setInterpolationDelay(0);
		this.display.setInterpolationDuration(ticks);
		
		this.beforeRotation = new Quaternionf(afterRotation);
		this.afterRotation.add(quaternion);
		this.afterRotation.normalize();
		
		this.transformationStartingTick = Bukkit.getCurrentTick();
		this.transformationLength = ticks;
	}
	
	public void update() {
		this.transformationStartingTick = Bukkit.getCurrentTick();
		this.display.setTransformation(new Transformation(transform, afterRotation, scale, beforeRotation));
	}
	
	
	
	private boolean debugMode = false;
	private BukkitTask debugRunnable = null;
	public void debugMode(boolean debug) {
		debugMode = debug;
		if (debugMode && debugRunnable == null) {
			debugRunnable = new BukkitRunnable() {@Override public void run() {
				if (!debugMode) this.cancel();
				World world = baseLocation.getWorld();
				world.spawnParticle(Particle.END_ROD, baseLocation, 1, 0, 0, 0, 0);
				world.spawnParticle(Particle.FLAME, getLocation(), 1, 0, 0, 0, 0);
				for (Vector3f vertex : getVertices())
					world.spawnParticle(Particle.CRIT, DisplayUtils.vector3ftoLocation(world, vertex).add(getLocation()), 1, 0, 0, 0, 0);
			}}.runTaskTimer(LynxLib.getCurrentPlugin(), 1L, 1L);
		} else if (!debugMode && debugRunnable != null) {
			debugRunnable.cancel();
			debugRunnable = null;
		}
	}
	
}
