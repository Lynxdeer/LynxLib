package com.lynxdeer.lynxlib.utils.npcs;

import com.lynxdeer.lynxlib.utils.display.DisplayUtils;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public class NPC {
	
	public Location location;
	
	public float size;
	public ItemDisplay base;
	
	public ItemDisplay head;
	
	public ItemDisplay bodyTop;
	public ItemDisplay bodyBottom;
	
	public ItemDisplay leftArmTop;
	public ItemDisplay leftArmBottom;
	
	public ItemDisplay rightArmTop;
	public ItemDisplay rightArmBottom;
	
	public ItemDisplay leftLegTop;
	public ItemDisplay leftLegBottom;
	
	public ItemDisplay rightLegTop;
	public ItemDisplay rightLegBottom;
	
	
	public float[] headRotation;
	public float[] bodyRotation;
	public float[] leftArmRotation;
	public float[] rightArmRotation;
	public float[] leftLegRotation;
	public float[] rightLegRotation;
	
	public boolean visibilityWhitelist = false;
	public ArrayList<Player> visibleTo = new ArrayList<>();
	
	public static final double halfPI = 1.5707963267948966;
	
	public NPC(Location loc, Skin skin) {
		this(loc, skin, 1);
	}
	
	public NPC(Location loc, Skin skin, float scale) {
		
		location = loc;
		size = scale;
		
		resetRotations();
		
		base = loc.getWorld().spawn(loc, ItemDisplay.class, i -> {});
		
		head = loc.getWorld().spawn(loc.clone().add(0, 0, 0), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.head));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(0.0f, 0.5f * size, 0.0f), DisplayUtils.eulerToQuaternion(0, 1.5708f/2f, 0),
							new Vector3f(size), new Quaternionf())); });
		
		
		
		bodyTop = loc.getWorld().spawn(loc.clone().add(0, 1.386 * size, 0), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.bodyTop));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(0.0f, 0.0f, 0.0f), new Quaternionf(),
							new Vector3f(size, size, 0.5f * size), new Quaternionf())); });
		bodyBottom = loc.getWorld().spawn(loc.clone().add(0, 1.386 * size, 0), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.bodyBottom));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(0.0f, -0.5f * size, 0.0f), new Quaternionf(),
							new Vector3f(size, 0.5f * size, 0.5f * size), new Quaternionf())); });
		
		
		
		double[] leftArmPosition = getLimbPos(0.255 * size, true);
		leftArmTop = loc.getWorld().spawn(loc.clone().add(leftArmPosition[0], 1.266 * size, leftArmPosition[1]), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.leftArmTop));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(0.12f * size, 0.12f * this.size, 0.0f), new Quaternionf(),
							new Vector3f(0.5f * size, size, 0.5f * size), new Quaternionf())); });
		leftArmBottom = loc.getWorld().spawn(loc.clone().add(leftArmPosition[0], 1.266 * size, leftArmPosition[1]), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.leftArmBottom));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(0.12f * size, -0.38f * size, 0.0f), new Quaternionf(),
							new Vector3f(0.5f * size, 0.5f * size, 0.5f * size), new Quaternionf())); });
		
		
		
		double[] rightArmPosition = getLimbPos(0.255 * size, false);
		rightArmTop = loc.getWorld().spawn(loc.clone().add(rightArmPosition[0], 1.266 * size, rightArmPosition[1]), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.rightArmTop));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(-0.12f * size, 0.12f * this.size, 0.0f), new Quaternionf(),
							new Vector3f(0.5f * size, size, 0.5f * size), new Quaternionf())); });
		rightArmBottom = loc.getWorld().spawn(loc.clone().add(rightArmPosition[0], 1.266 * size, rightArmPosition[1]), ItemDisplay.class, i -> {
			i.setItemStack(SkinUtils.textureToHead(skin.rightArmBottom));
			i.setViewRange(1000);
			i.setTransformation(
					new Transformation(
							new Vector3f(-0.12f * size, -0.38f * size, 0.0f), new Quaternionf(),
							new Vector3f(0.5f * size, 0.5f * size, 0.5f * size), new Quaternionf())); });
	
	}
	
	public void resetRotations() {
		float yaw = location.getYaw();
		float pitch = location.getPitch();
		headRotation = new float[]     {yaw - 0, pitch};
		bodyRotation = new float[]     {yaw - 0, pitch};
		leftArmRotation = new float[]  {yaw - 0, pitch};
		rightArmRotation = new float[] {yaw - 0, pitch};
		leftLegRotation = new float[]  {yaw - 0, pitch};
		rightLegRotation = new float[] {yaw - 0, pitch};
	}
	
	public double[] getLimbPos(double distance, boolean leftLimb) {
		double x = (leftLimb) ?
				-distance * Math.sin(Math.toRadians(bodyRotation[0]) - halfPI):
				-distance * Math.sin(Math.toRadians(bodyRotation[0]) + halfPI);
		double z = (leftLimb) ?
				-distance * Math.sin(Math.toRadians(bodyRotation[0]) + halfPI):
				-distance * Math.sin(Math.toRadians(bodyRotation[0]) - halfPI);
		return new double[] {x, z};
	}

}
