package com.lynxdeer.lynxlib.utils.display;

import com.lynxdeer.lynxlib.utils.display.enums.EaseType;

public class Ease {
	
	public EaseType type;
	public double progress;
	
	public Ease(EaseType easeType) {
		type = easeType;
		progress = 0.0d;
	}
	
	public double get() {
		return switch(type) {
			case LINEAR -> this.progress;
			case IN -> 0.0;
			case OUT -> 0.0;
			case IN_OUT -> progress < 0.5 ? 16 * Math.pow(progress, 5) :
							1 - Math.pow(-2 * progress + 2, 5) / 2;
		};
		// TODO: ACTUALLY RETURN REAL EASING
	}
	
}
