package com.seaSaltedToaster.restaurantGame.building.walls;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class TrimmedWallGenerator extends WallGenerator {

	@Override
	public Entity createWall(Vector3f startPoint, Vector3f endPoint, WallComponent wallType, boolean addToBatch) {
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> colors = new ArrayList<Vector3f>();
		List<Integer> triangles = new ArrayList<Integer>();
		
		Vector3f direction = startPoint.copy().subtract(endPoint.copy());
		direction.normalize();
		
		float projAngle = (float) Math.toDegrees(Vector3f.angle(direction, new Vector3f(0,0,-1)));
		if(projAngle >= 180)
			projAngle -= 180;
		projAngle = Math.abs(projAngle);
		
		this.addWall(vertices, triangles, colors, projAngle, 0.125f, 1.0f, new Vector3f(2,0,0), startPoint, endPoint);
		this.addWall(vertices, triangles, colors, projAngle, 0.25f, 0.25f, new Vector3f(3,0,0), startPoint, endPoint);
		
		int[] indices = MathUtils.getTriangles(triangles);
		float[] vertexFloats = MathUtils.getVectorList(vertices);
		float[] colorsList = MathUtils.getVectorList(colors);
		
		Engine engine = MainApp.restaurant.engine;
		Vao vao = engine.getLoader().loadToVAO(vertexFloats, colorsList, vertexFloats, indices);
				
		Entity wall = new Entity();
		wall.getTransform().getRotation().setY(-projAngle);
		wall.setPosition(startPoint);
		wall.addComponent(new ModelComponent(vao));
		
		WallComponent wallComp = (WallComponent) wallType.copyInstance();
		wallComp.setStart(startPoint);
		wallComp.setEnd(endPoint);
		wallComp.setProjAngle(projAngle);
		wall.addComponent(wallComp);
		
		if(addToBatch) {
			BuildLayer layer = MainApp.restaurant.layers.get(BuildingManager.curLayer);
			layer.addBuilding(wall, BuildingList.getBuilding(wallType.getWallType()), -127);	
		}
		
		return wall;
	}
	
	private void addWall(List<Vector3f> vertices, List<Integer> triangles, List<Vector3f> colors, float projAngle, float width, float height, Vector3f color, Vector3f startPoint, Vector3f endPoint) {
		int start = vertices.size();
		Vector3f direction = startPoint.copy().subtract(endPoint.copy());
		direction.normalize();
		
		Vector3f v1 = new Vector3f(0.0f, 0.0f, 0.0f);
		vertices.add(v1);
		Vector3f v3 = new Vector3f(endPoint.x - startPoint.x, 0.0f, endPoint.z - startPoint.z);
		v3 = MathUtils.rotatePointAtCenter(v3, -projAngle);
		vertices.add(v3);
				
		addSideWall(width, height, direction, v1.copy(), vertices, triangles);
		addSideWall(width, height, direction, v3.copy(), vertices, triangles);
		
		//Wall tri 1 and 2
		triangles.add(start + 2);
		triangles.add(start + 3);
		triangles.add(start + 6);
		
		triangles.add(start + 3);
		triangles.add(start + 7);
		triangles.add(start + 6);
		
		//Wall tri 3 and 4
		triangles.add(start + 4);
		triangles.add(start + 5);
		triangles.add(start + 8);
		
		triangles.add(start + 4);
		triangles.add(start + 11);
		triangles.add(start + 8);
		
		triangles.add(start + 5);
		triangles.add(start + 9);
		triangles.add(start + 8);
		
		//Roof tri 1 and 2
		triangles.add(start + 3);
		triangles.add(start + 5);
		triangles.add(start + 7);
		
		triangles.add(start + 5);
		triangles.add(start + 9);
		triangles.add(start + 7);
		
		for(int i = start; i < vertices.size(); i++) {
			colors.add(color);
		}
	}
	
	@Override
	public String getType() {
		return "Standard";
	}


}
