package com.lynxdeer.lynxlib.utils.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ItemBuilder {
	
	public Material material;
	public int amount;
	public int customModelData = 0;
	public Component name;
	public List<Component> lore = new ArrayList<>();
	public List<ItemFlag> itemFlags = new ArrayList<>();
	public boolean unbreakable;
	public boolean removeAttackDelay = false;
	public int[] color = null;
	public HashMap<Enchantment, Integer> enchantments = new HashMap<>();
	public Set<Material> canPlace;
	public Set<Material> canDestroy;
	
	public JavaPlugin creatingPlugin;
	public HashMap<String, Object> persistentDataContainer = new HashMap<>();
	
	
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
	
	public ItemBuilder removeAttackDelay() {
		removeAttackDelay = true;
		return this;
	}
	
	public ItemBuilder lore(List<Component> lore) {
		this.lore = lore;
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
	
	/**
	 * Persistent data container.
	 */
	public ItemBuilder pdc(JavaPlugin plugin, String key, Object value) {
		creatingPlugin = plugin;
		persistentDataContainer.put(key, value);
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
		if  (this.removeAttackDelay) meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "noDelay", 40, AttributeModifier.Operation.ADD_NUMBER));
		
		meta.displayName(name);
		if (customModelData > 0) meta.setCustomModelData(customModelData);
		if (unbreakable) meta.setUnbreakable(true); // Setting unbreakable to false could potentially cause some problems. This works instead.
		meta.lore(lore);
		for (ItemFlag flag : itemFlags)
			meta.addItemFlags(flag);
		for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet())
			meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
		for (Map.Entry<String, Object> persistentDataValue : persistentDataContainer.entrySet()) {
			String key = persistentDataValue.getKey();
			Object value = persistentDataValue.getValue();
			
			if (value instanceof String v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.STRING, v);
			else if (value instanceof Integer v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.INTEGER, v);
			else if (value instanceof Float v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.FLOAT, v);
			else if (value instanceof Long v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.LONG, v);
			else if (value instanceof Byte v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.BYTE, v);
			else if (value instanceof byte[] v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.BYTE_ARRAY, v);
			else if (value instanceof int[] v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.INTEGER_ARRAY, v);
			else if (value instanceof long[] v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.LONG_ARRAY, v);
			else if (value instanceof Boolean v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.BOOLEAN, v);
			else if (value instanceof Double v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.DOUBLE, v);
			else if (value instanceof Short v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.SHORT, v);
			else if (value instanceof PersistentDataContainer v) meta.getPersistentDataContainer().set(new NamespacedKey(creatingPlugin, key), PersistentDataType.TAG_CONTAINER, v);
			
		}
		
		if (color != null && meta instanceof LeatherArmorMeta lam) {
			lam.setColor(Color.fromRGB( color[0], color[1], color[2] ));
			item.setItemMeta(lam);
		} else item.setItemMeta(meta);
		
		return item;
		
	}

}
