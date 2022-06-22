package com.seaSaltedToaster.simpleEngine.utilities;

public class MathUtils {
	
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(value, max));
	}

	 public static float cosInterpolate(float a, float b, float blend) {
		 double ft = blend * Math.PI;
		 float f = (float)((1.0D - Math.cos(ft)) * 0.5D);
		 return a * (1.0F - f) + b * f;
	 }
	
}
