package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class Building {
	
	//Objects
	private Entity entity;
	private List<Component> buildingComponents;
	
	//Category
	private BuildingCategory category;
	public BuildingType type;
	
	//Settings
	private boolean isWall = false, isFloor = false, isObstructive = false, isTable = false;
	private float iconZoom = 1f;
	public String name;

	public Building(Entity entity, boolean isWall, boolean isFloor, BuildingType type) {
		this.entity = entity;
		this.buildingComponents = new ArrayList<Component>();
		this.isWall = isWall;
		this.isFloor = isFloor;
		this.type = type;
	}

	public Building(Entity entity) {
		this.entity = entity;
		this.buildingComponents = new ArrayList<Component>();
		this.isWall = false;
		this.isFloor = false;
	}

	public Entity getEntity() {
		return entity;
	}

	public List<Component> getBuildingComponents() {
		return buildingComponents;
	}

	public BuildingCategory getCategory() {
		return category;
	}

	public void setCategory(BuildingCategory category) {
		this.category = category;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public boolean isFloor() {
		return isFloor;
	}

	public void setFloor(boolean isFloor) {
		this.isFloor = isFloor;
	}

	public boolean isObstructive() {
		return isObstructive;
	}

	public void setObstructive(boolean isObstructive) {
		this.isObstructive = isObstructive;
	}

	public boolean isTable() {
		return isTable;
	}

	public void setIsTable(boolean isTable) {
		this.isTable = isTable;
	}

	public float getIconZoom() {
		return iconZoom;
	}

	public void setIconZoom(float iconZoom) {
		this.iconZoom = iconZoom;
	}

}
