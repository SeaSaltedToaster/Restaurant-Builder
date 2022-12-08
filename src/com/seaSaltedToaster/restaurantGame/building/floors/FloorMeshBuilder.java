package com.seaSaltedToaster.restaurantGame.building.floors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.PlaceAnimation;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.FloorComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class FloorMeshBuilder {
	
	private FloorGenerator generator;
	
	public Entity buildFloor(List<Vector3f> points, FloorComponent comp, boolean add) {
		this.setGenerator(comp.getGenerator());
		
		//Remove nulls
		int count = 0;
		for(int i = 0; i < points.size(); i++) {
			if(points.get(i) == null) {
				points.remove(i);
			}
			else {
				count++;
			}
		}
		points.removeAll(Collections.singleton(null));
		
		Entity result = null;
		
		//Build based on count
		switch(count) {
			case 2 :
				List<Vector3f> points2 = new ArrayList<Vector3f>();
				Vector3f one = points.get(0);
				points2.add(one);
				Vector3f two = points.get(1);
				points2.add(two);
				Vector3f three = new Vector3f(one.x, 0, two.z);
				points2.add(three);
				Vector3f four = new Vector3f(two.x, 0, one.z);
				points2.add(four);
				result = createSquareFloor(points2, comp);
				break;
			case 3 :
				result = createTriangle(points, comp);
				break;
			default :
				break;
		}
		
		if(result == null) {
			return null;
		}
		
		BuildLayer layer = MainApp.restaurant.layers.get(BuildingManager.curLayer);
		
		FloorComponent newComp = new FloorComponent(comp.getType(), comp.getGenerator(), points);
		result.addComponent(newComp);
		
		if(add)
			layer.addBuilding(result, BuildingList.getBuilding(comp.getType()), layer.getBuildings().size()+1);
			
		return result;
	}
	
	public void setGenerator(String generatorType) {
		switch(generatorType) {
		case "Flat" :
			this.generator = new FlatFloorGenerator();
			break;
		case "Tiled" :
			this.generator = new TiledFloorGenerator();
		default :
			this.generator = new TiledFloorGenerator();
			break;
		}
	}

	private Entity createTriangle(List<Vector3f> points, FloorComponent comp) {
		boolean isRight = MathUtils.isRight(points.get(0).copy(), points.get(1).copy(), points.get(2).copy());
		boolean isSpecial45 = MathUtils.isSpecial45(points.get(0).copy(), points.get(1).copy(), points.get(2).copy());
		if(!isSpecial45 || !isRight) {
			return null;
		}
		
		Vector3f main = null;
		Vector3f sub = null;
		for(Vector3f p : points) {
			for(Vector3f m : points) {
				if(diagonalTo(m, p)) {
					sub = p;
					main = m;
				}
			}
		}
		
		int xCount = (int) Math.abs(sub.x - main.x);
		int zCount = (int) Math.abs(sub.z - main.z);
		
		int xStart = (int) (main.x + 0.5f);
		int zStart = (int) (main.z + 0.5f);
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> colors = new ArrayList<Vector3f>();
		List<Integer> triangles = new ArrayList<Integer>();
		
		for(int x = xStart; x < xStart + xCount; x++) {
			for(int z = zStart; z < zStart + zCount; z++) {				
				if(x == z) {
					this.generator.addCornerTile();
				}
				else if(x < (xCount / 2)) {
					this.generator.addTile(vertices, triangles, colors, x - xStart, z - zStart);
				}
				else {
					continue;
				}
			}
		}
		
		float[] positions = MathUtils.getVectorList(vertices);
		float[] colorsList = MathUtils.getVectorList(colors);
		float[] normals = MathUtils.getVectorList(vertices);
		int[] indices = MathUtils.getTriangles(triangles);
		
		Vao vao = MainApp.restaurant.engine.getLoader().loadToVAO(positions, colorsList, normals, indices);
		
		BuildLayer layer = MainApp.restaurant.layers.get(BuildingManager.curLayer);
		Entity ent = new Entity();
		ent.setPosition(new Vector3f(main.x, layer.getLayerId() * BuildLayer.HEIGHT_OFFSET, main.z));
		ent.getTransform().getRotation().setY(180);
		ent.addComponent(new ModelComponent(vao));
		
		return ent;	
	}

	private Entity createSquareFloor(List<Vector3f> points, FloorComponent comp) {
		//Check for square
		boolean isSquare = MathUtils.isSquare(points.get(0), points.get(1), points.get(2), points.get(3));
		boolean isRect = MathUtils.IsRectangleAnyOrder(points.get(0), points.get(1), points.get(2), points.get(3));
		boolean isRect2 = MathUtils.IsRectangleAnyOrder(points.get(1), points.get(2), points.get(3), points.get(0));
		boolean isRect3 = MathUtils.IsRectangleAnyOrder(points.get(2), points.get(3), points.get(0), points.get(1));
		boolean isRect4 = MathUtils.IsRectangleAnyOrder(points.get(3), points.get(0), points.get(1), points.get(2));
		if(!isSquare && !isRect && !isRect2 && !isRect3 && !isRect4) {
			return null;
		}
		
		//Get points
		Vector3f main = null;
		Vector3f sub = null;
		for(Vector3f p : points) {
			for(Vector3f m : points) {
				if(diagonalTo(m, p) && p.x < m.x && p.z < m.z) {
					sub = p;
					main = m;
					break;
				}
			}
		}
		
		if(main == null || sub == null) {
			return null;
		}
		
		//Create floors
		int xCount = (int) Math.abs(sub.x - main.x);
		int zCount = (int) Math.abs(sub.z - main.z);
		
		int xStart = (int) (main.x + 0.5f);
		int zStart = (int) (main.z + 0.5f);
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector3f> colors = new ArrayList<Vector3f>();
		List<Integer> triangles = new ArrayList<Integer>();
		
		//Loop
		for(int x = xStart; x < xStart + xCount; x++) {
			for(int z = zStart; z < zStart + zCount; z++) {
				this.generator.addTile(vertices, triangles, colors, x - xStart, z - zStart);
			}
		}
		
		float[] positions = MathUtils.getVectorList(vertices);
		float[] colorsList = MathUtils.getVectorList(colors);
		float[] normals = MathUtils.getVectorList(vertices);
		int[] indices = MathUtils.getTriangles(triangles);
		
		Vao vao = MainApp.restaurant.engine.getLoader().loadToVAO(positions, colorsList, normals, indices);
		
		BuildLayer layer = MainApp.restaurant.layers.get(BuildingManager.curLayer);
		Entity ent = new Entity();
		ent.setPosition(new Vector3f(main.x, layer.getLayerId() * BuildLayer.HEIGHT_OFFSET, main.z));
		ent.getTransform().getRotation().setY(180);
		ent.addComponent(new ModelComponent(vao));
		
		return ent;
	}
	
	private boolean diagonalTo(Vector3f main, Vector3f sub) {
		return (main.x != sub.x) && (main.z != sub.z);
	}

}
