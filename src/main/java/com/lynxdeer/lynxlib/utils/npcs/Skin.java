package com.lynxdeer.lynxlib.utils.npcs;

import com.lynxdeer.lynxlib.LL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Skin {
	
	public static ArrayList<Skin> skins = new ArrayList<>();
	public static int nextFreeId = 0;
	
	public int id;
	boolean ready = false;
	public Map<BodyPartType, CompletableFuture<String>> futureTextures;
	public Map<BodyPartType, String> readyTextures;
	
	public Skin(String head, Map<BodyPartType, CompletableFuture<String>> finTextures) {
		
		id = nextFreeId++;
		
		this.futureTextures = finTextures;
		this.readyTextures = new HashMap<>();
		this.readyTextures.put(BodyPartType.HEAD, head);
		
		futureTextures.forEach((k, v) -> v.thenAccept(s -> readyTextures.put(k, s)));
		
		CompletableFuture.allOf(futureTextures.values().toArray(new CompletableFuture[0])).thenRun(() -> {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/LynxLib/skins/" + id + ".lynxskin"))) {
				writer.write("HEAD:::" + this.readyTextures.get(BodyPartType.HEAD));
				writer.newLine();
				for (CompletableFuture<String> future : this.futureTextures.values()) {
					writer.write(futureTextures.entrySet().stream().filter(entry -> entry.getValue().equals(future)).map(Map.Entry::getKey).findFirst().orElse(null).name());
					writer.write(":::");
					writer.write(future.join());
					writer.newLine();
				}
				ready = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		skins.add(id, this);
	}
	
	public Skin(int id, Map<BodyPartType, String> textures) {
		this.id = id;
		this.ready = true;
		this.readyTextures = textures;
		this.futureTextures = null;
		skins.add(this);
	}
	
	public static void loadSkins() {
		LL.debugFine("loading skins");
		try {
			
			int highestId = -1;
			for (File f : Files.walk(Paths.get("plugins/LynxLib/skins/")).filter(Files::isRegularFile).map(p -> p.toFile()).toArray(File[]::new)) {
				
				LL.debugFine("found a file");
				
				Map<BodyPartType, String> textures = new HashMap<>();
				
				if (!f.getName().endsWith(".lynxskin")) continue;
				
				int id = Integer.parseInt(f.getName().split("\\.")[0]);
				if (id > highestId) highestId = id;
				
				for (String s : Files.readAllLines(f.toPath())) {
					String[] split = s.split(":::");
					LL.debugFine(BodyPartType.valueOf(split[0]).name());
					textures.put(BodyPartType.valueOf(split[0]), split[1]);
				}
				
				new Skin(id, textures);
				
			}
			
			nextFreeId = highestId + 1;
			
		} catch (IOException ignored) {}
	}
	
}
