package com.seaSaltedToaster.restaurantGame.building.layers;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.WalkableType;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.PlaceAnimation;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildLayer {
	
	//Buildings list
	private List<Entity> buildings;
	private List<Entity> buildingsToAdd;
	
	//Data
	private boolean isOn;
	private int layerId;
	
	//Animation
	private SmoothFloat scaleAnim;
	private boolean isClosing = true;
	private float animFactor = 1f;
	
	//AI
	private BuildingManager manager;
	
	//OLD AI
	private float[][] walkableMap;
	private int worldScale;
	
	//Height
	public static float HEIGHT_OFFSET = 1f;
	
	public BuildLayer(BuildingManager manager, int layerId) {
		this.manager = manager;
		this.layerId = layerId;
		
		this.buildings = new ArrayList<Entity>();
		this.buildingsToAdd = new ArrayList<Entity>();
		
		this.isOn = false;
		this.scaleAnim = new SmoothFloat(0.0f);
		this.scaleAnim.setAmountPer(0.125f);
		
		this.worldScale = (int) (Ground.worldSize * 2);
		this.walkableMap = new float[worldScale][worldScale];
		for(int x = 0; x < worldScale; x++) {
			for(int y = 0; y < worldScale; y++) {
				walkableMap[x][y] = WalkableType.UNWALKABLE.ordinal();
			}
		}
	}
	
	public void updateLayer() {
		buildings.addAll(buildingsToAdd);
		buildingsToAdd.clear();
		
		scaleAnim.update(Window.DeltaTime);
		float scale = scaleAnim.getValue();
		if(isClosing)
			scale = animFactor - scale;
		for(Entity entity : buildings) {
			if(entity == null) continue;
			entity.getTransform().setScale(scale);
		}
		if(scale <= 0.05f && isClosing) 
			isOn = false;
		else if(scale >= 0.0f && !isClosing)
			isOn = true;
	}
	
	public void show() {
		if(isOn) return;
		this.scaleAnim.setValue(0.0f);
		this.scaleAnim.setTarget(animFactor);
		this.isClosing = false;
	}
	
	public void hide() {
		if(!isOn) return;
		this.scaleAnim.setValue(0.0f);
		this.scaleAnim.setTarget(animFactor);
		this.isClosing = true;
	}
	
	public boolean isEmpty() {
		return (buildings.size() <= 0);
	}
	
	public boolean isBuildingAt(Vector3f target, BuildingType type) {
		for(Entity entity : buildings) {
			if(entity == null) continue;
			Vector3f entityPos = entity.getTransform().getPosition();
			BuildingId id = (BuildingId) entity.getComponent("BuildingId");
			if(entityPos.equals(target, 0) && id.getType().type == type) {
				return true;
			}
		}
		return false;
	}

	public void addBuilding(Entity preview, Building object, int buildingIndex) {
		//Ids
		int id = buildingIndex;
		if(id == -127)
			id = IdMaker.getNew();
		IdMaker.addId(id);
		
		//Add building comps
		preview.addComponent(new BuildingId(id, object, this));
		preview.addComponent(new PlaceAnimation());
		this.buildingsToAdd.add(preview);
		for(Component comp : object.getBuildingComponents()) {
			if(comp.getComponentType() == "Pathfinder") 
			{
				PathfinderComponent cmp = (PathfinderComponent) comp.copyInstance();
				cmp.wrld = manager.getPathWorld();
				preview.addComponent(cmp);
				MainApp.restaurant.cmp = cmp;
			}
			else
			{
				preview.addComponent(comp.copyInstance());
			}
		}
		
		notifyListeners(preview, object);
		manager.getPathWorld().addBuilding(object, preview);
	}
	
	private void notifyListeners(Entity preview, Building object) {
		for(PlacementListener listener : BuildingManager.listeners) {
			listener.notifyPlacement(preview, object);
		}
	}

	public void remove(Entity entity) {
		this.buildings.remove(entity);
		this.manager.getPathWorld().removeBuilding(entity, this);
	}
	
	public List<Entity> getTables() {
		List<Entity> tables = new ArrayList<Entity>();
		for(Entity entity : buildings) {
			if(entity == null) continue;
			if(entity.hasComponent("Table")) {
				tables.add(entity);
			}
		}
		return tables;
	}

	public BuildingManager getManager() {
		return manager;
	}

	public float[][] getWalkableMap() {
		return walkableMap;
	}

	public void setWalkableMap(float[][] walkableMap) {
		this.walkableMap = walkableMap;
	}

	public int getLayerId() {
		return layerId;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public List<Entity> getBuildings() {
		return buildings;
	}

}
