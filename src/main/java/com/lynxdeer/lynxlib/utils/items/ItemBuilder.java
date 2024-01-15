package com.lynxdeer.lynxlib.utils.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

public class ItemBuilder {
	
	public Material material;
	public int amount;
	public int customModelData = 0;
	public Component name;
	public List<Component> lore;
	public List<ItemFlag> itemFlags = new ArrayList<>();
	public boolean unbreakable;
	public int[] color = null;
	public HashMap<Enchantment, Integer> enchantments = new HashMap<>();
	public Set<Material> canPlace;
	public Set<Material> canDestroy;
	
	
	
	public ItemBuilder(Material material) {
		this(material, 1);
	}
	
	public ItemBuilder(Material material, int amount) {
		this.material = (material == null) ? Material.AIR : material;
		this.amount = amount;
	}
	
	
	
	public ItemBuilder name(String name) { this.name = Component.text(name); return this; }
	public ItemBuilder name(Component name) { this.name = name; return this; }
	
	
	public ItemBuilder lore(String... lore) {
		List<Component> convertedComponents = new ArrayList<>();
		List.of(lore).forEach(line -> convertedComponents.add(Component.text(line)));
		this.lore = convertedComponents;
		return this;
	}
	
	public ItemBuilder lore(Component... lore) {
		this.lore = List.of(lore);
		return this;
	}
	
	public ItemBuilder canDestroy(Material... canDestroy) {
		this.canDestroy = Set.of(canDestroy);
		return this;
	}
	public ItemBuilder canPlace(Material... canPlace) {
		this.canPlace = Set.of(canPlace);
		return this;
	}
	
	public ItemBuilder enchant(Enchantment enchantment, int level) {
		enchantments.put(enchantment, level);
		return this;
	}
	
	
	
	public ItemBuilder amount(int amount) {
		if(((amount > material.getMaxStackSize()) || (amount <= 0))) amount = 1;
		this.amount = amount;
		return this;
	}
	public ItemBuilder amountUnsafe(int amount) { this.amount = amount; return this; }


	public ItemBuilder customModelData(int customModelData) { this.customModelData = customModelData; return this; }
	public ItemBuilder addItemFlags(ItemFlag... flags) { this.itemFlags.addAll(List.of(flags)); return this; }
	public ItemBuilder unbreakable() { unbreakable = true; return this; }
	
	public ItemBuilder dye(int red, int green, int blue) { color = new int[] {red, green, blue}; return this; }
	
	
	
	public ItemStack build() {
		
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		
		ItemMeta meta = item.getItemMeta();
		
		if (this.canDestroy != null) meta.setCanDestroy(this.canDestroy);
		if   (this.canPlace != null) meta.setCanPlaceOn(this.canPlace);
		
		meta.displayName(name);
		if (customModelData > 0) meta.setCustomModelData(customModelData);
		if (unbreakable) meta.setUnbreakable(true); // Setting unbreakable to false could potentially cause some problems. This works instead.
		meta.lore(lore);
		for (ItemFlag flag : itemFlags)
			meta.addItemFlags(flag);
		for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet())
			meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
		
		if (color != null && meta instanceof LeatherArmorMeta lam) {
			lam.setColor(Color.fromRGB( color[0], color[1], color[2] ));
			item.setItemMeta(lam);
		} else item.setItemMeta(meta);
		
		return item;
		
	}

}
