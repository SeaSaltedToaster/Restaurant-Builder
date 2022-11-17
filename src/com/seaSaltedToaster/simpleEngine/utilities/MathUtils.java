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
	 
	 public static Vector3f snapPointToLine(Vector3f point, Vector3f line1, Vector3f line2) {
		 float len = line1.copy().subtract(line2.copy()).length();
		 float u = (float) (((point.x - line1.x) * (line2.x - line1.x) + (point.z - line1.z) * (line2.z - line1.z)) / (
				    Math.pow(len, 2)));
				if(u > 1)
				  u = 1;
				else if(u < 0)
				  u = 0;
		float x = line1.x + u * (line2.x - line1.x);
		float z = line1.z + u * (line2.z - line1.z);
		return new Vector3f(x, point.y, z);
	 }
	 
	 public static Vector3f RotatePoint(Vector3f pointToRotate, Vector3f centerPoint, double angleInDegrees)
	 {
	     double angleInRadians = angleInDegrees * (Math.PI / 180);
	     double cosTheta = Math.cos(angleInRadians);
	     double sinTheta = Math.sin(angleInRadians);
         float x =
	             (int)
	             (cosTheta * (pointToRotate.x - centerPoint.x) -
	             sinTheta * (pointToRotate.z - centerPoint.z) + centerPoint.x);
	     float y =
	             (int)
	             (sinTheta * (pointToRotate.x - centerPoint.x) +
	             cosTheta * (pointToRotate.z - centerPoint.z) + centerPoint.z);
	     return new Vector3f(x,0,y);
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
