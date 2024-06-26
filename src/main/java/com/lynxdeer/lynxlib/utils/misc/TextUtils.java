package com.lynxdeer.lynxlib.utils.misc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TextUtils {
	
	public static String componentToString(Component component) {
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
	
	public static Component parse(String text) { // Short for parseColorCodes. Because I would want to use this a lot, I've shortened it.
		return Component.text(ChatColor.translateAlternateColorCodes('&', text));
	}
	
	public static String capitalizeFirstLetter(String original) {
		return (original.substring(0, 1).toUpperCase() + original.toLowerCase().substring(1));
	}
	
	public static void sendTitle(Player p, Component title, Component subtitle, int fadeIn, int stay, int fadeOut) {p.showTitle(Title.title(title, subtitle, Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut))));}
	public static void sendSubtitle(Player p, Component subtitle, int fadeIn, int stay, int fadeOut) {sendTitle(p, Component.text("§0"), subtitle, fadeIn, stay, fadeOut);}
	public static void sendSubtitleFade(Player p, Component subtitle, int fadeOut) { sendSubtitle(p, subtitle, 0, 0, fadeOut); }
	
	public static String pascalCaseEnum(Enum<?> value) {
		StringBuilder result = new StringBuilder();
		boolean next = false;
		for (char a : value.name().toCharArray()) {
			if (a == '_') { next = true; result.append(' '); }
			else if (next) { result.append(Character.toUpperCase(a)); next = false; }
			else result.append(Character.toLowerCase(a));
		}
		return (result.substring(0, 1).toUpperCase() + result.substring(1));
	}
	
	public static String formatTime(long seconds) {
		int hours = (int) (seconds / 3600);
		int minutes = (int) ((seconds % 3600) / 60);
		int secs = (int) (seconds % 60);
		return (hours == 0) ? String.format("%02d:%02d", minutes, secs) : String.format("%02d:%02d:%02d", hours, minutes, secs);
	}
	
	public static String wrapNewLines(String original) {
		return wrapNewLines(original, 1);
	}
	
	public static String wrapNewLines(String original, int amount) {
		if (amount < 0) return original;
		return "§r" + "\n".repeat(amount) + original + "\n".repeat(amount) + "§r";
	}
	
	public static Component wrapNewLines(Component original, int amount) {
		Component ret = Component.empty();
		for (int i = 0; i < amount; i++) ret = ret.append(Component.newline());
		ret = ret.append(original);
		for (int i = 0; i < amount; i++) ret = ret.append(Component.newline());
		return ret;
	}
	
	public static Component wrapNewLines(Component original) {
		return wrapNewLines(original, 1);
	}
	
	public static Component getColoredComponent(String text, TextColor color) {
		return Component.text(text).color(color).decoration(TextDecoration.ITALIC, false);
	}
	public static Component getColoredBoldedComponent(String text, TextColor color) {
		return Component.text(text).color(color).decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.BOLD, true);
	}
	
	public static String smallText(String s) {
		StringBuilder ret = new StringBuilder();
		for (char c : s.toLowerCase().toCharArray()) {
			ret.append(smallChar(c));
		}
		return ret.toString();
	}
	
	public static char smallChar(char original) {
		return switch (original) {
			case 'a' -> 'ᴀ';
			case 'b' -> 'ʙ';
			case 'c' -> 'ᴄ';
			case 'd' -> 'ᴅ';
			case 'e' -> 'ᴇ';
			case 'f' -> 'ғ';
			case 'g' -> 'ɢ';
			case 'h' -> 'ʜ';
			case 'i' -> 'ɪ';
			case 'j' -> 'ᴊ';
			case 'k' -> 'ᴋ';
			case 'l' -> 'ʟ';
			case 'm' -> 'ᴍ';
			case 'n' -> 'ɴ';
			case 'o' -> 'ᴏ';
			case 'p' -> 'ᴘ';
			case 'q' -> 'ǫ';
			case 'r' -> 'ʀ';
			case 's' -> 's';
			case 't' -> 'ᴛ';
			case 'u' -> 'ᴜ';
			case 'v' -> 'ᴠ';
			case 'w' -> 'ᴡ';
			case 'x' -> 'x';
			case 'y' -> 'ʏ';
			case 'z' -> 'ᴢ';
			case '0' -> '₀';
			case '1' -> '₁';
			case '2' -> '₂';
			case '3' -> '₃';
			case '4' -> '₄';
			case '5' -> '₅';
			case '6' -> '₆';
			case '7' -> '₇';
			case '8' -> '₈';
			case '9' -> '₉';
			default -> original;
		};
	}
	
}
