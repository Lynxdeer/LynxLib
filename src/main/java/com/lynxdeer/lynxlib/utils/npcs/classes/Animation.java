package com.lynxdeer.lynxlib.utils.npcs.classes;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Animation {
	
	public String name;
	public ArrayList<Keyframe> keyframes = new ArrayList<>();
	
	
	
	private static final Gson gson = new Gson();
	
	public Animation(String bbmodelPath) {
		
	
		
	}
	
	public static void loadAnimations(String bbModelPath) {
		
		File animationFile = new File(bbModelPath);
		if (!animationFile.exists()) {
			Bukkit.getLogger().severe("Tried to load an animation, but the file didn't exist.");
			return;
		}
		try {
			
			String fullJsonString = Files.toString(animationFile, Charset.defaultCharset());
			JsonObject fullJson = gson.fromJson(fullJsonString, JsonObject.class);
			
			JsonArray animations = fullJson.getAsJsonArray("animations");
			
			
			
		} catch (IOException e) {
			Bukkit.getLogger().severe("Tried to load an animation, but encountered an IO exception.");
		}
		
	}
	
}
