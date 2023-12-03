package com.lynxdeer.lynxlib.utils.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;

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
	
	public static Component parseColorCodes(String text) {
		return Component.text(ChatColor.translateAlternateColorCodes('&', text));
	}
	
}