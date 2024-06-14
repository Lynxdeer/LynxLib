package com.lynxdeer.lynxlib.utils.hud.components;

import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.hud.PlayerHudContainer;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;

public class HudItemComponent extends HudComponent {
	
	private ItemDisplay display = null;
	private ItemStack item;
	
	public HudItemComponent(float x, float y, float scale, int z, ItemStack item) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.scaleX = scale;
		this.scaleY = scale;
		
		this.item = item;
		
	}
	
	public HudItemComponent(float x, float y, float scale, ItemStack item) {
		new HudItemComponent(x, y, scale, 0, item);
	}
	
	public void setItem(ItemStack item) {
		this.item = item;
		this.display.setItemStack(item);
	}
	
	public HudItemComponent setParent(PlayerHudContainer parent) {
		this.parent = parent;
		
		this.display = parent.getPlayer().getWorld().spawn(parent.getPlayer().getLocation(), ItemDisplay.class, t -> {
			
			t.setItemStack(this.item);
			t.setBrightness(new Display.Brightness(15, 15));
			t.setBillboard(Display.Billboard.CENTER);
			
			t.setVisibleByDefault(false);
			this.parent.getPlayer().showEntity(LynxLib.getLLPlugin(), t);
			
			this.parent.getPlayer().addPassenger(t);
			
		});
		
		return this;
	}
	
	@Override
	public Display getDisplay() {
		return display;
	}
	
	@Override
	public void specificUpdate() {
	
	}
	
}
