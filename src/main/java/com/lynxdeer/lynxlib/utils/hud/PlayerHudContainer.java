package com.lynxdeer.lynxlib.utils.hud;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.utils.hud.components.HudComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerHudContainer {
	
	public static ArrayList<PlayerHudContainer> playerHudContainers = new ArrayList<>();
	
	private Player player;
	public ArrayList<HudComponent> components = new ArrayList<>();
	
	private PlayerHudContainer(Player p) {
		this.player = p;

//		components.add(new HudTextComponent(0, 0, 5, 10, Component.text("howdy")).setBackground(true).setShadowed(true).setParent(this));
//		components.add(new HudItemComponent(0, 0, 5, 10, new ItemStack(Material.CARROT)).setParent(this));
		
		this.update();
		playerHudContainers.add(this);
	}
	
	public void add(HudComponent component) {
		components.add(component);
	}
	
	public void update() {
		
		if (player == null || !player.isOnline()) {
			player = Bukkit.getPlayer(player.getUniqueId());
			if (player == null) {
				this.remove();
				return;
			}
		}
		
		for (HudComponent component : components) component.update();
	}
	
	/**
	 * If you are looping over this to remove all of them, don't. Use @link PlayerHudContainer#removeAll.
	 * Because otherwise it causes a ConcurrentModificationException.
	 */
	public void remove() {
		this.player.eject();
		for (HudComponent component : this.components) {
			component.halfRemove();
		}
		this.components.clear();
		LL.debug("Ran remove.");
		playerHudContainers.remove(this);
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
	public static void removeAll() {
		for (PlayerHudContainer container : playerHudContainers) {
			container.player.eject();
			for (HudComponent component : container.components) {
				component.halfRemove();
			}
		}
		playerHudContainers.clear();
	}
	
	/**
	 * This is preferable over get().remove() because if the player does not have a container, it would create a new one.
	 */
	public static boolean removeContainer(Player p) {
		for (PlayerHudContainer c : playerHudContainers) {
			if (c.getPlayer().getUniqueId().equals(p.getUniqueId())) {
				c.remove();
				return true;
			}
		}
		return false;
	}
	
	public static PlayerHudContainer getContainer(Player p) {
		for (PlayerHudContainer c : playerHudContainers) {
			if (c.getPlayer().getUniqueId().equals(p.getUniqueId())) return c;
		}
		return new PlayerHudContainer(p);
	}
	
}
