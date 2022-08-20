package com.seaSaltedToaster.restaurantGame.building.categories;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.Building;

public class BuildingCategory {
	
	//Parent
	private BuildingCategory parent;
	private boolean isTop;
	
	//Info
	private String name;
	private int icon;
	
	//Children
	private List<BuildingCategory> childCategories;
	private List<Building> childBuildings;
	
	public BuildingCategory(String name) {
		this.name = name;
		this.childCategories = new ArrayList<BuildingCategory>();
		this.childBuildings = new ArrayList<Building>();
	}
	
	public boolean isIndexBuilding(int index) {
		return (index > childCategories.size()-1);
	}
	
	public void addChild(BuildingCategory child) {
		child.setParent(this);
		child.setTop(false);
		this.childCategories.add(child);
	}
	
	public void addChild(Building child) {
		child.setCategory(this);
		this.childBuildings.add(child);
	}

	public BuildingCategory getParent() {
		return parent;
	}

	public void setParent(BuildingCategory parent) {
		this.parent = parent;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public List<BuildingCategory> getChildCategories() {
		return childCategories;
	}

	public List<Building> getChildBuildings() {
		return childBuildings;
	}

}
