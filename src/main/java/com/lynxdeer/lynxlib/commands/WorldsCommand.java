package com.lynxdeer.lynxlib.commands;

import com.lynxdeer.lynxlib.LL;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WorldsCommand implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		
		if (!(commandSender instanceof Player p)) {commandSender.sendMessage("can't use this as console sorry");return false;}
		
		if (strings == null || strings.length == 0)  {
			p.sendMessage("§3§lWORLDS");
			for (World w : Bukkit.getWorlds()) {
				Component c = Component.text("§b- " + w.getName())
						.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/world " + w.getName()))
						.hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, Component.text("§7§oClick to warp to " + w.getName())));
				p.sendMessage(c);
			}
			return true;
		}
		
		if (strings[0].equalsIgnoreCase("load")) {
			if (strings.length == 1) {p.sendMessage("§cPlease specify the world to load!");return true;}
			Bukkit.getServer().createWorld(new WorldCreator(strings[1]));
			p.sendMessage("Loaded world " + strings[1]);
			return true;
		}
		
		if (strings.length > 1) {p.sendMessage("§cToo many arguments!");return true;}
		
		p.sendMessage("§bWarped to world " + strings[0]);
		p.teleport(Bukkit.getWorld(strings[0]).getSpawnLocation());
		return false;
	}
	
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		return LL.tabComplete(Bukkit.getWorlds(), s);
	}
}
