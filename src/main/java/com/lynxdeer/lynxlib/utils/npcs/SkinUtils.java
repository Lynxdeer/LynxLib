package com.lynxdeer.lynxlib.utils.npcs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lynxdeer.lynxlib.LL;
import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.npcs.renderer.BodyPartType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class SkinUtils {
	
	public String base64ToUrl(String base64) {
		byte[] urlBytes = Base64.getDecoder().decode(base64);
		return new String(urlBytes, StandardCharsets.UTF_8);
	}
	
	public static String getDecodedSkinJson(Player p) {
		String value = getEncodedSkin(p);
		
		return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
	}
	
	public static String getEncodedSkin(Player p) {
		GameProfile profile = ((CraftPlayer) p).getProfile();
		Property textureProperties = profile.getProperties().get("textures").toArray(new Property[1])[0];
		return textureProperties.getValue();
	}
	
	public static BufferedImage getSkinImage(Player p) {
		
		String decoded = getDecodedSkinJson(p);
		String url = new Gson().fromJson(decoded, JsonObject.class).getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
		
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getSkinImageFromFile(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Skin getSkin(Player p) {
		
		LL.debugFine("ran getSkin");
		for (Skin skin : Skin.skins) {
			LL.debugFine("Looking through skin {}", skin.id);
			if (skin.ready && skin.readyTextures != null && skin.readyTextures.get(BodyPartType.HEAD).equals(getEncodedSkin(p))) {
				LL.debugFine("found ready skin");
				return skin;
			}
		}
		
		return fullSkinToParts(getEncodedSkin(p), getSkinImage(p));
	}
	
	public static Skin fullSkinToParts(String head, BufferedImage baseSkin) {
		Map<BodyPartType, CompletableFuture<String>> futureSkins = new HashMap<>();
		
		try {
			futureSkins.put(BodyPartType.CHEST, LynxLib.mineskinClient.generateUpload(getChest(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.WAIST, LynxLib.mineskinClient.generateUpload(getWaist(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.HIP, LynxLib.mineskinClient.generateUpload(getHips(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_ARM, LynxLib.mineskinClient.generateUpload(getRightArm(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FOREARM, LynxLib.mineskinClient.generateUpload(getRightForearm(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_ARM, LynxLib.mineskinClient.generateUpload(getLeftArm(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FOREARM, LynxLib.mineskinClient.generateUpload(getLeftForearm(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_LEG, LynxLib.mineskinClient.generateUpload(getRightLeg(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FORELEG, LynxLib.mineskinClient.generateUpload(getRightForeleg(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_LEG, LynxLib.mineskinClient.generateUpload(getLeftLeg(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FORELEG, LynxLib.mineskinClient.generateUpload(getLeftForeleg(baseSkin)).thenApply(skin -> skin.data.texture.value));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Skin(head, futureSkins);
	}
	
	public static Skin imageToParts(BufferedImage original) {
		Map<BodyPartType, CompletableFuture<String>> futureSkins = new HashMap<>();
		
		try {
			futureSkins.put(BodyPartType.HEAD, LynxLib.mineskinClient.generateUpload(original).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.CHEST, LynxLib.mineskinClient.generateUpload(getChest(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.WAIST, LynxLib.mineskinClient.generateUpload(getWaist(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.HIP, LynxLib.mineskinClient.generateUpload(getHips(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_ARM, LynxLib.mineskinClient.generateUpload(getRightArm(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FOREARM, LynxLib.mineskinClient.generateUpload(getRightForearm(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_ARM, LynxLib.mineskinClient.generateUpload(getLeftArm(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FOREARM, LynxLib.mineskinClient.generateUpload(getLeftForearm(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_LEG, LynxLib.mineskinClient.generateUpload(getRightLeg(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FORELEG, LynxLib.mineskinClient.generateUpload(getRightForeleg(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_LEG, LynxLib.mineskinClient.generateUpload(getLeftLeg(original)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FORELEG, LynxLib.mineskinClient.generateUpload(getLeftForeleg(original)).thenApply(skin -> skin.data.texture.value));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Skin(futureSkins);
	}
	
	public static ItemStack getPlayerHead(UUID uuid) { return getPlayerHead(Bukkit.getOfflinePlayer(uuid)); }
	public static ItemStack getPlayerHead(String s)  { return getPlayerHead(Bukkit.getOfflinePlayer(s)); }
	
	public static ItemStack getPlayerHead(OfflinePlayer player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwningPlayer(player);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack textureToHead(String texture) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		profile.getProperties().put("textures", new com.mojang.authlib.properties.Property("textures", texture));
		
		try {
			Field field = meta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(meta, profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static void drawSides(
			
			Graphics2D graphics,
			BufferedImage front, BufferedImage back, BufferedImage right, BufferedImage left,
			BufferedImage frontOverlay, BufferedImage backOverlay, BufferedImage rightOverlay, BufferedImage leftOverlay) {
		
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontOverlay, null, 40, 8);
		graphics.drawImage(backOverlay, null, 56, 8);
		graphics.drawImage(rightOverlay, null, 32, 8);
		graphics.drawImage(leftOverlay, null, 48, 8);
		
	}
	
	public static void drawUp(Graphics2D graphics, BufferedImage up, BufferedImage upOverlay) {
		
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(upOverlay, null, 40, 0);
		
	}
	
	public static void drawDown(Graphics2D graphics, BufferedImage down, BufferedImage downOverlay) {
		
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(downOverlay, null, 48, 0);
		
	}
	
	public static BufferedImage getChest(BufferedImage skin) {
		
		BufferedImage body1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body1.createGraphics();
		
		BufferedImage front = resize(skin, 20, 20, 8, 4);
		BufferedImage back =  resize(skin, 32, 20, 8, 4);
		BufferedImage up =    resize(skin, 20, 16, 8, 4);
		BufferedImage right = resize(skin, 16, 20, 4, 4);
		BufferedImage left =  resize(skin, 28, 20, 4, 4);
		BufferedImage frontOverlay = resize(skin, 20, 36, 8, 4);
		BufferedImage backOverlay =  resize(skin, 32, 36, 8, 4);
		BufferedImage upOverlay =    resize(skin, 20, 32, 8, 4);
		BufferedImage rightOverlay = resize(skin, 16, 36, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 28, 36, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawUp(graphics, up, upOverlay);
		
		return body1;
		
	}
	
	public static BufferedImage getWaist(BufferedImage skin) {
		
		BufferedImage body2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body2.createGraphics();
		
		BufferedImage front = resize(skin, 20, 24, 8, 4);
		BufferedImage back =  resize(skin, 32, 24, 8, 4);
		BufferedImage right = resize(skin, 16, 24, 4, 4);
		BufferedImage left =  resize(skin, 28, 24, 4, 4);
		BufferedImage frontOverlay = resize(skin, 20, 44, 8, 4);
		BufferedImage backOverlay =  resize(skin, 32, 44, 8, 4);
		BufferedImage rightOverlay = resize(skin, 16, 44, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 28, 44, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		
		return body2;
		
	}
	
	public static BufferedImage getHips(BufferedImage skin) {
		
		BufferedImage body2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body2.createGraphics();
		
		BufferedImage front = resize(skin, 20, 28, 8, 4);
		BufferedImage back =  resize(skin, 32, 28, 8, 4);
		BufferedImage right = resize(skin, 16, 28, 4, 4);
		BufferedImage left =  resize(skin, 28, 28, 4, 4);
		BufferedImage down =  resize(skin, 28, 16, 8, 4);
		BufferedImage frontOverlay = resize(skin, 20, 44, 8, 4);
		BufferedImage backOverlay =  resize(skin, 32, 44, 8, 4);
		BufferedImage rightOverlay = resize(skin, 16, 44, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 28, 44, 4, 4);
		BufferedImage downOverlay =  resize(skin, 28, 32, 8, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawDown(graphics, down, downOverlay);
		
		return body2;
		
	}
	
	public static BufferedImage getRightArm(BufferedImage skin) {
		
		BufferedImage right_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm1.createGraphics();
		
		BufferedImage front = resize(skin, 44, 20, 4, 8);
		BufferedImage back =  resize(skin, 52, 20, 4, 8);
		BufferedImage up =    resize(skin, 44, 16, 4, 4);
		BufferedImage right = resize(skin, 40, 20, 4, 8);
		BufferedImage left =  resize(skin, 48, 20, 4, 8);
		BufferedImage frontOverlay = resize(skin, 44, 36, 4, 8);
		BufferedImage backOverlay =  resize(skin, 52, 36, 4, 8);
		BufferedImage upOverlay =    resize(skin, 44, 32, 4, 4);
		BufferedImage rightOverlay = resize(skin, 40, 36, 4, 8);
		BufferedImage leftOverlay =  resize(skin, 48, 36, 4, 8);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawUp(graphics, up, upOverlay);
		
		return right_arm1;
		
	}
	
	public static BufferedImage getRightForearm(BufferedImage skin) {
		
		BufferedImage right_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm2.createGraphics();
		
		BufferedImage front = resize(skin, 44, 28, 4, 4);
		BufferedImage back =  resize(skin, 52, 28, 4, 4);
		BufferedImage right = resize(skin, 40, 28, 4, 4);
		BufferedImage left =  resize(skin, 48, 28, 4, 4);
		BufferedImage down =  resize(skin, 48, 16, 4, 4);
		BufferedImage frontOverlay = resize(skin, 44, 44, 4, 4);
		BufferedImage backOverlay =  resize(skin, 52, 44, 4, 4);
		BufferedImage rightOverlay = resize(skin, 40, 44, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 48, 44, 4, 4);
		BufferedImage downOverlay =  resize(skin, 48, 32, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawDown(graphics, down, downOverlay);
		
		return right_arm2;
		
	}
	
	public static BufferedImage getLeftArm(BufferedImage skin) {
		
		BufferedImage left_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm1.createGraphics();
		
		BufferedImage front = resize(skin, 36, 52, 4, 8);
		BufferedImage back =  resize(skin, 44, 52, 4, 8);
		BufferedImage up =    resize(skin, 36, 48, 4, 4);
		BufferedImage right = resize(skin, 32, 52, 4, 8);
		BufferedImage left =  resize(skin, 40, 52, 4, 8);
		BufferedImage frontOverlay = resize(skin, 52, 52, 4, 8);
		BufferedImage backOverlay =  resize(skin, 60, 52, 4, 8);
		BufferedImage upOverlay =    resize(skin, 52, 48, 4, 4);
		BufferedImage rightOverlay = resize(skin, 48, 52, 4, 8);
		BufferedImage leftOverlay =  resize(skin, 56, 52, 4, 8);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawUp(graphics, up, upOverlay);
		
		return left_arm1;
	}
	
	public static BufferedImage getLeftForearm(BufferedImage skin) {
		
		BufferedImage left_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm2.createGraphics();
		
		BufferedImage front = resize(skin, 36, 60, 4, 4);
		BufferedImage back =  resize(skin, 44, 60, 4, 4);
		BufferedImage down =  resize(skin, 40, 48, 4, 4);
		BufferedImage right = resize(skin, 32, 60, 4, 4);
		BufferedImage left =  resize(skin, 40, 60, 4, 4);
		BufferedImage frontOverlay = resize(skin, 52, 60, 4, 4);
		BufferedImage backOverlay =  resize(skin, 60, 60, 4, 4);
		BufferedImage downOverlay =  resize(skin, 56, 48, 4, 4);
		BufferedImage rightOverlay = resize(skin, 48, 60, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 56, 60, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawDown(graphics, down, downOverlay);
		
		return left_arm2;
	}
	
	public static BufferedImage getRightLeg(BufferedImage skin) {
		
		BufferedImage right_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg1.createGraphics();
		
		BufferedImage front = resize(skin, 4, 20, 4, 8);
		BufferedImage back =  resize(skin, 12, 20, 4, 8);
		BufferedImage up =    resize(skin, 4, 16, 4, 4);
		BufferedImage right = resize(skin, 0, 20, 4, 8);
		BufferedImage left =  resize(skin, 8, 20, 4, 8);
		BufferedImage frontOverlay = resize(skin, 4, 36, 4, 8);
		BufferedImage backOverlay =  resize(skin, 12, 36, 4, 8);
		BufferedImage upOverlay =    resize(skin, 4, 32, 4, 4);
		BufferedImage rightOverlay = resize(skin, 0, 36, 4, 8);
		BufferedImage leftOverlay =  resize(skin, 8, 36, 4, 8);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawUp(graphics, up, upOverlay);
		
		return right_leg1;
		
	}
	
	public static BufferedImage getRightForeleg(BufferedImage skin) {
		
		BufferedImage right_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg2.createGraphics();
		
		BufferedImage front = resize(skin, 4, 28, 4, 4);
		BufferedImage back =  resize(skin, 12, 28, 4, 4);
		BufferedImage down =  resize(skin, 8, 16, 4, 4);
		BufferedImage right = resize(skin, 0, 28, 4, 4);
		BufferedImage left =  resize(skin, 8, 28, 4, 4);
		BufferedImage frontOverlay = resize(skin, 4, 44, 4, 4);
		BufferedImage backOverlay =  resize(skin, 8, 32, 4, 4);
		BufferedImage downOverlay =  resize(skin, 8, 32, 4, 4);
		BufferedImage rightOverlay = resize(skin, 0, 44, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 8, 44, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawDown(graphics, down, downOverlay);
		
		return right_leg2;
		
	}
	
	public static BufferedImage getLeftLeg(BufferedImage skin) {
		
		BufferedImage left_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg1.createGraphics();
		
		BufferedImage front = resize(skin, 20, 52, 4, 8);
		BufferedImage back =  resize(skin, 28, 52, 4, 8);
		BufferedImage up =    resize(skin, 20, 48, 4, 4);
		BufferedImage right = resize(skin, 16, 52, 4, 8);
		BufferedImage left =  resize(skin, 24, 52, 4, 8);
		BufferedImage frontOverlay = resize(skin, 4, 52, 4, 8);
		BufferedImage backOverlay =  resize(skin, 12, 52, 4, 8);
		BufferedImage upOverlay =    resize(skin, 4, 48, 4, 4);
		BufferedImage rightOverlay = resize(skin, 0, 52, 4, 8);
		BufferedImage leftOverlay =  resize(skin, 8, 52, 4, 8);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawUp(graphics, up, upOverlay);
		
		return left_leg1;
	}
	
	public static BufferedImage getLeftForeleg(BufferedImage skin) {
		
		BufferedImage left_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg2.createGraphics();
		
		BufferedImage front = resize(skin, 20, 60, 4, 4);
		BufferedImage back =  resize(skin, 28, 60, 4, 4);
		BufferedImage down =  resize(skin, 24, 48, 4, 4);
		BufferedImage right = resize(skin, 16, 60, 4, 4);
		BufferedImage left =  resize(skin, 24, 60, 4, 4);
		BufferedImage frontOverlay = resize(skin, 4, 60, 4, 4);
		BufferedImage backOverlay =  resize(skin, 12, 60, 4, 4);
		BufferedImage downOverlay =  resize(skin, 8, 48, 4, 4);
		BufferedImage rightOverlay = resize(skin, 0, 60, 4, 4);
		BufferedImage leftOverlay =  resize(skin, 8, 60, 4, 4);
		
		drawSides(graphics, front, back, right, left, frontOverlay, backOverlay, rightOverlay, leftOverlay);
		drawDown(graphics, down, downOverlay);
		
		return left_leg2;
		
	}
	
	public static BufferedImage resize(BufferedImage base, int offsetX, int offsetY, int width, int height) {
		
		BufferedImage image = base.getSubimage(offsetX, offsetY, width, height);
		image = scale(image, 8, 8);
		
		return image;
		
	}
	
	public static BufferedImage scale(BufferedImage src, double y, double x) {
		BufferedImage after = new BufferedImage((int)x, (int)y, 2);
		AffineTransform at = new AffineTransform();
		at.scale(x / (double)src.getWidth(), y / (double)src.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, 1);
		after = scaleOp.filter(src, after);
		return after;
	}
	
}
