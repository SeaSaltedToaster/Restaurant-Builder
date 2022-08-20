package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.PersonType;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.food.Food;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefStation;
import com.seaSaltedToaster.restaurantGame.objects.people.CustomerComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.HostStand;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;

public class BuildingRepository {
	
	public void registerBuildings(Engine engine) {
		//Categories
		loadCategory("Furniture", "/uis/furniture", engine);
		loadCategory("Plants", "/uis/plant", engine);
		loadCategory("Workstations", "/uis/workstations", engine);
		loadCategory("Cafe Set", "/uis/cafe", engine);
		
		/*
		 * CAFE SET
		 */
		Building cafeWall1 = loadBuilding("cafe/cafeWall1", BuildingType.WALL, engine);
		cafeWall1.setWall(true);
		cafeWall1.setIconZoom(0.66f);
		BuildingList.register(cafeWall1, "Cafe Set"); 
		
		Building cafeWall2 = loadBuilding("cafe/cafeWall2", BuildingType.WALL, engine);
		cafeWall2.setWall(true);
		cafeWall2.setIconZoom(0.66f);
		BuildingList.register(cafeWall2, "Cafe Set"); 
		
		Building cafeWall3 = loadBuilding("cafe/cafeWall3", BuildingType.WALL, engine);
		cafeWall3.setWall(true);
		cafeWall3.setIconZoom(0.66f);
		BuildingList.register(cafeWall3, "Cafe Set"); 
		
		Building cafeWindow1 = loadBuilding("cafe/cafeWindow1", BuildingType.WALL, engine);
		cafeWindow1.setWall(true);
		cafeWindow1.setIconZoom(0.66f);
		BuildingList.register(cafeWindow1, "Cafe Set"); 
		
		Building testWall = loadBuilding("cafe/testWall", BuildingType.FLOOR, engine);
		testWall.setFloor(true);
		testWall.setIconZoom(0.66f);
		BuildingList.register(testWall, "Cafe Set"); 

		Building calif = loadBuilding("cafe/calif", BuildingType.FLOOR, engine);
		calif.setFloor(true);
		calif.setIconZoom(0.15f);
		BuildingList.register(calif, "Cafe Set"); 
		/*
		 * WALLS
		 */
		Building simpleWall = loadBuilding("simpleWall", BuildingType.WALL, engine);
		simpleWall.setWall(true);
		simpleWall.setIconZoom(0.66f);
		BuildingList.register(simpleWall, "General");
		
		Building simpleWindow = loadBuilding("simpleWindow", BuildingType.WALL, engine);
		simpleWindow.setWall(true);
		simpleWindow.setIconZoom(0.66f);
		BuildingList.register(simpleWindow, "General"); 
		
		Building simpleDoor = loadBuilding("simpleDoor", BuildingType.WALL, engine);
		simpleDoor.setWall(true);
		simpleDoor.setIconZoom(0.66f);
		simpleDoor.getBuildingComponents().add(new DoorComponent());
		BuildingList.register(simpleDoor, "General"); 
		
		Building simpleLowWall = loadBuilding("simpleLowWall", BuildingType.WALL, engine);
		simpleLowWall.setWall(true);
		simpleLowWall.setIconZoom(0.66f);
		BuildingList.register(simpleLowWall, "General"); 
		
		/*
		 * FLOORS / ROOFS
		 */
		Building simpleFloor = loadBuilding("simpleFloor", BuildingType.FLOOR, engine);
		simpleFloor.setFloor(true);
		simpleFloor.setIconZoom(1.0f);
		BuildingList.register(simpleFloor, "General");
		
		Building simpleRoof = loadBuilding("simpleRoof", BuildingType.FLOOR, engine);
		simpleRoof.setFloor(true);
		simpleRoof.setIconZoom(1.0f);
		BuildingList.register(simpleRoof, "General");
		
		Building simpleSidewalk = loadBuilding("simpleSidewalk", BuildingType.FLOOR, engine);
		simpleSidewalk.setFloor(true);
		simpleSidewalk.setIconZoom(1.0f);
		BuildingList.register(simpleSidewalk, "General");
		
		/*
		 * FURNITURE
		 */
		Building simpleBooth = loadBuilding("simpleBooth", BuildingType.FURNITURE, engine);
		simpleBooth.setIsTable(true);
		simpleBooth.setObstructive(true);
		simpleBooth.setIconZoom(1.0f);
		BuildingList.register(simpleBooth, "Furniture");
		TableComponent table = new TableComponent();
		simpleBooth.getBuildingComponents().add(table);
		
		Building simpleStool = loadBuilding("simpleStool", BuildingType.FURNITURE, engine);
		simpleStool.setObstructive(false); //true
		simpleStool.setIconZoom(0.5f);
		BuildingList.register(simpleStool, "Furniture");
		
		/*
		 * WORKSTATIONS
		 */
		Building hostStand = loadBuilding("hostStand", BuildingType.FURNITURE, engine);
		hostStand.setObstructive(true);
		hostStand.setIconZoom(0.4f);
		BuildingList.register(hostStand, "Workstations");
		hostStand.getBuildingComponents().add(new HostStand());
		
		Building kitchenCounter = loadBuilding("kitchenCounter", BuildingType.FURNITURE, engine);
		kitchenCounter.setObstructive(true);
		kitchenCounter.setWall(true);
		kitchenCounter.setIconZoom(0.4f);
		BuildingList.register(kitchenCounter, "Workstations");
		kitchenCounter.getBuildingComponents().add(new ChefStation());
		
		/*
		 * PLANTS
		 */
		Building simplePlant = loadBuilding("simplePlant", BuildingType.FURNITURE, engine);
		simplePlant.setObstructive(true);
		simplePlant.setIconZoom(0.66f);
		BuildingList.register(simplePlant, "Plants");
		
		Building simplePotHedge = loadBuilding("simplePotHedge", BuildingType.FURNITURE, engine);
		simplePotHedge.setWall(true);
		simplePotHedge.setIconZoom(0.5f);
		BuildingList.register(simplePotHedge, "Plants");
		
		/*
		 * TESTING
		 */
		Building person1 = loadBuilding("person1", BuildingType.PERSON, engine);
		person1.setObstructive(false);
		person1.setFloor(true);
		person1.setIconZoom(0.5f);
		person1.getBuildingComponents().add(new PathfinderComponent());
		person1.getBuildingComponents().add(new CustomerComponent());
		person1.getBuildingComponents().add(new ActionComponent(PersonType.CUSTOMER));
		BuildingList.register(person1, "General");
		
		Building waiter1 = loadBuilding("waiter1", BuildingType.PERSON, engine);
		waiter1.setObstructive(false);
		waiter1.setFloor(true);
		waiter1.setIconZoom(0.5f);
		waiter1.getBuildingComponents().add(new ServerComponent());
		waiter1.getBuildingComponents().add(new PathfinderComponent());
		waiter1.getBuildingComponents().add(new ActionComponent(PersonType.WAITER));
		BuildingList.register(waiter1, "General");
		

		Building chef1 = loadBuilding("chef1", BuildingType.PERSON, engine);
		chef1.setObstructive(false);
		chef1.setFloor(true);
		chef1.setIconZoom(0.5f);
		chef1.getBuildingComponents().add(new ChefComponent());
		chef1.getBuildingComponents().add(new PathfinderComponent());
		chef1.getBuildingComponents().add(new ActionComponent(PersonType.CHEF));
		BuildingList.register(chef1, "General");
	}
	
	public void registerFoods(Engine engine) {
		FoodRegistry.create();
		
		Food ribs = loadFood("foods/ribs", engine);
		FoodRegistry.registerFood(ribs, 0);
		
		Food jamToast = loadFood("foods/jamToast", engine);
		FoodRegistry.registerFood(jamToast, 1);
		
		Food chickenLegs = loadFood("foods/chickenLegs", engine);
		FoodRegistry.registerFood(chickenLegs, 2);
		
		Food dish1 = loadFood("foods/dish1", engine);
		FoodRegistry.registerFood(dish1, -1);
		
		Food check = loadFood("foods/check", engine);
		FoodRegistry.registerFood(check, -10);
	}
	
	private Food loadFood(String name, Engine engine) {
		Vao model = engine.getObjLoader().loadObjModel(name);
		Food food = new Food(model);
		return food;
	}
	
	private Building loadBuilding(String name, BuildingType type, Engine engine) {
		Vao model = engine.getObjLoader().loadObjModel("models/" + name);
		Entity entity = new Entity();
		entity.addComponent(new ModelComponent(model));
		Building bld = new Building(entity);
		bld.type = type;
		bld.name = name;
		return bld;
	}
	
	private void loadCategory(String name, String categoryIcon, Engine engine) {
		BuildingCategory category = new BuildingCategory(name);
		int icon = engine.getTextureLoader().loadTexture(categoryIcon);
		category.setIcon(icon);
		BuildingList.getRoot().addChild(category);
	}
}
