package com.lynxdeer.lynxlib.utils.items;

import com.lynxdeer.lynxlib.utils.misc.ClassUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PDCUtils {
	
	/**
	 * Null-safe version of PersistentDataContainer#get.
	 */
	public static <T, Z> T getPDC(JavaPlugin plugin, String key, ItemStack item, PersistentDataType<Z, T> type) {
		
		return getDefaultingPDC(plugin, key, item, type, ClassUtils.nullSafeVersionOfType(type.getComplexType()));
		
	}
	
	public static <T, Z> T getPDC(JavaPlugin plugin, String key, Entity entity, PersistentDataType<Z, T> type) {
		
		return getDefaultingPDC(plugin, key, entity, type, ClassUtils.nullSafeVersionOfType(type.getComplexType()));
		
	}
	
	public static <T extends Number> T addPDC(JavaPlugin plugin, String key, Entity entity, T count) {
		Number newValue = 0;
		Number oldValue = getPDC(plugin, key, entity, PersistentDataType.INTEGER);
		
		if (count instanceof Integer) newValue = oldValue.intValue() + count.intValue();
		else if (count instanceof Short) newValue = oldValue.shortValue() + count.shortValue();
		else if (count instanceof Long) newValue = oldValue.longValue() + count.longValue();
		else if (count instanceof Float) newValue = oldValue.floatValue() + count.floatValue();
		else if (count instanceof Double) newValue = oldValue.doubleValue() + count.doubleValue();
		
		setPDC(plugin, key, entity, newValue);
		return (T) newValue;
	}
	
	
	public static void setPDC(JavaPlugin plugin, String key, Entity entity, Object value) {
		
		PersistentDataContainer dataContainer = entity.getPersistentDataContainer();
		NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
		
		if (value instanceof String) dataContainer.set(namespacedKey, PersistentDataType.STRING, (String) value);
		else if (value instanceof Integer) dataContainer.set(namespacedKey, PersistentDataType.INTEGER, (Integer) value);
		else if (value instanceof Short) dataContainer.set(namespacedKey, PersistentDataType.SHORT, (Short) value);
		else if (value instanceof Long) dataContainer.set(namespacedKey, PersistentDataType.LONG, (Long) value);
		else if (value instanceof Float) dataContainer.set(namespacedKey, PersistentDataType.FLOAT, (Float) value);
		else if (value instanceof Double) dataContainer.set(namespacedKey, PersistentDataType.DOUBLE, (Double) value);
		else if (value instanceof Byte) dataContainer.set(namespacedKey, PersistentDataType.BYTE, (Byte) value);
		else if (value instanceof Boolean) dataContainer.set(namespacedKey, PersistentDataType.BOOLEAN, (Boolean) value);
		else if (value instanceof byte[]) dataContainer.set(namespacedKey, PersistentDataType.BYTE_ARRAY, (byte[]) value);
		else if (value instanceof int[]) dataContainer.set(namespacedKey, PersistentDataType.INTEGER_ARRAY, (int[]) value);
		else if (value instanceof long[]) dataContainer.set(namespacedKey, PersistentDataType.LONG_ARRAY, (long[]) value);
		else throw new IllegalStateException("Unexpected type in PDCUtils#setPDC: " + value.getClass().getTypeName());
		
	}
	
	/**
	 * Null-safe version of PersistentDataContainer#get.
	 * @param defaultValue The default value that should be returned if the item does not have the pdc key.
	 */
	public static <T, Z> T getDefaultingPDC(JavaPlugin plugin, String key, ItemStack item, PersistentDataType<Z, T> type, Object defaultValue) {
		
		if (item == null || !item.hasItemMeta()) {
			return type.getComplexType().cast(defaultValue);
		}
		
		PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
		NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
		
		return container.has(namespacedKey) ? container.get(namespacedKey, type) : type.getComplexType().cast(defaultValue);
		
	}
	
	/**
	 * Null-safe version of PersistentDataContainer#get.
	 * @param defaultValue The default value that should be returned if the item does not have the pdc key.
	 */
	public static <T, Z> T getDefaultingPDC(JavaPlugin plugin, String key, Entity entity, PersistentDataType<Z, T> type, Object defaultValue) {
		
		if (entity == null || entity.isDead()) {
			return type.getComplexType().cast(defaultValue);
		}
		
		PersistentDataContainer container = entity.getPersistentDataContainer();
		NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
		
		return container.has(namespacedKey) ? container.get(namespacedKey, type) : type.getComplexType().cast(defaultValue);
		
	}
	
}
