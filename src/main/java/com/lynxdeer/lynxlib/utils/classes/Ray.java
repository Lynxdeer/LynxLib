package com.lynxdeer.lynxlib.utils.classes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class Ray {
	
	public Location origin;
	public Vector direction;
	
	public Ray(Location origin) {
		this.origin = origin.clone();
		this.direction = origin.clone().getDirection().normalize();
	}
	
	public boolean check(Block block) { return check(block.getLocation()); }
	
	public boolean check(Location loc) {
		return check(loc, new Vector(1, 1, 1));
	}
	
	public boolean check(World world, BoundingBox boundingBox) {
		return check(new Location(world, boundingBox.getCenterX(), boundingBox.getCenterY(), boundingBox.getCenterZ()),
					 new Vector(boundingBox.getWidthX(), boundingBox.getHeight(), boundingBox.getWidthZ()));
	}
	
	
	// TODO: Test whether this method actually works, or whether internet man was incorrect!
	public boolean check(Location loc, Vector scaling) {
	
		double tx1 = (loc.getX() - this.origin.getX()) * this.direction.getX();
		double tx2 = (loc.getX() + scaling.getX() - this.origin.getX()) * this.direction.getX();
		
		double ty1 = (loc.getY() - this.origin.getY()) * this.direction.getY();
		double ty2 = (loc.getY() + scaling.getY() - this.origin.getY()) * this.direction.getY();
		
		double tz1 = (loc.getZ() - this.origin.getZ()) * this.direction.getZ();
		double tz2 = (loc.getZ() + scaling.getZ() - this.origin.getZ()) * this.direction.getZ();
		
		double min = Math.min(tx1, tx2);
		double max = Math.max(tx1, tx2);
		
		min = Math.max(min, Math.min(ty1, ty2));
		max = Math.min(max, Math.max(ty1, ty2));
		
		min = Math.max(min, Math.min(tz1, tz2));
		max = Math.min(max, Math.max(tz1, tz2));
		
		return (max >= min);
	
	}
	
	
}
