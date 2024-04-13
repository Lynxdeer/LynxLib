package com.lynxdeer.lynxlib.utils.holograms;

import com.lynxdeer.lynxlib.LynxLib;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SimpleHolograms {
	
	public static ArmorStand createHologram(Location loc, String text) {
		// Using this instead of World#spawnEntity prevents the armor stand from flashing when spawned.
		return loc.getWorld().spawn(loc, ArmorStand.class, a -> {
			a.setMarker(true);
			a.setCustomNameVisible(true);
			a.customName(Component.text(ChatColor.translateAlternateColorCodes('&', text)));
			a.setInvisible(true);
			a.addDisabledSlots(EquipmentSlot.values());
			a.setCanTick(false);
			a.setSmall(true);
			a.setGravity(false);
		});
	}
	
	public static ArmorStand createTemporaryHologram(Location loc, String text, long time) {
		ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class, a -> {
			a.setMarker(true);
			a.setCustomNameVisible(true);
			a.customName(Component.text(ChatColor.translateAlternateColorCodes('&', text)));
			a.setInvisible(true);
			a.addDisabledSlots(EquipmentSlot.values());
			a.setCanTick(false);
			a.setSmall(true);
			a.setGravity(false);
		});
		new BukkitRunnable() {@Override public void run() {
			armorStand.remove();
		}}.runTaskLater(LynxLib.getLLPlugin(), time);
		return armorStand;
	}
	
	
	public static void createBounceHolo(Location loc, String text) {
		ArmorStand a = createHologram(loc, text);
		a.setGravity(true);
		final float[] grav = {0.3f};
		final int[] ticks = {0};
		new BukkitRunnable() {
			@Override
			public void run() {
				grav[0]-=0.04f;
				ticks[0]++;
				a.teleport(a.getLocation().add(new Vector(0, grav[0], 0)));
				if (a.getLocation().getY() < -64 || a.getLocation().getBlock().getBoundingBox().contains(a.getLocation().toVector()) || ticks[0] > 40) {
					a.getWorld().spawnParticle(Particle.CLOUD, a.getLocation(), 5, 0, 0, 0, 0.1);
					a.remove();
					this.cancel();
				}
			}
		}.runTaskTimer(LynxLib.getLLPlugin(), 1L, 1L);
	}
	
}
