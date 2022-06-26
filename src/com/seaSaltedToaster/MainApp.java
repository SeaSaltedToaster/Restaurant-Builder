package com.seaSaltedToaster;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.renderer.BuildingRenderer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.ItemMenu;
import com.seaSaltedToaster.restaurantGame.menus.LayerMenu;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.text.Fonts;
import com.seaSaltedToaster.simpleEngine.utilities.ScreenshotUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class MainApp {
	
	public static Transform transform = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));
	public static Transform transform2 = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));

	public static void main(String[] args) {
		//Engine
		Engine engine = new Engine("Engine Test", ClientConfigs.WINDOW_X, ClientConfigs.WINDOW_Y);
		engine.setCamera(new WorldCamera(engine));
		engine.getKeyboard().addKeyListener(new ScreenshotUtils());
		
		//Assets
		Fonts.loadFonts(engine);
		BuildingList.create();
		
		//Buildings
		loadBuilding("simpleWall", 0, true, engine);
		loadBuilding("simpleFloor", 1, false, engine);
		loadBuilding("simpleLowWall", 2, true, engine);
		loadBuilding("simpleWindow", 3, true, engine);
		loadBuilding("simplePlant", 4, false, engine);
		loadBuilding("simpleBooth", 5, false, engine);
		loadBuilding("simpleStool", 6, false, engine);
		loadBuilding("simplePotHedge", 7, false, engine);
		loadBuilding("simpleDoor", 8, true, engine);
		loadBuilding("simpleRoof", 9, false, engine);
		loadBuilding("simpleSidewalk", 10, false, engine);
		
		//Ground
		Ground ground = new Ground(10, 1, engine);
		ground.generateGround(engine);
		
		//Ray
		Raycaster ray = new Raycaster(engine);
		ray.ground = ground;
		
		//Building
		BuildingManager manager = new BuildingManager(engine, ground, ray, null);
		ray.builder = manager;
		
		//Uis
		ItemMenu ui = new ItemMenu(manager, engine);
		engine.addUi(ui);
		LayerMenu layer = new LayerMenu(manager, engine);
		engine.addUi(layer);
		
		//Update per frame
		while(!engine.getWindow().shouldClose()) {	
			engine.prepareFrame();
			ground.update(engine);
			manager.updateFrame();
			engine.render();
			engine.renderUis();
			engine.update();
		}
		
		engine.getWindow().closeWindow();
	}
	
	private static void loadBuilding(String name, int id, boolean isWall, Engine engine) {
		Vao model = engine.getObjLoader().loadObjModel(name);
		Entity entity = new Entity();
		entity.addComponent(new ModelComponent(model));
		Building bld = new Building(entity, isWall);
		BuildingList.register(bld, id);
	}

}