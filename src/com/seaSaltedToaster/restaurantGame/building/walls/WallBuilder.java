package com.seaSaltedToaster.restaurantGame.building.walls;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WallBuilder {
	
	//Other
	private Vector3f reset = new Vector3f(-Integer.MAX_VALUE/2);
	private WallMeshBuilder builder;
	
	//Placement settings
	private WallComponent wallType;
	private Vector3f startPoint, endPoint;
	private Entity one, two, preview;

	public WallBuilder() {
		this.builder = new WallMeshBuilder();
		createPreviews(reset);
		resetEndpoints();
	}
	
	public void click(Vector3f position) {
		//Get the stage we are at
		Vector3f clickPos = getCorner(position);
		
		//If this is the first point
		if(startPoint == null) {
			this.startPoint = clickPos;
			this.one.setPosition(clickPos);
			return;
		}
		//if this is the second point
		else if(endPoint == null) {
			this.endPoint = clickPos;
			this.two.setPosition(clickPos);
			placeWall();
			this.startPoint = clickPos;
			return;
		}
	}
	
	
	private void placeWall() {
		this.builder.createMesh(startPoint, endPoint, wallType);
		this.resetEndpoints();
	}
	
	public void update(Vector3f position) {
		Vector3f corner = getCorner(position);
		boolean placedOne = (startPoint != null);
		
		if(placedOne) {
			if(two.getPosition().equals(corner)) {
				
			}
			else {
				this.two.setPosition(corner);
				
				Restaurant rest = MainApp.restaurant;
				int layer = BuildingManager.curLayer;
				rest.layers.get(layer).getBuildings().remove(preview);
				this.preview = builder.generator.createWall(startPoint, corner, wallType, false);
				rest.layers.get(layer).getBuildings().add(preview);
			}
		}
		else {
			this.one.setPosition(corner);
		}
		
		if(BuildingManager.isBuilding) {
			
		}
		else {
			this.resetEndpoints();
		}
	}
	
	public void set(Building building) {
		this.wallType = (WallComponent) building.getComponent("Wall");
		this.builder.setGenerator(wallType.getGenerator());
	}
	
	private void createPreviews(Vector3f pos) {
		Building bld = BuildingList.getBuilding("Ball");
		
		this.one = bld.getEntity().copyEntity();
		this.one.setPosition(pos);
		MainApp.restaurant.engine.addEntity(one);
		
		this.two = bld.getEntity().copyEntity();
		this.two.setPosition(pos);
		MainApp.restaurant.engine.addEntity(two);
	}

	private Vector3f getCorner(Vector3f position) {
		float offset = 1f;
		return new Vector3f(snap(position.x - 0.5f, offset) + 0.5f, 
				0, snap(position.z - 0.5f, offset) + 0.5f);
	}
	
	private float snap(float value, float gridSize) {
		float snap = gridSize / 1.0f;
		return Math.round(value * snap) / snap;
	}

	public void resetEndpoints() {
		this.startPoint = null;
		this.endPoint = null;
		this.one.setPosition(reset);
		this.two.setPosition(reset);
		
		Restaurant rest = MainApp.restaurant;
		int layer = BuildingManager.curLayer;
		rest.layers.get(layer).getBuildings().remove(preview);
	}

	public WallMeshBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(WallMeshBuilder builder) {
		this.builder = builder;
	}

}
