package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.PersonType;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.ObjectLoader;
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
	
	private ObjectLoader loader;
	
	public void registerBuildings(Engine engine) {
		this.loader = new ObjectLoader();
		
		//Categories
		loadCategory("Furniture", "/uis/furniture", engine);
		loadCategory("Garden", "/uis/plant", engine);
		loadCategory("Workstations", "/uis/workstations", engine);
		loadCategory("WorkInProgress", "/uis/cafe", engine);
		
		/*
		 * FINISHED BUILDINGS
		 */
		Building basicWall = this.loader.loadObject("basicWall", engine);
		Building basicWindow = this.loader.loadObject("basicWindow", engine);
		Building basicDoor = this.loader.loadObject("basicDoor", engine);
		Building basicBar = this.loader.loadObject("basicBar", engine);
		basicDoor.getBuildingComponents().add(new DoorComponent());
		
		Building tiledFloor = this.loader.loadObject("tiledFloor", engine);
		Building patio = this.loader.loadObject("patio", engine);
		Building trimmedWall = this.loader.loadObject("trimmedWall", engine);
		Building brickWall = this.loader.loadObject("brickWall", engine);
		
		//Garden
		Building pottedPlant = this.loader.loadObject("pottedPlant", engine);
		Building pottedHedge = this.loader.loadObject("pottedHedge", engine);
		
		//Funiture
		Building basicBooth = this.loader.loadObject("basicBooth", engine);
		basicBooth.setIsTable(true);
		TableComponent booth = new TableComponent(TableType.BOOTH1);
		basicBooth.getBuildingComponents().add(booth);
		
		/*
		 * WORK IN PROGRESS
		 */

		Building hostStand = loadBuilding("hostStand", BuildingType.Object, engine);
		hostStand.name = "Host Stand";
		hostStand.setPrice(35);
		hostStand.setObstructive(true);
		hostStand.setIconZoom(0.4f);
		BuildingList.register(hostStand, "Workstations");
		hostStand.getBuildingComponents().add(new HostStand());
		
		Building kitchenCounter = loadBuilding("kitchenCounter", BuildingType.Object, engine);
		kitchenCounter.name = "Chef Station";
		kitchenCounter.setPrice(50);
		kitchenCounter.setIconZoom(0.4f);
		BuildingList.register(kitchenCounter, "Workstations");
		kitchenCounter.getBuildingComponents().add(new ChefStation());
		
		Building largeHedge = loadBuilding("largeHedge", BuildingType.Object, engine);
		largeHedge.name = "Large Hedge";
		largeHedge.setPrice(20);
		largeHedge.setWall(true);
		largeHedge.setIconZoom(0.5f);
		BuildingList.register(largeHedge, "Garden");
		
		Building tileHedge = loadBuilding("tileHedge", BuildingType.Floor, engine);
		tileHedge.name = "Tile Hedge";
		tileHedge.setPrice(25);
		tileHedge.setIconZoom(0.5f);
		BuildingList.register(tileHedge, "Garden");

		Building person1 = loadBuilding("person1", BuildingType.Person, engine);
		person1.name = "Customer_TEST";
		person1.setObstructive(false);
		person1.setIconZoom(0.5f);
		person1.getBuildingComponents().add(new PathfinderComponent());
		person1.getBuildingComponents().add(new CustomerComponent());
		person1.getBuildingComponents().add(new ActionComponent(PersonType.CUSTOMER));
		BuildingList.register(person1, "WorkInProgress");
		
		Building waiter1 = loadBuilding("waiter1", BuildingType.Person, engine);
		waiter1.name = "Waiter";
		waiter1.setPrice(50);
		waiter1.setObstructive(false);
		waiter1.setIconZoom(0.5f);
		waiter1.getBuildingComponents().add(new ServerComponent());
		waiter1.getBuildingComponents().add(new PathfinderComponent());
		waiter1.getBuildingComponents().add(new ActionComponent(PersonType.WAITER));
		BuildingList.register(waiter1, "WorkInProgress");
		
		Building chef1 = loadBuilding("chef1", BuildingType.Person, engine);
		chef1.name = "Chef";
		chef1.setPrice(50);
		chef1.setObstructive(false);
		chef1.setIconZoom(0.5f);
		chef1.getBuildingComponents().add(new ChefComponent());
		chef1.getBuildingComponents().add(new PathfinderComponent());
		chef1.getBuildingComponents().add(new ActionComponent(PersonType.CHEF));
		BuildingList.register(chef1, "WorkInProgress");
	}
	
	public void registerFoods(Engine engine) {
		FoodRegistry.create();
		
		Food ribs = loadFood("foods/ribs", engine);
		FoodRegistry.registerFood(ribs, 0);
		
		Food jamToast = loadFood("foods/jamToast", engine);
		FoodRegistry.registerFood(jamToast, 1);
		
		Food chickenLegs = loadFood("foods/chickenLegs", engine);
		FoodRegistry.registerFood(chickenLegs, 2);
		
		Food beefTacos = loadFood("foods/beefTacos", engine);
		FoodRegistry.registerFood(beefTacos, 3);
		
		Food simpleStew = loadFood("foods/simpleStew", engine);
		FoodRegistry.registerFood(simpleStew, 4);
		
		Food fish = loadFood("foods/fish", engine);
		FoodRegistry.registerFood(fish, 5);
		
		Food steak = loadFood("foods/steak", engine);
		FoodRegistry.registerFood(steak, 6);
		
		Food burger = loadFood("foods/burger", engine);
		FoodRegistry.registerFood(burger, 7);
		
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
