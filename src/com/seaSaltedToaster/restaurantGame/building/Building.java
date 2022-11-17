package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Building {
	
	//Objects
	private Entity entity;
	private List<Component> buildingComponents;
	
	//Category
	private BuildingCategory category;
	public BuildingType type;
	
	//Colors (default)
	private Vector3f defPrimary, defSecondary;
	
	//Settings
	private boolean isWall = false, isFloor = false, isObstructive = false, isTable = false, walkThrough = false;
	private float iconZoom = 1f;
	private int price = 0;
	public String name;
	public Vao model;

	public Building(Entity entity, boolean isWall, boolean isFloor, BuildingType type) {
		this.entity = entity;
		this.buildingComponents = new ArrayList<Component>();
		this.isWall = isWall;
		this.isFloor = isFloor;
		this.type = type;
		this.defPrimary = new Vector3f();
		this.defSecondary = new Vector3f();
	}

	public Building(Entity entity) {
		this.entity = entity;
		this.buildingComponents = new ArrayList<Component>();
		this.isWall = false;
		this.isFloor = false;
		this.defPrimary = new Vector3f();
		this.defSecondary = new Vector3f();
	}

	public BuildingType getType() {
		return type;
	}

	public void setType(BuildingType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector3f getDefPrimary() {
		return defPrimary;
	}

	public void setDefPrimary(Vector3f defPrimary) {
		this.defPrimary = defPrimary;
	}

	public Vector3f getDefSecondary() {
		return defSecondary;
	}

	public void setDefSecondary(Vector3f defSecondary) {
		this.defSecondary = defSecondary;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	public boolean isWalkThrough() {
		return walkThrough;
	}

	public void setWalkThrough(boolean walkThrough) {
		this.walkThrough = walkThrough;
	}

}
