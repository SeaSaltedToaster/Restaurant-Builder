package com.seaSaltedToaster;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.DeleteTool;
import com.seaSaltedToaster.restaurantGame.menus.GeneralMenu;
import com.seaSaltedToaster.restaurantGame.menus.MoneyCounter;
import com.seaSaltedToaster.restaurantGame.menus.PaintMenu;
import com.seaSaltedToaster.restaurantGame.menus.TimeDisplay;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingMenu;
import com.seaSaltedToaster.restaurantGame.menus.employees.EmployeeMenu;
import com.seaSaltedToaster.restaurantGame.menus.layers.LayerMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MainMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.loadingScreen.IntroFade;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.loadingScreen.LoadingScreen;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.CreateMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.SavesMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu.SettingsMenu;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.save.LoadSystem;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.audio.management.AudioMaster;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.utilities.ScreenshotUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class MainApp {
	
	//Game logic
	public static Restaurant restaurant;
	
	//Menu logic
	public static boolean menuFocused = false;
	public static boolean quitApp = false;
	
	//Scenes in the game
	public static Scene game = null;
	public static Scene menu = null;
	public static String curSave = "";
	
	public static void main(String[] args) {	
		/*
		 * ENGINE AND RESOURCE BODIES
		 */
		Engine engine = new Engine("Restaurant Game", ClientConfigs.WINDOW_X, ClientConfigs.WINDOW_Y);
		engine.setCamera(new WorldCamera(engine));
		engine.getKeyboard().addKeyListener(new ScreenshotUtils());
		
		ResourceManager resourseManager = new ResourceManager();
		
		//Restaurant
		MainApp.restaurant = new Restaurant(engine);
		/*
		 * GAME STATE
		 */
		game = new Scene() {
			
			//Objects in the game world
			private Ground ground;
			private Raycaster raycaster;
			private BuildingManager manager;
			
			//Menus in the game world
			private GeneralMenu general;
			private BuildingMenu building;
			private EmployeeMenu employee;
			private PaintMenu paint;
			private DeleteTool delete;
			
			//Other objects
			private TimeDisplay timeDisplay;
			private MoneyCounter moneyCounter;
			private LayerMenu layerMenu;
			private IntroFade introFade;
			
			//Save system
			private SaveSystem saveSystem;
			private LoadSystem loadSystem;
			
			UiComponent comp;

			@Override
			public void loadScene(Engine engine) {
				/*
				 * GAME RESOURCES
				 */
				
				//Load all game assets
				resourseManager.doGameLoad(engine);
				engine.setCamera(new WorldCamera(engine));
				
				this.saveSystem = new SaveSystem(curSave);
				
				/*
				 * GAME OBJECTS
				 */
				//Layers
				this.ground = new Ground(25.0f, 0.25f, engine);
				
				this.raycaster = new Raycaster(engine);
				this.raycaster.ground = ground;
				
				this.manager = new BuildingManager(engine, 25.0f, raycaster, BuildingList.getRoot().getChildBuildings().get(0));
				
				//Ground the world is on
				this.loadSystem = new LoadSystem(curSave);
				this.ground.generateGround(loadSystem, engine);
				
				//Raycaster for building
				
				//Building manager
				this.raycaster.builder = manager;
								
				//Uis
				this.building = new BuildingMenu(manager, engine);
				engine.addUi(building);
				
				this.employee = new EmployeeMenu();
				engine.addUi(employee);
				
				this.layerMenu = new LayerMenu(manager, engine);
				engine.addUi(layerMenu);
				
				this.general = new GeneralMenu(engine);
				this.general.setBuilding(building);
				this.general.setEmployee(employee);
				engine.addUi(general);
				
				this.paint = new PaintMenu();
				this.raycaster.paint = paint;
				this.general.setPaint(paint);
				this.general.getButtons()[3].addComponent(paint);
				
				this.delete = new DeleteTool();
				this.general.setDelete(delete);
				
				this.timeDisplay = new TimeDisplay();
				engine.addUi(timeDisplay);
				
				this.moneyCounter = new MoneyCounter();
				engine.addUi(moneyCounter);
				
				this.introFade = new IntroFade(engine);
				this.introFade.slide(true);
				
				//Load save data
				this.loadSystem.loadCamera((WorldCamera) engine.getCamera());
				this.loadSystem.loadActions();
				this.loadSystem.loadBuildings(manager);
				
//				comp = new UiComponent(4);
//				comp.setScale(0.5f, -0.5f);
//				comp.setTexture(engine.getShadowRenderer().getShadowMap());
//				this.addComponent(comp);
								
				MainApp.menuFocused = false;
			}

			@Override
			public void renderScene(Engine engine) {
				ground.update(engine.getShadowRenderer(), engine);
				
				manager.updateFrame();
//				comp.setTexture(engine.getShadowRenderer().getShadowMap());
				
				introFade.update();
				
				engine.startShadows();
				for(BuildLayer layer : restaurant.layers)
					engine.renderShadows(layer.getBuildings());
				engine.renderShadows(ground.meshes);
				engine.endShadows();
			}

			@Override
			public void updateScene(Engine engine) {
				manager.renderSelection();
				
				if(GLFW.glfwGetKey(Window.windowID, GLFW.GLFW_KEY_BACKSPACE) == GLFW.GLFW_PRESS) {
					engine.setCurrentScene(MainApp.menu);
				}
			}

			@Override
			public void unloadScene(Engine engine) {
				saveSystem.save(engine.getCamera(), timeDisplay, ground, manager);
				
				saveSystem.openTo("actions");
				for(BuildLayer layer : manager.getLayers()) {
					for(Entity entity : layer.getBuildings()) {
						ActionComponent action = (ActionComponent) entity.getComponent("Action");
						if(action != null)
							action.save(saveSystem);
					}
				}
				saveSystem.closeWriter();
			}
			
		};
		
		/*
		 * MENU SCENE
		 */
		menu = new Scene() {
			
			/*
			 * MENU OBJECTS
			 */
			private MenuManager manager;
			
			private MainMenu mainMenu;
			private SettingsMenu settingsMenu;
			private SavesMenu savesMenu;
			private LoadingScreen loadingScreen;
			private CreateMenu createMenu;
			
			@Override
			public void loadScene(Engine engine) {
				/*
				 * STARTUP LOAD AND MENU ASSETS
				 */
				//Will do initial load if it hasnt been done yet
				resourseManager.doInitialLoad(engine);
				
				SaveSystem.createFolder();
				
				/*
				 * MENUS AND OBJECTS
				 */
				//Next create the main menus
				this.manager = new MenuManager();
				
				this.mainMenu = new MainMenu(manager, engine);
				this.settingsMenu = new SettingsMenu(manager, engine);
				this.savesMenu = new SavesMenu(manager, engine);
				this.loadingScreen = new LoadingScreen(manager, engine);
				this.createMenu = new CreateMenu(manager, engine);

				//No matter what, the camera will not move in the menu
				MainApp.menuFocused = true;
			}

			@Override
			public void renderScene(Engine engine) {
				
			}

			@Override
			public void updateScene(Engine engine) {
				mainMenu.update();
				settingsMenu.update();
				savesMenu.update();
				loadingScreen.update();
				createMenu.update();
			}

			@Override
			public void unloadScene(Engine engine) {
				//Close reason (exit game or go to new scene)
				mainMenu.getBackground().setActive(false);
				settingsMenu.getMainBody().setActive(false);
				savesMenu.getMainBody().setActive(false);
			}
			
		};

		//Set the new scene
		engine.setCurrentScene(menu);
		
		//Update per frame
		while(!engine.getWindow().shouldClose()) {	
			//Prepare new frame
			engine.prepareFrame();
			restaurant.update();
			
			//Shadow map pass
			engine.startPostProcess();
			
			//Render Normal Pass
			engine.renderScene();
			engine.render();
			
			//Post processing
			engine.postProcess();
			
			//Render Uis on top
			engine.updateScene();
			engine.renderUis();
			engine.update();
			//Check if we closed
			if(quitApp)
				break;
		}
		
		AudioMaster.cleanUp();
		
		engine.getCurrentScene().unloadScene(engine);
		
		engine.getWindow().closeWindow();
	}
	
	public static void addExample(Vector3f seatPosition) {
		Entity ent = BuildingList.getBuilding("Ball").getEntity().copyEntity();
		ent.setPosition(seatPosition);
		MainApp.restaurant.engine.addEntity(ent);
	}


}