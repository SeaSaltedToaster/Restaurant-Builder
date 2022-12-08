package com.seaSaltedToaster.simpleEngine.utilities;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class MathUtils {

	public static int[] getTriangles(List<Integer> triangles) {
		int[] indices = new int[triangles.size()];
		for(int i=0;i<triangles.size();i++){
			indices[i] = triangles.get(i);
		}		
		return indices;
	}

	public static float dotVec(Vector3f left, Vector3f right) {
		return left.x * right.x + left.y * right.y + left.z * right.z;
	}

	public static float[] getVectorList(List<Vector3f> vectors) {
		float[] positions = new float[vectors.size()*3];
		int vertexPointer = 0;
		for(Vector3f vertex : vectors){
			positions[vertexPointer++] = vertex.x;
			positions[vertexPointer++] = vertex.y;
			positions[vertexPointer++] = vertex.z;
		}		
		return positions;
	}
	
	public static byte[] extractBytes (String ImageName) {
		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		WritableRaster raster = bufferedImage .getRaster();
		DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

		return ( data.getData() );
	}
	
	public static Vector3f projectVertex(Vector3f original, float dist, float angle) {
		Vector3f proj = new Vector3f(0,0,-dist);
		proj = MathUtils.rotatePointAtCenter(proj, angle);
		return new Vector3f(original.x + proj.x, 0, original.z + proj.z);
	}
	
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
	 
	 public static boolean isRight(Vector3f a, Vector3f b, Vector3f c) {
		 float side1 = a.copy().subtract(b.copy()).lengthSquared();
		 float side2 = b.copy().subtract(c.copy()).lengthSquared();
		 float side3 = c.copy().subtract(a.copy()).lengthSquared();
		 if(Math.abs(side1 + side2 - side3) < 0.2)
		        return true;
		    if(Math.abs(side1 + side3 - side2) < 0.2)
		        return true;
		    if(Math.abs(side3 + side2 - side1) < 0.2)
		        return true;
		    else
		        return false;

	 }
	 
	 public static boolean isSpecial45(Vector3f a, Vector3f b, Vector3f c) {
		 float side1 = a.copy().subtract(b.copy()).length();
		 float side2 = b.copy().subtract(c.copy()).length();
		 float side3 = c.copy().subtract(a.copy()).length();
		 System.out.println(side1 + " : " + side2 + " : " + side3 + " : " + (side1 * Math.sqrt(2)));
		 
		 if(side1 == side2 && side3 - (Math.sqrt(2) * side1) < 0.01f) 
			 return true;
		 if(side1 == side3 && side2 - (Math.sqrt(2) * side1) < 0.01f) 
			 return true;
		 if(side3 == side2 && side1 - (Math.sqrt(2) * side3) < 0.01f) 
			 return true;
		 return false;
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
	 
	 public static boolean onSegment(Vector2f p, Vector2f q, Vector2f r)
	 {
	     if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
	         q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
	     return true;
	   
	     return false;
	 }
	   
	 // To find orientation of ordered triplet (p, q, r).
	 // The function returns following values
	 // 0 --> p, q and r are collinear
	 // 1 --> Clockwise
	 // 2 --> Counterclockwise
	 public static int orientation(Vector2f p, Vector2f q, Vector2f r)
	 {
	     // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
	     // for details of below formula.
	     int val = (int) ((q.y - p.y) * (r.x - q.x) -
	             (q.x - p.x) * (r.y - q.y));
	   
	     if (val == 0) return 0; // collinear
	   
	     return (val > 0)? 1: 2; // clock or counterclock wise
	 }
	   
	 public static double dot(Vector3f v, Vector3f u) {
		 double num = (v.x*u.x + v.z*u.z);
		 double den = (Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.z, 2)) * (Math.sqrt(Math.pow(u.x, 2) + Math.pow(u.z, 2))) );
		 double cos =  num / den;
		 return cos;
	 }
	 
	 // The main function that returns true if line segment 'p1q1'
	 // and 'p2q2' intersect.
	 public static boolean doIntersect(Vector2f p1, Vector2f p2, Vector2f q1, Vector2f q2)
	 {
	     // Find the four orientations needed for general and
	     // special cases
	     int o1 = orientation(p1, q1, p2);
	     int o2 = orientation(p1, q1, q2);
	     int o3 = orientation(p2, q2, p1);
	     int o4 = orientation(p2, q2, q1);
	   
	     // General case
	     if (o1 != o2 && o3 != o4)
	         return true;
	   
	     // Special Cases
	     // p1, q1 and p2 are collinear and p2 lies on segment p1q1
	     if (o1 == 0 && onSegment(p1, p2, q1)) return true;
	   
	     // p1, q1 and q2 are collinear and q2 lies on segment p1q1
	     if (o2 == 0 && onSegment(p1, q2, q1)) return true;
	   
	     // p2, q2 and p1 are collinear and p1 lies on segment p2q2
	     if (o3 == 0 && onSegment(p2, p1, q2)) return true;
	   
	     // p2, q2 and q1 are collinear and q1 lies on segment p2q2
	     if (o4 == 0 && onSegment(p2, q1, q2)) return true;
	   
	     return false; // Doesn't fall in any of the above cases
	 }

	 public static int distSq(Vector3f p, Vector3f q)	{
	     return (int) p.copy().subtract(q.copy()).lengthSquared();
	 }
	
	 public static boolean isSquare(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4)	{
	     int d2 = distSq(p1, p2); // from p1 to p2
	     int d3 = distSq(p1, p3); // from p1 to p3
	     int d4 = distSq(p1, p4); // from p1 to p4
	  
	     if (d2 == 0 || d3 == 0 || d4 == 0)   
	         return false;
	  
	     // If lengths if (p1, p2) and (p1, p3) are same, then
	     // following conditions must met to form a square.
	     // 1) Square of length of (p1, p4) is same as twice
	     // the square of (p1, p2)
	     // 2) Square of length of (p2, p3) is same
	     // as twice the square of (p2, p4)
	  
	     if (d2 == d3 && 2 * d2 == d4
	         && 2 * distSq(p2, p4) == distSq(p2, p3))
	     {
	         return true;
	     }
	  
	     // The below two cases are similar to above case
	     if (d3 == d4 && 2 * d3 == d2
	         && 2 * distSq(p3, p2) == distSq(p3, p4))
	     {
	         return true;
	     }
	     if (d2 == d4 && 2 * d2 == d3
	         && 2 * distSq(p2, p3) == distSq(p2, p4))
	     {
	         return true;
	     }
	  
	     return false;
	 }
	 
	 private static boolean IsOrthogonal(Vector3f a, Vector3f b, Vector3f c)
	 {
	     return ((int) (b.x - a.x) * (int) (b.x - c.x) + (int) (b.y - a.y) * (int) (b.y - c.y)) == 0;
	 }

	 public static boolean IsRectangle(Vector3f a, Vector3f b, Vector3f c, Vector3f d)
	 {
	     return
	         IsOrthogonal(a, b, c) &&
	         IsOrthogonal(b, c, d) &&
	         IsOrthogonal(c, d, a);
	 }

	 public static boolean IsRectangleAnyOrder(Vector3f a, Vector3f b, Vector3f c, Vector3f d)
	 {
	     return IsRectangle(a, b, c, d) ||
	            IsRectangle(b, c, a, d) ||
	            IsRectangle(c, a, b, d);
	 }
}
