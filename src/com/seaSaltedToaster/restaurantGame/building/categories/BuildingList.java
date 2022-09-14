package com.seaSaltedToaster.restaurantGame.building.categories;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;

public class BuildingList {

	//All Registered Models
	private static BuildingCategory rootCategory;
	private static int buildingIndex = 0;

	public static void create() {
		BuildingList.rootCategory = new BuildingCategory("General");
	}
		
	//Register new model
	public static void register(Building building) {
		System.out.println("Registered new model with the id : " + buildingIndex);
		rootCategory.addChild(building);
		buildingIndex++;
	}
	
	public static void register(Building bld, String categoryName) {
		System.out.println("Registered new model with the id : " + buildingIndex);
		BuildingCategory category = getCategory(categoryName);
		if(category != null)
			category.addChild(bld);
		buildingIndex++;
	}
	
	public static BuildingCategory getCategory(String name) {
		for(BuildingCategory category : rootCategory.getChildCategories()) {
			if(category.getName().equals(name)) {
				return category;
			}
		}
		return rootCategory;
	}
	
	public static Building getBuilding(String name) {
		return getBuilding(name, rootCategory);
	}
	
	public static Building getBuilding(String name, BuildingCategory category) {
		for(Building building : category.getChildBuildings()) {
			if(building.name.equals(name))
				return building;
		}
		for(BuildingCategory curCat : category.getChildCategories()) {
			Building build = getBuilding(name, curCat);
			if(build != null)
				return build;
		}
		return null;
	}
		
	//Clear Registered Models
	public static void clear() {
		System.out.println("Cleared all registered models");
		for(Building building : rootCategory.getChildBuildings()) {
			//TODO delete data
			ModelComponent comp = (ModelComponent) building.getEntity().getComponent("Model");
			comp.getMesh().delete();
		}
	}
		
	public static BuildingCategory getRoot() {
		return rootCategory;
	}
	
}
