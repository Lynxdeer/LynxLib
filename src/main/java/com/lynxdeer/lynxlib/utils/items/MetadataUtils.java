package com.lynxdeer.lynxlib.utils.items;

import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.misc.ClassUtils;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MetadataUtils {
	
	/**
	 * Note that you have to use this method to use getMetadata, because otherwise the plugins will be different!
	 */
	public static void setMetadata(Entity e, String key, Object value) {
		e.setMetadata(key, new FixedMetadataValue(LynxLib.getLLPlugin(), value));
	}
	
	public static void toggleMetadata(Entity e, String key) {
		setMetadata(e, key, !getMetadata(e, key, Boolean.class));
	}
	
	public static void addMetadata(Entity e, String key, int amount) {
		setMetadata(e, key, getMetadata(e, key, Integer.class) + amount);
	}
	public static void addMetadata(Entity e, String key, float amount) {
		setMetadata(e, key, getMetadata(e, key, Float.class) + amount);
	}
	public static void addMetadata(Entity e, String key, double amount) {
		setMetadata(e, key, getMetadata(e, key, Double.class) + amount);
	}
	
	public static <T> T getMetadata(Entity e, String key, Class<T> type) {
		
		if (!e.hasMetadata(key))
			return ClassUtils.nullSafeVersionOfType(type);
		
		MetadataValue value = e.getMetadata(key).get(0);
		if (type == String.class)
			return type.cast(value.asString());
		else if (type == Integer.class)
			return type.cast(value.asInt());
		else if (type == Boolean.class)
			return type.cast(value.asBoolean());
		else if (type == Double.class)
			return type.cast(value.asDouble());
		else if (type == Long.class)
			return type.cast(value.asLong());
		else if (type == Short.class)
			return type.cast(value.asShort());
		else if (type == Byte.class)
			return type.cast(value.asByte());
		else if (type == Float.class)
			return type.cast(value.asFloat());
		
		return ClassUtils.nullSafeVersionOfType(type);
	}
	
	
}
