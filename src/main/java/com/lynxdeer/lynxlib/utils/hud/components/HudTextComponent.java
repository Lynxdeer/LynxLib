package com.lynxdeer.lynxlib.utils.hud.components;

import com.lynxdeer.lynxlib.LynxLib;
import com.lynxdeer.lynxlib.utils.hud.PlayerHudContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;

public class HudTextComponent extends HudComponent {
	
	private TextDisplay display = null;
	private Component text;
	
	// Ranges from 0-255
	public byte textOpacity = (byte) 255;
	
	public boolean enableBackground = false;
	public boolean shadowed = true;
	public Color backgroundColor;
	
	public HudTextComponent(float x, float y, float scale, int z, Component text) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.scaleX = scale;
		this.scaleY = scale;
		
		this.text = text;
		
	}
	
	public HudTextComponent(float x, float y, float scale, Component text) {
		new HudTextComponent(x, y, scale, 0, text);
	}
	
	public void setText(Component text) {
		this.text = text;
		this.display.text(text);
	}
	
	public HudTextComponent setBackground(boolean enableBackground) { this.enableBackground = enableBackground; return this; }
	public HudTextComponent setShadowed(boolean shadowed) { this.shadowed = shadowed; return this; }
	public HudTextComponent setBackgroundColor(Color color) { this.backgroundColor = color; return this; }
	public HudTextComponent setTextOpacity(int opacity) { this.textOpacity = (byte) opacity; return this; }
	
	public void setOpacity(int opacity) {
		setTextOpacity(opacity);
		if (this.backgroundColor == null) {
			this.backgroundColor = Color.fromARGB(255,0, 0, 0);
		}
		setBackgroundColor(Color.fromARGB(opacity, this.backgroundColor.getRed(), this.backgroundColor.getGreen(), this.backgroundColor.getBlue()));
	}
	
	public HudTextComponent setParent(PlayerHudContainer parent) {
		this.parent = parent;
		
		this.display = parent.getPlayer().getWorld().spawn(parent.getPlayer().getLocation(), TextDisplay.class, t -> {
			
			t.text(this.text);
			t.setDefaultBackground(this.enableBackground);
			t.setShadowed(this.shadowed);
			t.setSeeThrough(true);
			t.setRotation(0, 0);
			t.setAlignment(TextDisplay.TextAlignment.CENTER);
			t.setBrightness(new Display.Brightness(15, 15));
			t.setBillboard(Display.Billboard.CENTER);
			
			if (backgroundColor != null) t.setBackgroundColor(this.backgroundColor);
			
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
		this.display.setBackgroundColor(this.backgroundColor);
		this.display.setTextOpacity(this.textOpacity);
	}
	
}

