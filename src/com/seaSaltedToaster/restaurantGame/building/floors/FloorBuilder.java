package com.seaSaltedToaster.restaurantGame.building.floors;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.FloorComponent;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class FloorBuilder {
	
	//Other
	private Vector3f reset = new Vector3f(-Integer.MAX_VALUE/2);
	private Entity preview;
	private int maxPoints = 5;
	
	//Placement settings
	private FloorMeshBuilder meshBuilder;
	private FloorComponent floorComp;
	private List<Vector3f> points;
	private List<Entity> previews;
	
	public FloorBuilder() {
		this.meshBuilder = new FloorMeshBuilder();
		this.points = new ArrayList<Vector3f>();
		this.previews = new ArrayList<Entity>();
		this.createPreviews(reset);
		this.resetEndpoints();
	}
	
	public void click(Vector3f position) {
		//Get the stage we are at
		Vector3f clickPos = getCorner(position);
		for(Vector3f point : points) {
			if(point == null)
				continue;
			if(point.equals(clickPos))
				return;
		}
		
		//If this is the first point
		for(int i = 0; i < maxPoints; i++) {
			if(points.get(i) == null) {
				points.set(i, clickPos);
				previews.get(i).setPosition(clickPos);
				break;
			}
		}
		place();
	}
	
	public void update(Vector3f position) {
		Vector3f corner = getCorner(position);
		
		int index = 0;
		for(Vector3f point : points) {
			if(point == null) {
				points.add(corner);
				previews.get(index).setPosition(corner);
				
				Restaurant rest = MainApp.restaurant;
				int layer = BuildingManager.curLayer;
				rest.layers.get(layer).getBuildings().remove(preview);
				this.preview = this.getMeshBuilder().buildFloor(points, floorComp, false);
				rest.layers.get(layer).getBuildings().add(preview);
				
				points.set(index, null);
				
			}
			index++;
		}
		
		if(BuildingManager.isBuilding) {
			
		}
		else {
			this.resetEndpoints();
		}
		fill();
	}
	
	public void place() {		
		Entity placement = this.meshBuilder.buildFloor(points, floorComp, true);
		if(placement != null)
			this.resetEndpoints();
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
	
	public void setBuilding(Building type) {
		this.floorComp = (FloorComponent) type.getComponent("Floor");
	}
	
	private void createPreviews(Vector3f pos) {
		Building bld = BuildingList.getBuilding("Ball");
		
		for(int i = 0; i < maxPoints; i++) {
			Entity entity = bld.getEntity().copyEntity();
			entity.setPosition(pos);
			
			this.previews.add(entity);
			MainApp.restaurant.engine.addEntity(entity);
		}
	}

	private void resetEndpoints() {
		this.points = new ArrayList<Vector3f>();
		this.points.clear();
		fill();
	}

	private void fill() {
		for(int i = 0; i < maxPoints; i++) {
			if(points.size()-1 < i) {
				this.points.add(null);
				this.previews.get(i).setPosition(reset);
			}
		}		
	}

	public FloorMeshBuilder getMeshBuilder() {
		return meshBuilder;
	}

	public void setMeshBuilder(FloorMeshBuilder meshBuilder) {
		this.meshBuilder = meshBuilder;
	}

}
