package com.lynxdeer.lynxlib;

import com.lynxdeer.lynxlib.commands.LynxLibCommand;
import com.lynxdeer.lynxlib.events.LLEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

public class LL {
	
	// General methods I use in every project anyway.
	
	public static void debug(String s) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.isOp() && (!p.hasMetadata("debug") || p.getMetadata("debug").get(0).asBoolean()))
				p.sendMessage(Component
						.text("[Debug] " + s)
						.hoverEvent(HoverEvent.showText(Component.text("§eDo §6/lynxlib debug §eto toggle debug messages!\n§fClick to copy!")))
						.clickEvent(ClickEvent.copyToClipboard(s)));
		if (LynxLibCommand.consoleDebug)
			Bukkit.getLogger().log(Level.INFO, "[Debug] " + s);
	}
	
	public static void debugFine(String s) {
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.isOp() && (p.hasMetadata("debug") && p.getMetadata("debug").get(0).asBoolean()))
				p.sendMessage(Component
						.text("§7§o[Debug] " + s)
						.hoverEvent(HoverEvent.showText(Component.text("§eDo §6/lynxlib fine §eto toggle fine debug messages!\n§fClick to copy!")))
						.clickEvent(ClickEvent.copyToClipboard(s)));
		if (LynxLibCommand.consoleFineDebug)
			Bukkit.getLogger().log(Level.INFO, "[Fine] " + s);
	}
	
	// Inspired by Geode's logging system, which is a lot better than just taking an object and changing it to a string.
	public static void debug(String format, Object... args) {
		debug(debugBuilder(format, args));
	}
	public static void debugFine(String format, Object... args) {
		debugFine(debugBuilder(format, args));
	}
	
	public static void debug(Object s) { debug("{}", s);}
	public static void debugFine(Object s) { debugFine("{}", s);}
	
	public static String debugBuilder(String format, Object... args) {
		for (Object s : args) {
			String fs;
			if (s == null) fs = "null";
			else if (s instanceof Location) fs = String.format("x: %.2f, y: %.2f, z: %.2f", ((Location) s).getX(), ((Location) s).getY(), ((Location) s).getZ());
			else if (s instanceof Vector) fs = String.format("x: %.2f, y: %.2f, z: %.2f", ((Vector) s).getX(), ((Vector) s).getY(), ((Vector) s).getZ());
			else if (s instanceof Vector3f) fs = String.format("x: %.2f, y: %.2f, z: %.2f", ((Vector3f) s).z(), ((Vector3f) s).y(), ((Vector3f) s).z());
			else if (s instanceof Quaternionf) fs = "x:" + ((Quaternionf) s).x + ", y:" + ((Quaternionf) s).y + ", z:" + ((Quaternionf) s).z  + ", w:" + ((Quaternionf) s).w;
			else if (s instanceof AxisAngle4f) fs = "x:" + ((AxisAngle4f) s).x + ", y:" + ((AxisAngle4f) s).y + ", z:" + ((AxisAngle4f) s).z  + ", angle:" + ((AxisAngle4f) s).angle;
			else if (s instanceof Collection<?> a) { fs = "["; for (Object o : a) fs += o.toString() + ", "; fs+="]"; }
			else if (s instanceof Map<?, ?> a) { fs = "["; for (Map.Entry<?, ?> o : a.entrySet()) fs += "(" + o.getKey().toString() + ", " + o.getValue().toString() + ") "; fs+="]"; }
			else fs = s.toString();
			
			if (format.contains("{}")) format = format.replaceFirst("\\{}", fs);
		}
		return format;
	}
	
	public static void bindDebugTool(int id, Consumer<Player> runnable) {
		LLEvents.debugToolFunctions[id] = runnable;
	}
	
	public static List<String> tabComplete(List<?> s, String t) {
		List<String> ret = new ArrayList<>();
		List<String> strs= new ArrayList<>();
		for (Object a : s) {strs.add(a.toString());}
		for (String b : strs) if (b.startsWith(t)) ret.add(b);
		return ret;
	}
	
	public static ArrayList<String> getPlayerNames(List<Player> players) {
		ArrayList<String> ret = new ArrayList<>();
		for (Player p : players) ret.add(p.getName());
		return ret;
	}
	
	// In preparation for 1.20.3.
	public static int millisToTicks(int millis) {
		return (millis / (1000 / LynxLib.tickRate));
	}
	
	public static void playClearSound(Player p, String s, float volume, float pitch) {
		Location loc = p.getEyeLocation();
		p.playSound(loc, s, 1000, pitch);
		for (Player lp : Bukkit.getOnlinePlayers()) {
			if (lp == p) continue;
			lp.playSound(loc, s, 4 * volume/Math.round(loc.distance(lp.getEyeLocation())), pitch);
		}
	}
	
}
