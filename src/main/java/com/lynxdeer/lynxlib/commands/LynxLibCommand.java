package com.lynxdeer.lynxlib.commands;

import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.items.ItemBuilder;
import com.lynxdeer.lynxlib.utils.misc.ExtraNamedTextColor;
import com.lynxdeer.lynxlib.utils.misc.TextUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class LynxLibCommand implements CommandExecutor {
	
	public static final ItemStack[] debugTools = {
			new ItemBuilder(Material.LIGHT_BLUE_DYE).name("§9§lDebug Tool 0").pdc(LynxLib.getLLPlugin(), "debugTool", 0).build(),
			new ItemBuilder(Material.CYAN_DYE).name("§3§lDebug Tool 1").pdc(LynxLib.getLLPlugin(), "debugTool", 1).build(),
			new ItemBuilder(Material.GRAY_DYE).name("§7§lDebug Tool 2").pdc(LynxLib.getLLPlugin(), "debugTool", 2).build(),
			new ItemBuilder(Material.PURPLE_DYE).name("§5§lDebug Tool 3").pdc(LynxLib.getLLPlugin(), "debugTool", 3).build(),
			new ItemBuilder(Material.ORANGE_DYE).name("§6§lDebug Tool 4").pdc(LynxLib.getLLPlugin(), "debugTool", 4).build(),
			new ItemBuilder(Material.LIME_DYE).name("§a§lDebug Tool 5").pdc(LynxLib.getLLPlugin(), "debugTool", 5).build(),
			new ItemBuilder(Material.YELLOW_DYE).name("§e§lDebug Tool 6").pdc(LynxLib.getLLPlugin(), "debugTool", 6).build(),
			new ItemBuilder(Material.MAGENTA_DYE).name("§d§lDebug Tool 7").pdc(LynxLib.getLLPlugin(), "debugTool", 7).build(),
			new ItemBuilder(Material.RED_DYE).name("§c§lDebug Tool 8").pdc(LynxLib.getLLPlugin(), "debugTool", 8).build(),
			new ItemBuilder(Material.LIGHT_GRAY_DYE).name("§7§lDebug Tool 9").pdc(LynxLib.getLLPlugin(), "debugTool", 9).build(),
			new ItemBuilder(Material.PINK_DYE).name("§d§lDebug Tool 10").pdc(LynxLib.getLLPlugin(), "debugTool", 10).build(),
			new ItemBuilder(Material.GREEN_DYE).name("§2§lDebug Tool 11").pdc(LynxLib.getLLPlugin(), "debugTool", 11).build(),
			new ItemBuilder(Material.BLUE_DYE).name("§9§lDebug Tool 12").pdc(LynxLib.getLLPlugin(), "debugTool", 12).build(),
			new ItemBuilder(Material.WHITE_DYE).name("§f§lDebug Tool 13").pdc(LynxLib.getLLPlugin(), "debugTool", 13).build(),
			new ItemBuilder(Material.BLACK_DYE).name("§0§lDebug Tool 14").pdc(LynxLib.getLLPlugin(), "debugTool", 14).build(),
			new ItemBuilder(Material.BROWN_DYE).name(TextUtils.getColoredBoldedComponent("Debug Tool 15", ExtraNamedTextColor.SADDLEBROWN)).pdc(LynxLib.getLLPlugin(), "debugTool", 15).build()
	};
	public static boolean consoleDebug = true;
	public static boolean consoleFineDebug = true;
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		// This is so that I can still use this command for when I need to debug without op
		if (!sender.getName().equals("Lynxdeer") && !sender.isOp()) {
			sender.sendMessage("§cInsufficient permissions!");
			return true;
		}
		
		if (args != null && args.length > 0) {
			if (args[0].equalsIgnoreCase("debug")) {
				if (sender instanceof Player p) {
					p.setMetadata("debug", new FixedMetadataValue(LynxLib.getLLPlugin(),!(p.hasMetadata("debug") && p.getMetadata("debug").get(0).asBoolean())));
					p.sendMessage("§eToggled debug messages to §6" + p.getMetadata("debug").get(0).asBoolean());
				} else {
					consoleDebug = !consoleDebug;
					sender.sendMessage("§eToggled console debug messages to §6" + consoleDebug);
				}
			}
			if (args[0].equalsIgnoreCase("fine")) {
				if (sender instanceof Player p) {
					p.setMetadata("fine", new FixedMetadataValue(LynxLib.getLLPlugin(),!(p.hasMetadata("fine") && p.getMetadata("fine").get(0).asBoolean())));
					p.sendMessage("§eToggled fine debug messages to §6" + p.getMetadata("fine").get(0).asBoolean());
				} else {
					consoleFineDebug = !consoleFineDebug;
					sender.sendMessage("§eToggled fine debug messages to §6" + consoleFineDebug);
				}
			}
			if (args[0].equalsIgnoreCase("givedebugtool")) {
				if (sender instanceof Player p) {
					int id = args.length > 1 ? Integer.parseInt(args[1]) : 0;
					p.sendMessage("§aGiven you a debug tool with id " + id + ".");
					p.getInventory().addItem(debugTools[id]);
				}
			}
		}
		
		//if (!(sender instanceof Player p)) return true;
		return true;
	}
}
