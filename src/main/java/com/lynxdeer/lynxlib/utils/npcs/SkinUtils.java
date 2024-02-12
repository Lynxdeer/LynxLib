package com.lynxdeer.lynxlib.utils.npcs;

import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class SkinUtils {
	
	public String base64ToUrl(String base64) {
		byte[] urlBytes = Base64.getDecoder().decode(base64);
		return new String(urlBytes, StandardCharsets.UTF_8);
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
	
	public BufferedImage getBody1(BufferedImage skin) {
		BufferedImage body1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body1.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{20, 20, 8, 8}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{32, 20, 8, 8}, 8, 8);
		BufferedImage up = this.getClipResize(skin, new Integer[]{20, 16, 8, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{16, 20, 4, 8}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{28, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{20, 36, 8, 8}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{32, 36, 8, 8}, 8, 8);
		BufferedImage upnd = this.getClipResize(skin, new Integer[]{20, 32, 8, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{16, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{28, 36, 4, 8}, 8, 8);
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
	
	public BufferedImage getBody2(BufferedImage skin) {
		BufferedImage body2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = body2.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{20, 28, 8, 4}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{32, 28, 8, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{16, 28, 4, 4}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{28, 28, 4, 4}, 8, 8);
		BufferedImage down = this.getClipResize(skin, new Integer[]{28, 16, 8, 4}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{20, 44, 8, 4}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{32, 44, 8, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{16, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{28, 44, 4, 4}, 8, 8);
		BufferedImage downnd = this.getClipResize(skin, new Integer[]{28, 32, 8, 4}, 8, 8);
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
	
	public BufferedImage getRightArm1(BufferedImage skin) {
		BufferedImage right_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm1.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{44, 20, 4, 8}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{52, 20, 4, 8}, 8, 8);
		BufferedImage up = this.getClipResize(skin, new Integer[]{44, 16, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{40, 20, 4, 8}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{48, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{44, 36, 4, 8}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{52, 36, 4, 8}, 8, 8);
		BufferedImage upnd = this.getClipResize(skin, new Integer[]{44, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{40, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{48, 36, 4, 8}, 8, 8);
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
	
	public BufferedImage getRightArm2(BufferedImage skin) {
		BufferedImage right_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_arm2.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{44, 28, 4, 4}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{52, 28, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{40, 28, 4, 4}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{48, 28, 4, 4}, 8, 8);
		BufferedImage down = this.getClipResize(skin, new Integer[]{48, 16, 4, 4}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{44, 44, 4, 4}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{52, 44, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{40, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{48, 44, 4, 4}, 8, 8);
		BufferedImage downnd = this.getClipResize(skin, new Integer[]{48, 32, 4, 4}, 8, 8);
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
	
	public BufferedImage getLeftArm1(BufferedImage skin) {
		BufferedImage left_arm1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm1.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{36, 52, 4, 8}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{44, 52, 4, 8}, 8, 8);
		BufferedImage up = this.getClipResize(skin, new Integer[]{36, 48, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{32, 52, 4, 8}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{40, 52, 4, 8}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{52, 52, 4, 8}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{60, 52, 4, 8}, 8, 8);
		BufferedImage upnd = this.getClipResize(skin, new Integer[]{52, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{48, 52, 4, 8}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{56, 52, 4, 8}, 8, 8);
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
	
	public BufferedImage getLeftArm2(BufferedImage skin) {
		BufferedImage left_arm2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_arm2.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{36, 60, 4, 4}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{44, 60, 4, 4}, 8, 8);
		BufferedImage down = this.getClipResize(skin, new Integer[]{40, 48, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{32, 60, 4, 4}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{40, 60, 4, 4}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{52, 60, 4, 4}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{60, 60, 4, 4}, 8, 8);
		BufferedImage downnd = this.getClipResize(skin, new Integer[]{56, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{48, 60, 4, 4}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{56, 60, 4, 4}, 8, 8);
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
	
	public BufferedImage getRightLeg1(BufferedImage skin) {
		BufferedImage right_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg1.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{4, 20, 4, 8}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{12, 20, 4, 8}, 8, 8);
		BufferedImage up = this.getClipResize(skin, new Integer[]{4, 16, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{0, 20, 4, 8}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{8, 20, 4, 8}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{4, 36, 4, 8}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{12, 36, 4, 8}, 8, 8);
		BufferedImage upnd = this.getClipResize(skin, new Integer[]{4, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{0, 36, 4, 8}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{8, 36, 4, 8}, 8, 8);
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
	
	public BufferedImage getRightLeg2(BufferedImage skin) {
		BufferedImage right_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = right_leg2.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{4, 28, 4, 4}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{12, 28, 4, 4}, 8, 8);
		BufferedImage down = this.getClipResize(skin, new Integer[]{8, 16, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{0, 28, 4, 4}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{8, 28, 4, 4}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{4, 44, 4, 4}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{8, 32, 4, 4}, 8, 8);
		BufferedImage downnd = this.getClipResize(skin, new Integer[]{8, 32, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{0, 44, 4, 4}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{8, 44, 4, 4}, 8, 8);
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
	
	public BufferedImage getLeftLeg1(BufferedImage skin) {
		BufferedImage left_leg1 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg1.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{20, 52, 4, 8}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{28, 52, 4, 8}, 8, 8);
		BufferedImage up = this.getClipResize(skin, new Integer[]{20, 48, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{16, 52, 4, 8}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{24, 52, 4, 8}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{4, 52, 4, 8}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{12, 52, 4, 8}, 8, 8);
		BufferedImage upnd = this.getClipResize(skin, new Integer[]{4, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{0, 52, 4, 8}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{8, 52, 4, 8}, 8, 8);
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
	
	public BufferedImage getLeftLeg2(BufferedImage skin) {
		BufferedImage left_leg2 = new BufferedImage(64, 64, 2);
		Graphics2D graphics = left_leg2.createGraphics();
		BufferedImage front = this.getClipResize(skin, new Integer[]{20, 60, 4, 4}, 8, 8);
		BufferedImage back = this.getClipResize(skin, new Integer[]{28, 60, 4, 4}, 8, 8);
		BufferedImage down = this.getClipResize(skin, new Integer[]{24, 48, 4, 4}, 8, 8);
		BufferedImage right = this.getClipResize(skin, new Integer[]{16, 60, 4, 4}, 8, 8);
		BufferedImage left = this.getClipResize(skin, new Integer[]{24, 60, 4, 4}, 8, 8);
		BufferedImage frontnd = this.getClipResize(skin, new Integer[]{4, 60, 4, 4}, 8, 8);
		BufferedImage backnd = this.getClipResize(skin, new Integer[]{12, 60, 4, 4}, 8, 8);
		BufferedImage downnd = this.getClipResize(skin, new Integer[]{8, 48, 4, 4}, 8, 8);
		BufferedImage rightnd = this.getClipResize(skin, new Integer[]{0, 60, 4, 4}, 8, 8);
		BufferedImage leftnd = this.getClipResize(skin, new Integer[]{8, 60, 4, 4}, 8, 8);
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
	
	public BufferedImage getClipResize(BufferedImage base, Integer[] offset, int sizex, int sizey) {
		int x = offset[0];
		int y = offset[1];
		int w = offset[2];
		int h = offset[3];
		BufferedImage image = base.getSubimage(x, y, w, h);
		image = this.scale(image, (double)sizex, (double)sizey);
		return image;
	}
	
	public BufferedImage scale(BufferedImage src, double y, double x) {
		BufferedImage after = new BufferedImage((int)x, (int)y, 2);
		AffineTransform at = new AffineTransform();
		at.scale(x / (double)src.getWidth(), y / (double)src.getHeight());
		AffineTransformOp scaleOp = new AffineTransformOp(at, 1);
		after = scaleOp.filter(src, after);
		return after;
	}
	
}
