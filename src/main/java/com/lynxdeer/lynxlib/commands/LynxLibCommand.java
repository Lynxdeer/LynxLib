package com.lynxdeer.lynxlib.commands;

import com.lynxdeer.lynxlib.LynxLib;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class LynxLibCommand extends BukkitCommand {
	
	public static boolean consoleDebug = true;
	
	public LynxLibCommand(@NotNull String name) {
		super(name);
	}
	
	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
		
		// This is so that I can still use this command for when I need to debug without op
		if (!sender.getName().equals("Lynxdeer") && !sender.isOp()) {
			sender.sendMessage("§cInsufficient permissions!");
			return true;
		}
		
		if (args != null && args.length > 0) {
			if (args[0].equalsIgnoreCase("debug")) {
				if (sender instanceof Player p) {
					p.setMetadata("debug", new FixedMetadataValue(LynxLib.getCurrentPlugin(),!(p.hasMetadata("debug") && p.getMetadata("debug").get(0).asBoolean())));
					p.sendMessage("§eToggled debug messages to §6" + p.getMetadata("debug").get(0).asBoolean());
				} else {
					consoleDebug = !consoleDebug;
					sender.sendMessage("§eToggled console debug messages to §6" + consoleDebug);
				}
			}
		}
		
		//if (!(sender instanceof Player p)) return true;
		return true;
	}
	
}
