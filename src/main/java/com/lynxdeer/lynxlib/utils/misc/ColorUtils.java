package com.lynxdeer.lynxlib.utils.misc;

import net.kyori.adventure.text.format.TextColor;

public class ColorUtils {
	
	public static int[] hsvToRgb(float hue, float saturation, float value) {
		if (hue < 0f || hue > 360f || saturation < 0f || saturation > 1f || value < 0f || value > 1f) {
			throw new IllegalArgumentException("Invalid HSV values. Hue: [0, 360], found " + hue + " Saturation and Value: [0, 1], found " + saturation + ", " + value);
		}
		
		int hi = (int) (hue / 60f) % 6;
		float f = (hue / 60f) - (int) (hue / 60f);
		float p = value * (1f - saturation);
		float q = value * (1f - f * saturation);
		float t = value * (1f - (1f - f) * saturation);
		
		float red, green, blue;
		switch (hi) {
			case 0: red = value; green = t; blue = p; break;
			case 1: red = q; green = value; blue = p; break;
			case 2: red = p; green = value; blue = t; break;
			case 3: red = p; green = q; blue = value; break;
			case 4: red = t; green = p; blue = value; break;
			case 5: red = value; green = p; blue = q; break;
			default: red = green = blue = value; break;
		}
		
		int r = Math.round(red * 255);
		int g = Math.round(green * 255);
		int b = Math.round(blue * 255);
		
		return new int[]{r, g, b};
	}
	
	public static TextColor intsToTextColor(int[] c) {
		return TextColor.color(c[0], c[1], c[2]);
	}

}
