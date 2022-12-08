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

public class StandardWallGenerator extends WallGenerator {

	@Override
	public Entity createWall(Vector3f startPoint, Vector3f endPoint, WallComponent wallType, boolean addToBatch) {
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		
		Vector3f direction = startPoint.copy().subtract(endPoint.copy());
		direction.normalize();
		
		float projAngle = (float) Math.toDegrees(Vector3f.angle(direction, new Vector3f(0,0,-1)));
		if(projAngle >= 180)
			projAngle -= 180;
		projAngle = Math.abs(projAngle);
		
		Vector3f v1 = new Vector3f(0.0f);
		vertices.add(v1);
		Vector3f v3 = new Vector3f(endPoint.x - startPoint.x, 0, endPoint.z - startPoint.z);
		v3 = MathUtils.rotatePointAtCenter(v3, -projAngle);
		vertices.add(v3);
		
		List<Integer> triangles = new ArrayList<Integer>();
		
		float wallWidth = 0.125f;
		addSideWall(wallWidth, direction, v1.copy(), vertices, triangles);
		addSideWall(wallWidth, direction, v3.copy(), vertices, triangles);
		
		//Wall tri 1 and 2
		triangles.add(2);
		triangles.add(3);
		triangles.add(6);
		
		triangles.add(3);
		triangles.add(7);
		triangles.add(6);
		
		//Wall tri 3 and 4
		triangles.add(4);
		triangles.add(5);
		triangles.add(8);
		
		triangles.add(4);
		triangles.add(11);
		triangles.add(8);
		
		triangles.add(5);
		triangles.add(9);
		triangles.add(8);
		
		//Roof tri 1 and 2
		triangles.add(3);
		triangles.add(5);
		triangles.add(7);
		
		triangles.add(5);
		triangles.add(9);
		triangles.add(7);
		
		int[] indices = MathUtils.getTriangles(triangles);
		float[] vertexFloats = MathUtils.getVectorList(vertices);
		
		float[] colors = new float[128];
		for(int i = 0; i < 64; i+=3) {
			colors[i] = 2.0f;
			colors[i+1] = 0.0f;
			colors[i+2] = 0.15f;
		}
		
		Engine engine = MainApp.restaurant.engine;
		Vao vao = engine.getLoader().loadToVAO(vertexFloats, colors, vertexFloats, indices);
				
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
			layer.addBuilding(wall, BuildingList.getBuilding(wallType.getWallType()), layer.getBuildings().size()+1);	
		}
		
		return wall;
	}
	
	private void addSideWall(float width, Vector3f direction, Vector3f point, List<Vector3f> vertices, List<Integer> triangles) {
		float projAngle = 90.0f;
		
		Vector3f v1 = MathUtils.projectVertex(point, width/2, projAngle);
		vertices.add(v1);
		Vector3f v2 = new Vector3f(v1.x, 1, v1.z);
		vertices.add(v2);
		
		Vector3f v3 =  MathUtils.projectVertex(point, width/2, -projAngle);
		vertices.add(v3);
		Vector3f v4 = new Vector3f(v3.x, 1, v3.z);
		vertices.add(v4);
		
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v2));
		triangles.add(vertices.indexOf(v4));
		
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v4));
		triangles.add(vertices.indexOf(v3));
	}

	@Override
	public String getType() {
		return "Standard";
	}

}
