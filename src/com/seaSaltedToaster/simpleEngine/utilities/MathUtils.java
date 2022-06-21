package com.seaSaltedToaster.simpleEngine.utilities;

public class MathUtils {
	
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(value, max));
	}

}
