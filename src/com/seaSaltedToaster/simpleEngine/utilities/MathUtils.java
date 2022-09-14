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
	 
	 public static Vector3f rotatePointAtCenter(Vector3f pt, double angleDeg) {
		 return rotatePoint(pt, new Vector3f(0.0f), angleDeg);
	 }
	 
	 public static Vector3f rotatePoint(Vector3f point, Vector3f center, double angle) {
		 double x1 = point.x - center.x;
		 double z1 = point.z - center.z;

		 double x2 = x1 * Math.cos(angle) - z1 * Math.sin(angle);
		 double z2 = x1 * Math.sin(angle) + z1 * Math.cos(angle);

		 float x = (float) (x2 + center.x);
		 float z = (float) (z2 + center.z);
	     return new Vector3f(x, point.y, z);
	 }
	 
	 public static Vector3f rotateBy90(Vector3f center, int count) {
		 Vector3f result = new Vector3f(center.copy());
		 for(int i = 0; i < count; i++) {
			 Vector3f newResult = new Vector3f();
			 newResult.x = result.z;
			 newResult.z = -result.x;
			 result = newResult;
		 }
		 return result;
	 }

	
}
