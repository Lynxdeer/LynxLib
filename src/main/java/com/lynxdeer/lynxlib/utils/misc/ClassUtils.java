package com.lynxdeer.lynxlib.utils.misc;

import java.lang.reflect.Array;

public class ClassUtils {
	
	
	public static <T> T nullSafeVersionOfType(Class<T> type) {
		
		// Woohoo, casting!
		
		if (type == String.class) return type.cast("");
		if (type == Byte.class) return type.cast((byte) 0x00);
		if (type == Integer.class) return type.cast(0);
		if (type == Short.class) return type.cast((short) 0);
		if (type == Long.class) return type.cast(0L);
		if (type == Float.class) return type.cast(0.0f);
		if (type == Double.class) return type.cast(0.0);
		if (type == Boolean.class) return type.cast(false);
		
		if (type.isArray()) return type.cast(Array.newInstance(type.getComponentType(), 0));
		
		// If all else fails, just return null
		return null;
	}
	
}
