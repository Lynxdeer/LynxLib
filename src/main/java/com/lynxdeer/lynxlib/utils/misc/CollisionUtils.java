package com.lynxdeer.lynxlib.utils.misc;

import com.lynxdeer.lynxlib.LL;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.joml.Vector3f;

import java.util.ArrayList;

public class CollisionUtils {
	
	public static boolean canPass(Location loc) {
		
		Block block = loc.getBlock();
		Material mat = block.getType();
		if (mat.isAir()) return true;
		if (!mat.isCollidable()) return true;
		// For some reason, solid checks are completely opposite! Why? I don't know! It's really stupid!
		if (mat.isSolid()) return !block.getBoundingBox().contains(loc.toVector());
		
		return false;
	}
	
	public static ArrayList<LivingEntity> checkWideHitbox(Player p, Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (LivingEntity loopentity : loc.getWorld().getLivingEntities()) {
			if (loopentity != null && (loopentity.getLocation().clone().add(0, 1, 0)).distance(loc) < loopentity.getBoundingBox().getHeight() && (loopentity!=p))
				ret.add(loopentity);
		}
		return ret;
	}
	
	public static ArrayList<LivingEntity> checkExpandedHitbox(Player p, Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (LivingEntity loopentity : loc.getWorld().getLivingEntities()) {
			if (loopentity != null && loopentity.getBoundingBox().expand(0.5).shift(loc.getDirection().multiply(-1)).contains(loc.toVector()) && (loopentity!=p))
				ret.add(loopentity);
		}
		return ret;
	}
	
	public static ArrayList<LivingEntity> checkHitboxes(Player p, Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (LivingEntity le : loc.getNearbyLivingEntities(2)) {
			if (le.isDead()) continue; // Needed because entities still have a bounding box during death animation for some reason?
			if (le != p) {
				BoundingBox b = le.getBoundingBox();
				//b = b.expand(new Vector(0.25, 0.25, 0.25)); //Moving.getSpread(ep)
				if (b.contains(loc.toVector())) ret.add(le);
			}
		}
		
		return ret;
	}
	
	public static boolean checkCollisionWithBlock(Vector3f[] aVertices, Vector3f[] aAxes, Vector3f relativeBlockPosition) {
		return checkComplicatedCollision(aVertices, new Vector3f[] {
				new Vector3f(0, 0, 0).add(relativeBlockPosition),
				new Vector3f(1, 0, 0).add(relativeBlockPosition),
				new Vector3f(1, 0, 1).add(relativeBlockPosition),
				new Vector3f(0, 0, 1).add(relativeBlockPosition),
				
				new Vector3f(0, 1, 0).add(relativeBlockPosition),
				new Vector3f(1, 1, 0).add(relativeBlockPosition),
				new Vector3f(1, 1, 1).add(relativeBlockPosition),
				new Vector3f(0, 1, 1).add(relativeBlockPosition),
				
		}, aAxes, new Vector3f[] {
				new Vector3f(1, 0, 0),
				new Vector3f(0, 1, 0),
				new Vector3f(0, 0, 1)
		});
	}
	
	
	
	public static boolean checkComplicatedCollision(Vector3f[] aVertices, Vector3f[] bVertices, Vector3f[] aAxes, Vector3f[] bAxes) {
		
		Vector3f[] allAxes = new Vector3f[] {
				aAxes[0],
				aAxes[1],
				aAxes[2],
				
				bAxes[0],
				bAxes[1],
				bAxes[2],
				
				aAxes[0].cross(bAxes[0]),
				aAxes[0].cross(bAxes[1]),
				aAxes[0].cross(bAxes[2]),
				
				aAxes[1].cross(bAxes[0]),
				aAxes[1].cross(bAxes[1]),
				aAxes[1].cross(bAxes[2]),
				
				aAxes[2].cross(bAxes[0]),
				aAxes[2].cross(bAxes[1]),
				aAxes[2].cross(bAxes[2])
		};
		
		return checkProjectionOverlap(allAxes, aVertices, bVertices) || checkProjectionOverlap(allAxes, bVertices, aVertices);
		
	}
	
	
	private static boolean checkProjectionOverlap(Vector3f[] axes, Vector3f[] aVertices, Vector3f[] bVertices) {
		
		for (Vector3f axis : axes) {
			
			if (axis.equals(0, 0, 0)) return true;
			
			float aProjMin = -999999999, bProjMin = -999999999;
			float aProjMax = 999999999, bProjMax = 999999999; // Arbitrarily high number lmao
			
			for (Vector3f vertex : aVertices) {
				float bDot = vertex.dot(axis);
				if (bDot < bProjMin) bProjMin = bDot;
				if (bDot > bProjMax) bProjMax = bDot;
			}
			for (Vector3f vertex : bVertices) {
				float bDot = vertex.dot(axis);
				if (bDot < bProjMin) bProjMin = bDot;
				if (bDot > bProjMax) bProjMax = bDot;
			}
			
			float overlap = getOverlap(aProjMin, aProjMax, bProjMin, bProjMax);
			if (overlap <= 0) return false;
			
		}
		
		return true;
	
	}
	
	private static float getOverlap(float aStart, float aEnd, float bStart, float bEnd) {
		if (aStart > bStart) return aEnd < bStart ? 0 : aEnd - bStart;
		return bEnd > aStart ? 0 : bEnd - aStart;
	}
	

}
