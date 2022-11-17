package com.seaSaltedToaster.restaurantGame.building.walls;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WallBuilder {
	
	//Other
	private Vector3f reset = new Vector3f(-Integer.MAX_VALUE/2);
	private WallMeshBuilder builder;
	
	//Placement settings
	private Vector3f startPoint, endPoint;
	private Entity one, two;

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
			return;
		}
	}
	
	
	private void placeWall() {
		this.builder.createMesh(startPoint, endPoint);
		this.resetEndpoints();
	}
	
	public void update(Vector3f position) {
		Vector3f corner = getCorner(position);
		boolean placedOne = (startPoint != null);
		
		if(placedOne) {
			this.two.setPosition(corner);
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
	
	private void createPreviews(Vector3f pos) {
		Building bld = BuildingList.getBuilding("Pillar");
		
		this.one = bld.getEntity().copyEntity();
		this.one.setPosition(pos);
		MainApp.restaurant.engine.addEntity(one);
		
		this.two = bld.getEntity().copyEntity();
		this.two.setPosition(pos);
		MainApp.restaurant.engine.addEntity(two);
	}

	private Vector3f getCorner(Vector3f position) {
		float offset = 2f;
		return new Vector3f(snap(position.x, offset), 
				0, snap(position.z, offset));
	}
	
	private float snap(float value, float gridSize) {
		float snap = gridSize / 1.0f;
		return Math.round(value * snap) / snap;
	}

	private void resetEndpoints() {
		this.startPoint = null;
		this.endPoint = null;
		this.one.setPosition(reset);
		this.two.setPosition(reset);
	}

	public WallMeshBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(WallMeshBuilder builder) {
		this.builder = builder;
	}

}
