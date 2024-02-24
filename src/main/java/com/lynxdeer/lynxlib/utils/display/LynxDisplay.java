package com.lynxdeer.lynxlib.utils.display;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.misc.CollisionUtils;
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

import java.util.ArrayList;
import java.util.HashMap;


public class LynxDisplay {
	
	public ItemDisplay display;
	
	public Location baseLocation;
	
	public Vector3f transform;
	public Quaternionf afterRotation; // (Left rotation)
	public Vector3f scale;
	public Quaternionf beforeRotation; // (Right Rotation)
	public int transformationStartingTick;
	public int transformationTime = 1;
	
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
	
	public Vector3f[] getAxes() {
		
		Vector3f[] vertices = getVertices();
		
		return new Vector3f[] {
				new Vector3f(vertices[1].sub(vertices[4])).normalize(),
				new Vector3f(vertices[3].sub(vertices[0])).normalize(),
				new Vector3f(vertices[7].sub(vertices[4])).normalize()
		};
		
	}
	
	public void teleport(Location loc) {
		baseLocation = loc;
		transform = new Vector3f(0, 0, 0);
	}
	
	public void move(Location loc) {
		Location original = this.getLocation();
		Vector v = original.subtract(loc).toVector();
		this.move(DisplayUtils.vectortovector3f(v));
	}
	
	public void move(Vector3f vector) {
		this.transform.add(vector);
	}
	
	public void rotate(float x, float y, float z) {this.rotate(DisplayUtils.translateDegrees(new Quaternionf().rotateXYZ(x, y, z)));}
	
	public void rotate(Quaternionf quaternion) {
		this.beforeRotation = new Quaternionf(afterRotation);
		this.afterRotation.add(quaternion);
		this.afterRotation.normalize();
	}
	
	public void update() {
		this.transformationStartingTick = Bukkit.getCurrentTick();
		this.display.setInterpolationDelay(0);
		this.display.setInterpolationDuration(this.transformationTime);
		this.display.setTransformation(new Transformation(transform, afterRotation, scale, beforeRotation));
	}
	
	public void setTransformationTime(int ticks) {
		this.transformationTime = ticks;
	}
	
	public void setTransformationTime(double seconds) {
		this.transformationTime = LL.millisToTicks((int) seconds * 1000);
	}
	
	/**
	 * Checks whether the display is colliding with any block around it.
	 * Only checks 7 blocks (around, in, above, down) rather than the full 19. Not 100% accurate, but saves a lot of computing power.
	 * It is impossible for the block, unless scaled, to reach the edges of a 3x3 cube, so we don't need to check 27.
	 * */
	public boolean isCollidingWithABlockEfficient() {
		
		for (Vector3f v : new Vector3f[] {
				new Vector3f(0, -1, 0),
				new Vector3f(0, 1, 0),
				
				new Vector3f(0, 0, 0),
				new Vector3f(1, 0, 0),
				new Vector3f(0, 0, 1),
				new Vector3f(-1, 0, 0),
				new Vector3f(0, 0, -1) }) {
			if (CollisionUtils.checkCollisionWithBlock(getVertices(), getAxes(), v)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	
	/**
	 * Only checks 7 blocks (around, in, above, down) rather than the full 19. Not 100% accurate, but saves a lot of computing power.
	 * */
	public ArrayList<Location> getCollidingBlocksEfficient() {
		
		ArrayList<Location> ret = new ArrayList<>();
		
		for (Vector3f v : new Vector3f[] {
				new Vector3f(0, -1, 0),
				new Vector3f(0, 1, 0),
				
				new Vector3f(0, 0, 0),
				new Vector3f(1, 0, 0),
				new Vector3f(0, 0, 1),
				new Vector3f(-1, 0, 0),
				new Vector3f(0, 0, -1) }) {
			if (CollisionUtils.checkCollisionWithBlock(getVertices(), getAxes(), v))
				ret.add(getLocation().clone().add(new Vector(v.x, v.y, v.z)));
		}
		
		return ret;
	
	}
	
	
	
	public boolean debugMode = false;
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
