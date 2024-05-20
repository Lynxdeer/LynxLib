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
			futureSkins.put(BodyPartType.CHEST, LynxLib.mineskinClient.generateUpload(getBodyTop(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.WAIST, LynxLib.mineskinClient.generateUpload(getBodyBottom(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_ARM, LynxLib.mineskinClient.generateUpload(getRightArmTop(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FOREARM, LynxLib.mineskinClient.generateUpload(getRightArmBottom(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_ARM, LynxLib.mineskinClient.generateUpload(getLeftArmTop(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FOREARM, LynxLib.mineskinClient.generateUpload(getLeftArmBottom(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_LEG, LynxLib.mineskinClient.generateUpload(getRightLegTop(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.RIGHT_FORELEG, LynxLib.mineskinClient.generateUpload(getRightLegBottom(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_LEG, LynxLib.mineskinClient.generateUpload(getLeftLegTop(baseSkin)).thenApply(skin -> skin.data.texture.value));
			futureSkins.put(BodyPartType.LEFT_FORELEG, LynxLib.mineskinClient.generateUpload(getLeftLegBottom(baseSkin)).thenApply(skin -> skin.data.texture.value));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Skin(head, futureSkins);
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
	
	public static BufferedImage getBodyTop(BufferedImage skin) {
		BufferedImage body1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body1.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{20, 20, 8, 8}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{32, 20, 8, 8}, 8, 8);
		BufferedImage up = getClipResize(skin, new Integer[]{20, 16, 8, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{16, 20, 4, 8}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{28, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{20, 36, 8, 8}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{32, 36, 8, 8}, 8, 8);
		BufferedImage upnd = getClipResize(skin, new Integer[]{20, 32, 8, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{16, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{28, 36, 4, 8}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(upnd, null, 40, 0);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		return body1;
	}
	
	public static BufferedImage getBodyBottom(BufferedImage skin) {
		BufferedImage body2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body2.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{20, 28, 8, 4}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{32, 28, 8, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{16, 28, 4, 4}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{28, 28, 4, 4}, 8, 8);
		BufferedImage down = getClipResize(skin, new Integer[]{28, 16, 8, 4}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{20, 44, 8, 4}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{32, 44, 8, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{16, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{28, 44, 4, 4}, 8, 8);
		BufferedImage downnd = getClipResize(skin, new Integer[]{28, 32, 8, 4}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		graphics.drawImage(downnd, null, 48, 0);
		return body2;
	}
	
	public static BufferedImage getRightArmTop(BufferedImage skin) {
		BufferedImage right_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm1.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{44, 20, 4, 8}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{52, 20, 4, 8}, 8, 8);
		BufferedImage up = getClipResize(skin, new Integer[]{44, 16, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{40, 20, 4, 8}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{48, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{44, 36, 4, 8}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{52, 36, 4, 8}, 8, 8);
		BufferedImage upnd = getClipResize(skin, new Integer[]{44, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{40, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{48, 36, 4, 8}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(upnd, null, 40, 0);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		return right_arm1;
	}
	
	public static BufferedImage getRightArmBottom(BufferedImage skin) {
		BufferedImage right_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm2.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{44, 28, 4, 4}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{52, 28, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{40, 28, 4, 4}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{48, 28, 4, 4}, 8, 8);
		BufferedImage down = getClipResize(skin, new Integer[]{48, 16, 4, 4}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{44, 44, 4, 4}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{52, 44, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{40, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{48, 44, 4, 4}, 8, 8);
		BufferedImage downnd = getClipResize(skin, new Integer[]{48, 32, 4, 4}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		graphics.drawImage(downnd, null, 48, 0);
		return right_arm2;
	}
	
	public static BufferedImage getLeftArmTop(BufferedImage skin) {
		BufferedImage left_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm1.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{36, 52, 4, 8}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{44, 52, 4, 8}, 8, 8);
		BufferedImage up = getClipResize(skin, new Integer[]{36, 48, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{32, 52, 4, 8}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{40, 52, 4, 8}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{52, 52, 4, 8}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{60, 52, 4, 8}, 8, 8);
		BufferedImage upnd = getClipResize(skin, new Integer[]{52, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{48, 52, 4, 8}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{56, 52, 4, 8}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(upnd, null, 40, 0);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		return left_arm1;
	}
	
	public static BufferedImage getLeftArmBottom(BufferedImage skin) {
		BufferedImage left_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm2.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{36, 60, 4, 4}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{44, 60, 4, 4}, 8, 8);
		BufferedImage down = getClipResize(skin, new Integer[]{40, 48, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{32, 60, 4, 4}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{40, 60, 4, 4}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{52, 60, 4, 4}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{60, 60, 4, 4}, 8, 8);
		BufferedImage downnd = getClipResize(skin, new Integer[]{56, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{48, 60, 4, 4}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{56, 60, 4, 4}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		graphics.drawImage(downnd, null, 48, 0);
		return left_arm2;
	}
	
	public static BufferedImage getRightLegTop(BufferedImage skin) {
		BufferedImage right_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg1.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{4, 20, 4, 8}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{12, 20, 4, 8}, 8, 8);
		BufferedImage up = getClipResize(skin, new Integer[]{4, 16, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{0, 20, 4, 8}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{8, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{4, 36, 4, 8}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{12, 36, 4, 8}, 8, 8);
		BufferedImage upnd = getClipResize(skin, new Integer[]{4, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{0, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{8, 36, 4, 8}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(upnd, null, 40, 0);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		return right_leg1;
	}
	
	public static BufferedImage getRightLegBottom(BufferedImage skin) {
		BufferedImage right_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg2.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{4, 28, 4, 4}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{12, 28, 4, 4}, 8, 8);
		BufferedImage down = getClipResize(skin, new Integer[]{8, 16, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{0, 28, 4, 4}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{8, 28, 4, 4}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{4, 44, 4, 4}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{8, 32, 4, 4}, 8, 8);
		BufferedImage downnd = getClipResize(skin, new Integer[]{8, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{0, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{8, 44, 4, 4}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		graphics.drawImage(downnd, null, 48, 0);
		return right_leg2;
	}
	
	public static BufferedImage getLeftLegTop(BufferedImage skin) {
		BufferedImage left_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg1.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{20, 52, 4, 8}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{28, 52, 4, 8}, 8, 8);
		BufferedImage up = getClipResize(skin, new Integer[]{20, 48, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{16, 52, 4, 8}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{24, 52, 4, 8}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{4, 52, 4, 8}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{12, 52, 4, 8}, 8, 8);
		BufferedImage upnd = getClipResize(skin, new Integer[]{4, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{0, 52, 4, 8}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{8, 52, 4, 8}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(up, null, 8, 0);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(upnd, null, 40, 0);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		return left_leg1;
	}
	
	public static BufferedImage getLeftLegBottom(BufferedImage skin) {
		BufferedImage left_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg2.createGraphics();
		BufferedImage front = getClipResize(skin, new Integer[]{20, 60, 4, 4}, 8, 8);
		BufferedImage back = getClipResize(skin, new Integer[]{28, 60, 4, 4}, 8, 8);
		BufferedImage down = getClipResize(skin, new Integer[]{24, 48, 4, 4}, 8, 8);
		BufferedImage right = getClipResize(skin, new Integer[]{16, 60, 4, 4}, 8, 8);
		BufferedImage left = getClipResize(skin, new Integer[]{24, 60, 4, 4}, 8, 8);
		BufferedImage frontnd = getClipResize(skin, new Integer[]{4, 60, 4, 4}, 8, 8);
		BufferedImage backnd = getClipResize(skin, new Integer[]{12, 60, 4, 4}, 8, 8);
		BufferedImage downnd = getClipResize(skin, new Integer[]{8, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = getClipResize(skin, new Integer[]{0, 60, 4, 4}, 8, 8);
		BufferedImage leftnd = getClipResize(skin, new Integer[]{8, 60, 4, 4}, 8, 8);
		graphics.drawImage(front, null, 8, 8);
		graphics.drawImage(back, null, 24, 8);
		graphics.drawImage(right, null, 0, 8);
		graphics.drawImage(left, null, 16, 8);
		graphics.drawImage(down, null, 16, 0);
		graphics.drawImage(frontnd, null, 40, 8);
		graphics.drawImage(backnd, null, 56, 8);
		graphics.drawImage(rightnd, null, 32, 8);
		graphics.drawImage(leftnd, null, 48, 8);
		graphics.drawImage(downnd, null, 48, 0);
		return left_leg2;
	}
	
	public static BufferedImage getClipResize(BufferedImage base, Integer[] offset, int sizex, int sizey) {
		int x = offset[0];
		int y = offset[1];
		int w = offset[2];
		int h = offset[3];
		BufferedImage image = base.getSubimage(x, y, w, h);
		image = scale(image, sizex, sizey);
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
