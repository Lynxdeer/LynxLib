package com.lynxdeer.lynxlib.utils.misc;

import com.lynxdeer.lynxlib.LL;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class FileObjectContainer {
	
	
	
	public static Class<?>[] supportedClasses = {
		String.class, Character.class,
		Integer.class, Float.class, Double.class, Short.class, Long.class,
		Byte.class,
		UUID.class
	};
	
	public Class<?> identifier;
	public Class<?>[] order;
	
	public LinkedHashMap<Object, Object[]> savedItems = new LinkedHashMap<>();
	
	public FileObjectContainer(Class<?> identifier, Class<?>... order) {
		this.identifier = identifier;
		this.order = order;
	}
	
	public void add(Object identifier, Object... itemsToSave) {
		savedItems.put(identifier, itemsToSave);
	}
	
	public <T> Object[] get(T identifier) {
		if (!identifier.getClass().equals(this.identifier)) {
			Bukkit.getLogger().severe("Tried to use identifier " + identifier.getClass().getName() + " to get from a FileObjectContainer. Should be " + this.identifier.getName() + "!");
			return null;
		}
		return savedItems.get(identifier);
	}
	
	
	public void saveFile(String path) {
//		LL.debug("Size: {}", savedItems.size());
		try {
			
			File file = new File(path);
			if (file.exists()) file.delete();
			file.createNewFile();
			
			FileOutputStream outStream = new FileOutputStream(file);
			
			byte[] header = { (byte)'f', (byte)'c', (byte)'1' }; // File container version 1
			byte[] size = ByteBuffer.allocate(4).putInt(savedItems.size()).array();
			
			outStream.write(header);
			outStream.write(size);
			
			// TODO: DEBUG THIS
			
			for (Map.Entry<Object, Object[]> entry : savedItems.entrySet()) {
				
				Object identifier = entry.getKey();
				Object[] values = entry.getValue();
				
				byte[] rawIdentifier = toBytes(identifier);
				outStream.write(rawIdentifier);
				
//				LL.debug("Identifier: {}", rawIdentifier);
				for (Object obj : values) {
					byte[] rawValue = toBytes(obj);
					outStream.write(rawValue);
//					LL.debug("Value: {}", rawValue);
				}
				
				// Flush the stream to ensure all data is written? Whatever that means
				outStream.flush();
			}
			
			outStream.flush();
			outStream.close();
			
		} catch (FileNotFoundException exception) {
			Bukkit.getLogger().severe("Tried to save a FileObjectContainer, but file was somehow not found.");
		} catch (IOException exception) {
			Bukkit.getLogger().severe("Tried to save a FileObjectContainer, but encountered an IOException. \nStacktrace:\n" + Arrays.toString(exception.getStackTrace()));
		}
	}
	
	public LinkedHashMap<Object, Object[]> loadFile(String path) {
		
		LinkedHashMap<Object, Object[]> input = new LinkedHashMap<>();
		
		try {
			
			File file = new File(path);
			
			FileInputStream inputStream = new FileInputStream(file);
			byte[] header = new byte[3];
			if (inputStream.read(header) == 3 && header[0] == (byte) 'f' && header[1] == (byte) 'c') {
				switch (header[2]) {
					case (byte) '1' -> {
						
						
						byte[] rawCount = new byte[4];
						inputStream.read(rawCount);
						int entryCount = fromBytes(rawCount, Integer.class);
						
						for (int i = 0; i < entryCount; i++) {
							int identifierSize = sizeof(this.identifier);
							byte[] rawIdentifier = new byte[identifierSize];
							if (inputStream.read(rawIdentifier) == -1)
								Bukkit.getLogger().severe("Tried to read an identifier from a FileObjectContainer, but the file ended.");
							byte[][] rawContentArray = new byte[this.order.length][0]; // Why is it reversed??? Arrays be weird man
							int index = 0;
							for (Class<?> contentType : this.order) {
								if (contentType == String.class) {
									byte[] rawStringSize = new byte[4];
									inputStream.read(rawStringSize);
									int stringSize = ByteBuffer.wrap(rawStringSize).getInt();
									byte[] rawContent = new byte[stringSize];
									inputStream.read(rawContent);
									rawContentArray[index] = rawContent;
								} else {
									int typeSize = sizeof(contentType);
									byte[] rawContent = new byte[typeSize];
//									LL.debug("Reading content type " + contentType.getSimpleName() + " at index " + index);
									if (inputStream.read(rawContent) == -1)
										Bukkit.getLogger().severe("Tried to read content type + " + contentType.getSimpleName() + " from a FileObjectContainer at index + " + index + ", but the file ended.");
									rawContentArray[index] = rawContent;
								}
								index++;
								
							}
							
							Object identifier = fromBytes(rawIdentifier, this.identifier);
							Object[] values = new Object[this.order.length];
							
							int index2 = 0;
							for (Class<?> type : this.order) {
								byte[] rawValue = rawContentArray[index2];
//								LL.debug("size: {} | type: {}", sizeof(type), type.getSimpleName());
								Object value = fromBytes(rawValue, type);
								values[index2] = value;
								index2++;
								
							}
							input.put(identifier, values);
							// read identifier
							// read objects in order
						}
					
					
					}
				}
			} else {
				Bukkit.getLogger().severe("Tried to read a FileObjectContainer, but the file header was invalid.");
				LL.debug("Invalid file header. Header found: {} {} {}", header[0], header[1], header[2]);
			}
			
			inputStream.close();
			
		} catch (FileNotFoundException exception) {
			Bukkit.getLogger().severe("Tried to read a FileObjectContainer, but file was somehow not found. \nStacktrace:\n" + Arrays.toString(exception.getStackTrace()));
		} catch (IOException exception) {
			Bukkit.getLogger().severe("Tried to read a FileObjectContainer, but encountered an IOException. \nStacktrace:\n" + Arrays.toString(exception.getStackTrace()));
		}
		
		return input;
	
	}
	
	public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
		
		if (clazz == String.class) return (T) new String(bytes, StandardCharsets.UTF_8);
		
		else if (clazz == Integer.class) return clazz.cast(ByteBuffer.wrap(bytes).getInt());
		else if (clazz == Float.class) return clazz.cast(ByteBuffer.wrap(bytes).getFloat());
		else if (clazz == Double.class) return clazz.cast(ByteBuffer.wrap(bytes).getDouble());
		else if (clazz == Short.class) return clazz.cast(ByteBuffer.wrap(bytes).getShort());
		else if (clazz == Long.class) return clazz.cast(ByteBuffer.wrap(bytes).getLong());
		else if (clazz == Character.class) return clazz.cast(ByteBuffer.wrap(bytes).getChar());
		
		else if (clazz == Byte.class) return clazz.cast(bytes[0]);
		
		else if (clazz == UUID.class) {
			ByteBuffer bb = ByteBuffer.wrap(bytes);
			return (T) new UUID(bb.getLong(), bb.getLong());
		}
		
		return null;
		
	}
	
	public static byte[] toBytes(Object obj) {
		byte[] out = {};
		
		if (obj instanceof String s) {
			
			byte[] length = ByteBuffer.allocate(4).putInt(s.length()).array();
			byte[] content = s.getBytes(StandardCharsets.UTF_8);
			
			out = new byte[length.length + content.length];
			
			System.arraycopy(length, 0, out, 0, length.length);
			System.arraycopy(content, 0, out, length.length, content.length);
			
		}
		
		else if (obj instanceof Integer v) return ByteBuffer.allocate(4).putInt(v).array();
		else if (obj instanceof Float v) return ByteBuffer.allocate(4).putFloat(v).array();
		else if (obj instanceof Double v) return ByteBuffer.allocate(4).putDouble(v).array();
		else if (obj instanceof Short v) return ByteBuffer.allocate(2).putShort(v).array();
		else if (obj instanceof Long v) return ByteBuffer.allocate(8).putLong(v).array();
		else if (obj instanceof Character v) return ByteBuffer.allocate(1).putChar(v).array();
		
		else if (obj instanceof Byte v) return new byte[] {v};
		
		else if (obj instanceof UUID uuid) {
		
			ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
			buffer.putLong(uuid.getMostSignificantBits());
			buffer.putLong(uuid.getLeastSignificantBits());
			return buffer.array();
		
		}
		
		
		
		return out;
	}
	
	public static int sizeof(Class<?> clazz) {
		if (clazz == Integer.class || clazz == Float.class || clazz == Double.class)
			return 4;
		if (clazz == Short.class)
			return 2;
		if (clazz == Long.class)
			return 8;
		if (clazz == UUID.class)
			return 16;
		if (clazz == Character.class || clazz == Byte.class)
			return 1;
		return 0;
	}
	
	@Override
	public FileObjectContainer clone() {
		return new FileObjectContainer(this.identifier, this.order);
	}
	
}
