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
		loadCategory("Garden", "/uis/plant", engine);
		loadCategory("Workstations", "/uis/workstations", engine);
		loadCategory("Cafe Set", "/uis/cafe", engine);
		
		/*
		 * CAFE SET
		 */
		Building cafeWall1 = loadBuilding("cafe/cafeWall1", BuildingType.Wall, engine);
		cafeWall1.name = "Brick Wall";
		cafeWall1.setPrice(10);
		cafeWall1.setWall(true);
		cafeWall1.setIconZoom(0.66f);
		BuildingList.register(cafeWall1, "Cafe Set"); 
		
		Building cafeWall3 = loadBuilding("cafe/cafeWall3", BuildingType.Wall, engine);
		cafeWall3.name = "Trimmed Wall";
		cafeWall3.setPrice(10);
		cafeWall3.setWall(true);
		cafeWall3.setIconZoom(0.66f);
		BuildingList.register(cafeWall3, "Cafe Set"); 
		
		Building cafeWall2 = loadBuilding("cafe/cafeWall2", BuildingType.Wall, engine);
		cafeWall2.name = "Plain Wall";
		cafeWall2.setPrice(10);
		cafeWall2.setWall(true);
		cafeWall2.setIconZoom(0.66f);
		BuildingList.register(cafeWall2, "Cafe Set"); 
		
		Building testWall = loadBuilding("cafe/testWall", BuildingType.Floor, engine);
		testWall.name = "Plain Floor";
		testWall.setPrice(10);
		testWall.setFloor(true);
		testWall.setIconZoom(0.66f);
		BuildingList.register(testWall, "Cafe Set"); 
		
		/*
		 * WALLS
		 */
		Building simpleWall = loadBuilding("simpleWall", BuildingType.Wall, engine);
		simpleWall.name = "Simple Wall";
		simpleWall.setPrice(15);
		simpleWall.setWall(true);
		simpleWall.setIconZoom(0.66f);
		BuildingList.register(simpleWall, "General");
		
		Building simpleWindow = loadBuilding("simpleWindow", BuildingType.Wall, engine);
		simpleWindow.name = "Simple Window";
		simpleWindow.setPrice(15);
		simpleWindow.setWall(true);
		simpleWindow.setIconZoom(0.66f);
		BuildingList.register(simpleWindow, "General"); 
		
		Building simpleDoor = loadBuilding("simpleDoor", BuildingType.Wall, engine);
		simpleDoor.name = "Simple Door";
		simpleDoor.setPrice(20);
		simpleDoor.setWall(true);
		simpleDoor.setIconZoom(0.66f);
		simpleDoor.getBuildingComponents().add(new DoorComponent());
		BuildingList.register(simpleDoor, "General"); 
		
		Building simpleLowWall = loadBuilding("simpleLowWall", BuildingType.Wall, engine);
		simpleLowWall.name = "Simple Bar";
		simpleLowWall.setPrice(10);
		simpleLowWall.setWall(true);
		simpleLowWall.setIconZoom(0.66f);
		BuildingList.register(simpleLowWall, "General"); 
		
		/*
		 * FLOORS / ROOFS
		 */
		Building simpleFloor = loadBuilding("simpleFloor", BuildingType.Floor, engine);
		simpleFloor.name = "Tile Floor";
		simpleFloor.setPrice(15);
		simpleFloor.setFloor(true);
		simpleFloor.setIconZoom(1.0f);
		BuildingList.register(simpleFloor, "General");
		
		Building simpleRoof = loadBuilding("simpleRoof", BuildingType.Floor, engine);
		simpleRoof.name = "Flat Roof";
		simpleRoof.setPrice(15);
		simpleRoof.setFloor(true);
		simpleRoof.setIconZoom(1.0f);
		BuildingList.register(simpleRoof, "General");
		
		Building simpleSidewalk = loadBuilding("simpleSidewalk", BuildingType.Floor, engine);
		simpleSidewalk.name = "Sidewalk";
		simpleSidewalk.setPrice(10);
		simpleSidewalk.setFloor(true);
		simpleSidewalk.setIconZoom(1.0f);
		BuildingList.register(simpleSidewalk, "General");
		
		/*
		 * FURNITURE
		 */
		Building simpleBooth = loadBuilding("simpleBooth", BuildingType.Object, engine);
		simpleBooth.name = "Booth";
		simpleBooth.setPrice(25);
		simpleBooth.setIsTable(true);
		simpleBooth.setObstructive(true);
		simpleBooth.setIconZoom(1.0f);
		BuildingList.register(simpleBooth, "Furniture");
		TableComponent table = new TableComponent(TableType.BOOTH1);
		simpleBooth.getBuildingComponents().add(table);
		
		Building simpleTable = loadBuilding("simpleTable", BuildingType.Object, engine);
		simpleTable.name = "Table";
		simpleTable.setPrice(20);
		simpleTable.setIsTable(true);
		simpleTable.setObstructive(true);
		simpleTable.setIconZoom(1.0f);
		BuildingList.register(simpleTable, "Furniture");
		TableComponent table2 = new TableComponent(TableType.TABLE1);
		simpleTable.getBuildingComponents().add(table2);
		
		Building simpleStool = loadBuilding("simpleStool", BuildingType.Object, engine);
		simpleStool.name = "Stool";
		simpleStool.setPrice(15);
		simpleStool.setObstructive(false); //true
		simpleStool.setIconZoom(0.5f);
		BuildingList.register(simpleStool, "Furniture");
		
		/*
		 * WORKSTATIONS
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
		
		/*
		 * PLANTS
		 */
		Building simplePlant = loadBuilding("simplePlant", BuildingType.Object, engine);
		simplePlant.name = "Plant";
		simplePlant.setPrice(10);
		simplePlant.setObstructive(true);
		simplePlant.setIconZoom(0.66f);
		BuildingList.register(simplePlant, "Garden");
		
		Building simplePotHedge = loadBuilding("simplePotHedge", BuildingType.Object, engine);
		simplePotHedge.name = "Small Hedge";
		simplePotHedge.setPrice(15);
		simplePotHedge.setWall(true);
		simplePotHedge.setIconZoom(0.5f);
		BuildingList.register(simplePotHedge, "Garden");
		
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
		
		Building patio = loadBuilding("patio", BuildingType.Floor, engine);
		patio.name = "Patio";
		patio.setPrice(20);
		patio.setFloor(true);
		patio.setIconZoom(1.0f);
		BuildingList.register(patio, "Garden");
		
		/*
		 * TESTING
		 */
		Building person1 = loadBuilding("person1", BuildingType.Person, engine);
		person1.name = "Customer_TEST";
		person1.setObstructive(false);
		person1.setIconZoom(0.5f);
		person1.getBuildingComponents().add(new PathfinderComponent());
		person1.getBuildingComponents().add(new CustomerComponent());
		person1.getBuildingComponents().add(new ActionComponent(PersonType.CUSTOMER));
		BuildingList.register(person1, "null");
		
		Building waiter1 = loadBuilding("waiter1", BuildingType.Person, engine);
		waiter1.name = "Waiter";
		waiter1.setPrice(50);
		waiter1.setObstructive(false);
		waiter1.setIconZoom(0.5f);
		waiter1.getBuildingComponents().add(new ServerComponent());
		waiter1.getBuildingComponents().add(new PathfinderComponent());
		waiter1.getBuildingComponents().add(new ActionComponent(PersonType.WAITER));
		BuildingList.register(waiter1, "General");
		

		Building chef1 = loadBuilding("chef1", BuildingType.Person, engine);
		chef1.name = "Chef";
		chef1.setPrice(50);
		chef1.setObstructive(false);
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
		
		Food beefTacos = loadFood("foods/beefTacos", engine);
		FoodRegistry.registerFood(beefTacos, 3);
		
		Food simpleStew = loadFood("foods/simpleStew", engine);
		FoodRegistry.registerFood(simpleStew, 4);
		
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
