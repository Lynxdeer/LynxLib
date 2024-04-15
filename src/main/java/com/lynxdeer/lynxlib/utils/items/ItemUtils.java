package com.lynxdeer.lynxlib.utils.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemUtils {
	
	/**
	 * Gives a player an item, but drops it on the ground if they don't have space
	 * @return Whether an item was dropped.
	 */
	public static boolean giveDropItem(Player p, ItemStack item) {
		
		HashMap<Integer, ItemStack> itemsDropped = p.getInventory().addItem(item);
		
		Set<Map.Entry<Integer, ItemStack>> entrySet = itemsDropped.entrySet();
		if (entrySet.size() > 0) {
			entrySet.forEach((entry) -> {
				int count = entry.getKey();
				ItemStack i = entry.getValue().clone();
				i.setAmount(count);
				p.getWorld().dropItemNaturally(p.getLocation(), i);
			});
			return true;
		}
		
		return false;
	}

}
