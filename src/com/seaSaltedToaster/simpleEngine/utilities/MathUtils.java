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
	 
	 public static Vector3f rotatePoint(Vector3f pt, Vector3f center, double angleDeg) {
	     float angleRad = (float) ((angleDeg/180)*Math.PI);
	     float cosAngle = (float) Math.cos(angleRad );
	     float sinAngle = (float) Math.sin(angleRad );
	     float dx = (pt.x-center.x);
	     float dy = (pt.z-center.z);

	     pt.x = center.x + (dx*cosAngle-dy*sinAngle);
	     pt.z = center.z + (dx*sinAngle+dy*cosAngle);
	     return pt;
	 }

	
}
