package com.lynxdeer.lynxlib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LL {
	
	// General methods I use in every project anyway.
	
	public static void debug(Object s) {
		if (s instanceof Location) s = "x" + ((Location) s).getX() + " y" + ((Location) s).getY() + " z" + ((Location) s).getZ();
		if (s instanceof Vector) s = "x" + ((Vector) s).getX() + " y" + ((Vector) s).getY() + " z" + ((Vector) s).getZ();
		for (Player p : Bukkit.getOnlinePlayers())
			if (p.isOp() && (p.hasMetadata("debug") && p.getMetadata("debug").get(0).asBoolean()))
				p.sendMessage(Component.text("[Debug] " + s).hoverEvent(HoverEvent.showText(Component.text("§eDo §6/lynxlib debug §eto toggle debug messages!"))));
		Bukkit.getLogger().log(Level.INFO, "[Debug] " + s);
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
	
}
