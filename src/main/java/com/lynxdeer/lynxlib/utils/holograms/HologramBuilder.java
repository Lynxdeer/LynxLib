package com.lynxdeer.lynxlib.utils.holograms;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

public class HologramBuilder {
	
	public Location location;
	
	public Component text = Component.empty();
	public Display.Billboard billboard = Display.Billboard.CENTER;
	public boolean background = true;
	public float size = 1;
	
	public HologramBuilder(Location loc, Component... originalText) {
		location = loc;
		text = Component.join(JoinConfiguration.separator(Component.newline()), originalText);
	}
	public HologramBuilder(Location loc, String... originalText) {
		
		location = loc;
		text = Component.text(originalText[0]);
		for (String s : originalText)
			if (!s.equals(originalText[0])) // Needed so that there's not a new line at the start
				text = text.append(Component.newline().append(Component.text(s)));
		
	}
	
	public HologramBuilder setSize(float amount) { size = amount; return this; }
	
	public HologramBuilder setBillboard(Display.Billboard bill) { billboard = bill; return this; }
	
	public HologramBuilder removeBackground() { background = false; return this; }
	
	public TextDisplay build() {
		return location.getWorld().spawn(location, TextDisplay.class, h -> {
			h.setDefaultBackground(background);
			h.setBillboard(billboard);
			h.setShadowed(false);
			h.text(text);
			h.setTransformation(
				new Transformation(
					h.getTransformation().getTranslation(),
					h.getTransformation().getLeftRotation(),
					new Vector3f(size, size, size),
					h.getTransformation().getRightRotation()
			));
		});
	}
	
}
