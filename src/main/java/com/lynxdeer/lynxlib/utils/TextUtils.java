package com.lynxdeer.lynxlib.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

public class TextUtils {
	
	public String componentToString(Component component) {
		return ((TextComponent) component).content();
	}
	
	public static String getFilterableString(String s) {
		return s.replaceAll("[^a-zA-Z0-9]", "")
				.replaceAll("0", "o")
				.replaceAll("1", "i")
				.replaceAll("3", "e")
				.replaceAll("4", "a")
				.replaceAll("5", "s")
				.replaceAll("8", "b")
				.toLowerCase();
	}
	
}
