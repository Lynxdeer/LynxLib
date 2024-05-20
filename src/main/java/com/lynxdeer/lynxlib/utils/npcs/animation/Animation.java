package com.lynxdeer.lynxlib.utils.npcs.animation;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lynxdeer.lynxlib.utils.npcs.animation.classes.Keyframe;
import com.lynxdeer.lynxlib.utils.npcs.animation.enums.AnimationChannel;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartParent;
import org.bukkit.Bukkit;
import org.joml.Vector3f;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

public class Animation {
	
	public static ArrayList<Animation> animations = new ArrayList<>();
	
	public String name;
	public boolean loop;
	public int length;
	
	public ArrayList<Keyframe> keyframes = new ArrayList<>();
	
	
	
	private static final Gson gson = new Gson();
	
	private Animation(String name, boolean loop, int lengthTicks) {
		
		this.name = name;
		this.loop = loop;
		this.length = lengthTicks;
		
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
			
			JsonArray jsonAnimations = fullJson.getAsJsonArray("animations");
			
			for (int i = 0; i < jsonAnimations.size(); i++) {
				
				JsonObject animationObject = jsonAnimations.get(i).getAsJsonObject();
				
				String animationName = animationObject.get("name").getAsString();
				boolean loop = !animationObject.get("loop").getAsString().equals("once");
				int animationLength = (int) animationObject.get("length").getAsDouble() * 20;
				
				Animation animation = new Animation(animationName, loop, animationLength);
				
				JsonObject parts = animationObject.get("animators").getAsJsonObject();
				for (Map.Entry<String, JsonElement> partElement : parts.entrySet()) {
					JsonObject part = partElement.getValue().getAsJsonObject();
					String partName = part.get("name").getAsString();
					
					JsonArray partKeyframes = part.get("keyframes").getAsJsonArray();
					for (int j = 0; j < partKeyframes.size(); j++) {
						
						JsonObject kf = partKeyframes.get(j).getAsJsonObject();
						
						AnimationChannel channel = AnimationChannel.valueOf(kf.get("channel").getAsString().toUpperCase());
						Vector3f dataPoints = new Vector3f(
								(float) (kf.get("data_points").getAsJsonObject().get("x").getAsDouble()),
								(float) (kf.get("data_points").getAsJsonObject().get("y").getAsDouble()),
								(float) (kf.get("data_points").getAsJsonObject().get("z").getAsDouble())  );
						
						if (channel == AnimationChannel.ROTATION) {
							dataPoints.x = (float) Math.toRadians(dataPoints.x);
							dataPoints.y = (float) Math.toRadians(dataPoints.y);
							dataPoints.z = (float) Math.toRadians(dataPoints.z);
						}
						
						animation.keyframes.add(
								new Keyframe(
										(int) kf.get("time").getAsDouble() * 20,
										BodyPartParent.valueOf(partName.toUpperCase()),
										channel,
										dataPoints
								));
						
					}
					
					animations.add(animation);
					
				}
				
			}
			
		} catch (IOException e) {
			Bukkit.getLogger().severe("Tried to load an animation, but encountered an IO exception.");
		}
		
	}
	
	public static Animation get(String name) {
		for (Animation anim : animations) {
			if (anim.name.equals(name))
				return anim;
		}
		return null;
	}
	
	
}
