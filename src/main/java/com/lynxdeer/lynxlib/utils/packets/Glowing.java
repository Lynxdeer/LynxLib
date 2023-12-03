package com.lynxdeer.lynxlib.utils.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Lists;
import com.lynxdeer.lynxlib.LynxLib;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;

public class Glowing {
	
	public static Entity getEntityById(World world, int id) {
		for (Entity e : world.getEntities()) if (e.getEntityId() == id) return e;
		return null;
	}
	
	public static void registerGlowPacketHandler() {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(LynxLib.getCurrentPlugin(), PacketType.Play.Server.ENTITY_METADATA) {
			@Override
			public void onPacketSending(PacketEvent event) {
				
				PacketContainer packet = event.getPacket();
				
				Player packetReceiver = event.getPlayer();
				Entity glower = getEntityById(packetReceiver.getWorld(), packet.getIntegers().read(0));
				
				if (glower == null) return;
				
				boolean shouldGlow = false;
				if (glower.isGlowing()) shouldGlow = true;
				
				if (packetReceiver != glower)
					packet.getDataValueCollectionModifier().write(0, wrappedData(
							(ArrayList<WrappedDataValue>) packet.getDataValueCollectionModifier().read(0),
							packetReceiver, shouldGlow));
				
				
			}
		});
	}
	
	
	public static void glowForPlayer(Entity e, Player p, boolean glow) {
		
		if (e == p) return;
		
		ProtocolManager pm = ProtocolLibrary.getProtocolManager();
		PacketContainer packet = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		packet.getIntegers().write(0, e.getEntityId());
		
		packet.getDataValueCollectionModifier().write(0, wrappedData(Lists.newArrayList(), p, glow));
		
		pm.sendServerPacket(p, packet);
		
		//PacketContainer teamPacket = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
	}
	
	public static ArrayList<WrappedDataValue> wrappedData(ArrayList<WrappedDataValue> currentList, Player target, boolean bool) {
		
		WrappedDataWatcher watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
		WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
		watcher.setEntity(target); //Set the new data watcher's target
		watcher.setObject(0, serializer, (byte) (0x40)); //Set status to glowing, found on protocol page
		
		final ArrayList<WrappedDataValue> wrappedDataValueList = currentList;
		watcher.getWatchableObjects().stream().filter(Objects::nonNull).forEach(entry -> {
			final WrappedDataWatcher.WrappedDataWatcherObject dataWatcherObject = entry.getWatcherObject();
			if (bool) wrappedDataValueList.add(new WrappedDataValue(dataWatcherObject.getIndex(), dataWatcherObject.getSerializer(), (byte) (0x40) )); // entry.getRawValue()
			else wrappedDataValueList.remove(new WrappedDataValue(dataWatcherObject.getIndex(), dataWatcherObject.getSerializer(), (byte) (0x40) ));
		});
		
		return wrappedDataValueList;
		
	}
	
}
