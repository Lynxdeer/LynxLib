package com.lynxdeer.lynxlib.commands.tabcompleters;

import com.lynxdeer.lynxlib.LL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AllPlayers implements TabCompleter {
	
	public static ArrayList<String> getOnlinePlayerNames() {
		ArrayList<String> ret = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) ret.add(p.getName());
		return ret;
	}
	
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (args.length == 1) return LL.tabComplete(getOnlinePlayerNames(), args[args.length - 1]);
		return null;
	}
}
