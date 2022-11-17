package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.PersonType;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
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
		loadCategory("WorkInProgress", "/uis/employees", engine);
		loadCategory("Event", "/uis/spooky", engine);
		
		/*
		 * FINISHED BUILDINGS
		 */
		Building basicWall = this.loader.loadObject("basicWall", engine);
//		Building basicWindow = this.loader.loadObject("basicWindow", engine);
//		Building basicDoor = this.loader.loadObject("basicDoor", engine);
//		Building basicBar = this.loader.loadObject("basicBar", engine);
//		basicDoor.getBuildingComponents().add(new DoorComponent());
		
		Building tiledFloor = this.loader.loadObject("tiledFloor", engine);
		Building cornerFloor = this.loader.loadObject("cornerFloor", engine);
		Building patio = this.loader.loadObject("patio", engine);
//		Building trimmedWall = this.loader.loadObject("trimmedWall", engine);
//		Building brickWall = this.loader.loadObject("brickWall", engine);

		//Roof Tiles
		Building roofCorner = this.loader.loadObject("roofCorner", engine);
		Building roof = this.loader.loadObject("roof", engine);
		Building roofFlat = this.loader.loadObject("roofFlat", engine);
		Building roofOut = this.loader.loadObject("roofOut", engine);
		Building shadeRoof = this.loader.loadObject("/roofTiles/shadeRoof", engine);
		
		//Door
//		Building doorFrame = this.loader.loadObject("doors/doorFrame", engine);
//		doorFrame.setWalkThrough(true);
		
		/*
		 * GARDEN
		 */
		Building pottedPlant = this.loader.loadObject("pottedPlant", engine);
		Building pottedHedge = this.loader.loadObject("pottedHedge", engine);
		Building largeHedge = this.loader.loadObject("largeHedge", engine);
		Building tileHedge = this.loader.loadObject("tileHedge", engine);
		
//		Building borderedFence = this.loader.loadObject("fences/borderedFence", engine);
//
//		Building vinylFence = this.loader.loadObject("fences/vinylFence", engine);
		Building pillar = this.loader.loadObject("pillars/pillar", engine);
//		
//		Building brickFence = this.loader.loadObject("fences/brickFence", engine);
//		Building brickHalfPillar = this.loader.loadObject("pillars/brickHalfPillar", engine);
//		Building brickPillar = this.loader.loadObject("pillars/brickPillar", engine);
		
		/*
		 * EVENT / SEASONAL
		 */
		Building pumpkin = this.loader.loadObject("pumpkin", engine);
		
		Building wallObject = this.loader.loadObject("wallObjects/wallObject", engine);
		Building door = this.loader.loadObject("wallObjects/door", engine);
		door.getBuildingComponents().add(new DoorComponent());
		Building window = this.loader.loadObject("wallObjects/window", engine);
		
		/*
		 * FURNITURE
		 */
		Building basicBooth = this.loader.loadObject("basicBooth", engine);
		basicBooth.setIsTable(true);
		TableComponent booth = new TableComponent(TableType.BOOTH1);
		basicBooth.getBuildingComponents().add(booth);
		
		Building table = this.loader.loadObject("furniture/table", engine);
		Building chair = this.loader.loadObject("furniture/chair", engine);
		
		/*
		 * WORKSTATIONS
		 */
		Building chefStation = this.loader.loadObject("chefStation", engine);
		chefStation.getBuildingComponents().add(new ChefStation());
		
		Building waiterStation = this.loader.loadObject("waiterStation", engine);
		waiterStation.getBuildingComponents().add(new HostStand());

		/*
		 * WORK IN PROGRESS
		 */
		Building person1 = loadBuilding("person1", BuildingType.Person, engine);
		person1.name = "Customer_TEST";
		person1.setObstructive(false);
		person1.setIconZoom(0.5f);
		person1.getBuildingComponents().add(new PathfinderComponent());
		person1.getBuildingComponents().add(new CustomerComponent());
		person1.getBuildingComponents().add(new ActionComponent("CustomerOld"));
		BuildingList.register(person1, "WorkInProgress");
		
		Building waiter1 = loadBuilding("waiter1", BuildingType.Person, engine);
		waiter1.name = "Waiter";
		waiter1.setPrice(50);
		waiter1.setObstructive(false);
		waiter1.setIconZoom(0.5f);
		waiter1.getBuildingComponents().add(new ServerComponent());
		waiter1.getBuildingComponents().add(new PathfinderComponent());
		waiter1.getBuildingComponents().add(new ActionComponent("WaiterOld"));
		BuildingList.register(waiter1, "WorkInProgress");
		
		Building chef1 = loadBuilding("chef1", BuildingType.Person, engine);
		chef1.name = "Chef";
		chef1.setPrice(50);
		chef1.setObstructive(false);
		chef1.setIconZoom(0.5f);
		chef1.getBuildingComponents().add(new ChefComponent());
		chef1.getBuildingComponents().add(new PathfinderComponent());
		chef1.getBuildingComponents().add(new ActionComponent("ChefOld"));
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
		bld.model = model;
		return bld;
	}
	
	private void loadCategory(String name, String categoryIcon, Engine engine) {
		BuildingCategory category = new BuildingCategory(name);
		int icon = engine.getTextureLoader().loadTexture(categoryIcon);
		category.setIcon(icon);
		BuildingList.getRoot().addChild(category);
	}
}
