package com.lynxdeer.lynxlib.utils.misc;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileObjectContainer {
	
	
	
	public static Class<?>[] supportedClasses = {
		String.class, Character.class,
		Integer.class, Float.class, Double.class
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
		try {
			
			File file = new File(path);
			if (file.exists()) file.delete();
			file.createNewFile();
			
			FileOutputStream outStream = new FileOutputStream(path);
			
		} catch (FileNotFoundException exception) {
			Bukkit.getLogger().severe("Tried to save a FileObjectContainer, but file was somehow not found.");
		} catch (IOException exception) {
			Bukkit.getLogger().severe("Tried to save a FileObjectContainer, but encounered an IOEXception. \nStacktrace:\n" + Arrays.toString(exception.getStackTrace()));
		}
	}
	
}
