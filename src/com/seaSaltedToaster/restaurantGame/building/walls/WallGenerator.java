package com.seaSaltedToaster.restaurantGame.building.walls;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public abstract class WallGenerator {
		
	public abstract Entity createWall(Vector3f startPoint, Vector3f endPoint, WallComponent wallType, boolean addToBatch);
	
	public abstract String getType();
	
	protected void addSideWall(float width, float height, Vector3f direction, Vector3f point, List<Vector3f> vertices, List<Integer> triangles) {
		float projAngle = 90.0f;
		
		Vector3f v1 = MathUtils.projectVertex(point, width/2, projAngle);
		vertices.add(v1);
		Vector3f v2 = new Vector3f(v1.x, height, v1.z);
		vertices.add(v2);
		
		Vector3f v3 =  MathUtils.projectVertex(point, width/2, -projAngle);
		vertices.add(v3);
		Vector3f v4 = new Vector3f(v3.x, height, v3.z);
		vertices.add(v4);
		
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v2));
		triangles.add(vertices.indexOf(v4));
		
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v4));
		triangles.add(vertices.indexOf(v3));
	}

}
