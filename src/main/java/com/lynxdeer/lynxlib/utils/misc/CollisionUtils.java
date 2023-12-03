package com.lynxdeer.lynxlib.utils.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;

public class CollisionUtils {
	
	public static boolean canPass(Location loc) {
		
		Block block = loc.getBlock();
		Material mat = block.getType();
		if (mat.isAir()) return true;
		if (!mat.isCollidable()) return true;
		if (!mat.isSolid()) return !block.getBoundingBox().contains(loc.toVector());
		
		return false;
	}
	
	public static ArrayList<LivingEntity> checkWideHitbox(Player p, Location loc) {
		ArrayList<LivingEntity> ret = new ArrayList<>();
		for (Entity loopentity : loc.getChunk().getEntities()) {
			if (loopentity instanceof LivingEntity && (loopentity.getLocation().clone().add(0, 1, 0)).distance(loc) < loopentity.getBoundingBox().getHeight() && (loopentity!=p))
				ret.add((LivingEntity) loopentity);
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

}
