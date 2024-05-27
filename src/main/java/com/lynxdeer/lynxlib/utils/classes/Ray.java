package com.lynxdeer.lynxlib.utils.classes;

import com.lynxdeer.lynxlib.LL;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Ray {
	
	public Vector origin;
	public Vector direction;
	
	public Ray(Vector origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	/**
	 * This assumes that the direction vector is normalised. If it isn't, be warned!!!!
	 */
	public Ray(Location origin, Vector direction) {
		this(origin.clone().toVector(), direction);
	}
	public Ray(Location origin) {
		this(origin.clone().toVector(), origin.clone().getDirection());
	}
	
	public boolean isIntersectingBoundingBox(BoundingBox box) {
		Vector min = box.getMin();
		Vector max = box.getMax();
		
		double tmin = (min.getX() - this.origin.getX()) / this.direction.getX();
		double tmax = (max.getX() - this.origin.getX()) / this.direction.getX();
		
		if (tmin > tmax) {
			double temp = tmin;
			tmin = tmax;
			tmax = temp;
		}
		
		double tymin = (min.getY() - this.origin.getY()) / this.direction.getY();
		double tymax = (max.getY() - this.origin.getY()) / this.direction.getY();
		
		if (tymin > tymax) {
			double temp = tymin;
			tymin = tymax;
			tymax = temp;
		}
		
		if ((tmin > tymax) || (tymin > tmax))
			return false;
		
		if (tymin > tmin) tmin = tymin;
		if (tymax < tmax) tmax = tymax;
		
		double tzmin = (min.getZ() - this.origin.getZ()) / this.direction.getZ();
		double tzmax = (max.getZ() - this.origin.getZ()) / this.direction.getZ();
		
		if (tzmin > tzmax) {
			double temp = tzmin;
			tzmin = tzmax;
			tzmax = temp;
		}
		
		if ((tmin > tzmax) || (tzmin > tmax))
			return false;
		
		return true;
	}
	
	public Vector[] getIntersectionPoints(BoundingBox box) {
		Vector[] intersectionsAndNormals = new Vector[2];
		Vector min = box.getMin();
		Vector max = box.getMax();
		
		double tmin = (min.getX() - origin.getX()) / direction.getX();
		double tmax = (max.getX() - origin.getX()) / direction.getX();
		
		if (tmin > tmax) {
			double temp = tmin;
			tmin = tmax;
			tmax = temp;
		}
		
		double tymin = (min.getY() - origin.getY()) / direction.getY();
		double tymax = (max.getY() - origin.getY()) / direction.getY();
		
		if (tymin > tymax) {
			double temp = tymin;
			tymin = tymax;
			tymax = temp;
		}
		
		if ((tmin > tymax) || (tymin > tmax))
			return null;
		
		if (tymin > tmin) tmin = tymin;
		if (tymax < tmax) tmax = tymax;
		
		double tzmin = (min.getZ() - origin.getZ()) / direction.getZ();
		double tzmax = (max.getZ() - origin.getZ()) / direction.getZ();
		
		if (tzmin > tzmax) {
			double temp = tzmin;
			tzmin = tzmax;
			tzmax = temp;
		}
		
		if ((tmin > tzmax) || (tzmin > tmax)) return null;
		if (tzmin > tmin) tmin = tzmin;
		if (tzmax < tmax) tmax = tzmax;
		
		Vector firstIntersection = origin.clone().add(direction.clone().multiply(tmin));
		Vector lastIntersection = origin.clone().add(direction.clone().multiply(tmax));
		
		intersectionsAndNormals[0] = firstIntersection;
		intersectionsAndNormals[1] = lastIntersection;
		
		return intersectionsAndNormals;
	}
	
	public Vector[] getIntersectionPointsAndNormals(BoundingBox box) {
		Vector[] intersectionsAndNormals = new Vector[4];
		Vector min = box.getMin();
		Vector max = box.getMax();
		Vector[] normals = {
				new Vector(-1, 0, 0), // Normal for min X
				new Vector(1, 0, 0),  // Normal for max X
				new Vector(0, -1, 0), // Normal for min Y
				new Vector(0, 1, 0),  // Normal for max Y
				new Vector(0, 0, -1), // Normal for min Z
				new Vector(0, 0, 1)   // Normal for max Z
		};
		
		double tmin = (min.getX() - origin.getX()) / direction.getX();
		double tmax = (max.getX() - origin.getX()) / direction.getX();
		
		if (tmin > tmax) {
			double temp = tmin;
			tmin = tmax;
			tmax = temp;
		}
		
		double tymin = (min.getY() - origin.getY()) / direction.getY();
		double tymax = (max.getY() - origin.getY()) / direction.getY();
		
		if (tymin > tymax) {
			double temp = tymin;
			tymin = tymax;
			tymax = temp;
		}
		
		if ((tmin > tymax) || (tymin > tmax)) {
			return null;
		}
		
		if (tymin > tmin) {
			tmin = tymin;
		}
		
		if (tymax < tmax) {
			tmax = tymax;
		}
		
		double tzmin = (min.getZ() - origin.getZ()) / direction.getZ();
		double tzmax = (max.getZ() - origin.getZ()) / direction.getZ();
		
		if (tzmin > tzmax) {
			double temp = tzmin;
			tzmin = tzmax;
			tzmax = temp;
		}
		
		if ((tmin > tzmax) || (tzmin > tmax)) {
			return null;
		}
		
		if (tzmin > tmin) {
			tmin = tzmin;
		}
		
		if (tzmax < tmax) {
			tmax = tzmax;
		}
		
		Vector firstIntersection = origin.clone().add(direction.clone().multiply(tmin));
		Vector lastIntersection = origin.clone().add(direction.clone().multiply(tmax));
		
		Vector firstNormal = normals[getNormalIndex(firstIntersection, min, max)];
		Vector lastNormal = normals[getNormalIndex(lastIntersection, min, max)];
		
		intersectionsAndNormals[0] = firstIntersection;
		intersectionsAndNormals[1] = firstNormal;
		intersectionsAndNormals[2] = lastIntersection;
		intersectionsAndNormals[3] = lastNormal;
		
		return intersectionsAndNormals;
	}
	
	private int getNormalIndex(Vector intersection, Vector min, Vector max) {
		if (intersection.getX() == min.getX()) {
			return 0;
		} else if (intersection.getX() == max.getX()) {
			return 1;
		} else if (intersection.getY() == min.getY()) {
			return 2;
		} else if (intersection.getY() == max.getY()) {
			return 3;
		} else if (intersection.getZ() == min.getZ()) {
			return 4;
		} else {
			return 5;
		}
	}
	
	
	
	
	
	
	
	
	
	
}