package com.seaSaltedToaster.restaurantGame.building;

import java.util.HashMap;
import java.util.Map;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;

public class BuildingList {

	//All Registered Models
	private static Map<Building, Integer> buildings;

	public static void create() {
		BuildingList.buildings = new HashMap<Building, Integer>();
	}
		
	//Register new model
	public static void register(Building building, Integer id) {
		System.out.println("Registered new model with the id : " + id);
		buildings.put(building, id);
	}
		
	//Clear Registered Models
	public static void clear() {
		System.out.println("Cleared all registered models");
		for(Building building : buildings.keySet()) {
			//TODO delete data
			ModelComponent comp = (ModelComponent) building.getEntity().getComponent("Model");
			comp.getMesh().delete();
		}
	}
		
	//Get model with certain ID
	public static Building getBuilding(int id) {
		for(Map.Entry<Building, Integer> entry : buildings.entrySet()) {
			if(entry.getValue() == id) {
			   return entry.getKey();
			}
		}
		return null;
	}

	public static Map<Building, Integer> getBuildings() {
		return buildings;
	}
	
}
