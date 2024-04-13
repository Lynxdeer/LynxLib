package com.lynxdeer.lynxlib.events;

import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.items.PDCUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public class LLEvents implements org.bukkit.event.Listener {
	
	public static Consumer<Player>[] debugToolFunctions = new Consumer[16];
	
	@EventHandler
	public void onDebugToolUse(PlayerInteractEvent event) {
		int pdc = PDCUtils.getDefaultingPDC(LynxLib.getLLPlugin(), "debugTool", event.getItem(), PersistentDataType.INTEGER, -1);
		if (event.getAction().isRightClick() && event.getItem() != null && pdc > -1) {
			LL.debugFine("§7§oRan debug function {}", pdc);
			debugToolFunctions[pdc].accept(event.getPlayer());
		}
	}
	
}
