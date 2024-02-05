package com.lynxdeer.lynxlib.nms;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS1194 {
	
	public static void hidePlayerOnTab(Player p) {
	
	}
	
	public static void createSimpleNPC(Player player) {
		
		CraftPlayer craftPlayer = (CraftPlayer) player;
		EntityPlayer ep = craftPlayer.getHandle();
		
		MinecraftServer server = ep.c;
		
		PlayerConnection connection = ep.b;
		
		connection.a(); // send packet
		
	}
	
	
}
