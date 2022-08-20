package com.seaSaltedToaster;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.BuildingMenu;
import com.seaSaltedToaster.restaurantGame.menus.GeneralMenu;
import com.seaSaltedToaster.restaurantGame.menus.LayerMenu;
import com.seaSaltedToaster.restaurantGame.menus.TimeDisplay;
import com.seaSaltedToaster.restaurantGame.menus.employees.EmployeeMenu;
import com.seaSaltedToaster.restaurantGame.menus.languages.Language;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageReader;
import com.seaSaltedToaster.restaurantGame.objects.BuildingRepository;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.audio.management.AudioMaster;
import com.seaSaltedToaster.simpleEngine.uis.text.Fonts;
import com.seaSaltedToaster.simpleEngine.utilities.ScreenshotUtils;

public class MainApp {
	
	//Game logic
	public static Restaurant restaurant;
	
	//Menu logic
	public static boolean menuFocused = false;
	
	public static void main(String[] args) {
		//Engine
		Engine engine = new Engine("Restaurant Game v0.0.4e", ClientConfigs.WINDOW_X, ClientConfigs.WINDOW_Y);
		engine.setCamera(new WorldCamera(engine));
		engine.getKeyboard().addKeyListener(new ScreenshotUtils());
		
		//Assets
		Fonts.loadFonts(engine);
		AudioMaster.init(engine);
		BuildingList.create();
		
		//Languages
		System.out.println("Loading Language Files");
		LanguageReader languageReader = new LanguageReader("" + "/lang/");
		List<Language> languages = languageReader.loadLanguageFiles("english", "deutsch");
				
		//Set defualt language
		LanguageManager.start(languages, "english");
		
		//Restaurant
		MainApp.restaurant = new Restaurant(engine);
		
		//Buildings
		BuildingRepository repository = new BuildingRepository();
		repository.registerFoods(engine);
		repository.registerBuildings(engine);
		
		//Ground
		Ground ground = new Ground(15, 1, engine);
		ground.generateGround(engine);
		
		//Ray
		Raycaster ray = new Raycaster(engine);
		ray.ground = ground;
		
		//Building
		BuildingManager manager = new BuildingManager(engine, ground, ray, BuildingList.getRoot().getChildBuildings().get(0));
		ray.builder = manager;
		
		//Uis
		BuildingMenu building = new BuildingMenu(manager, engine);
		engine.addUi(building);
		
		EmployeeMenu employee = new EmployeeMenu();
		engine.addUi(employee);
		
		LayerMenu layer = new LayerMenu(manager, engine);
		engine.addUi(layer);
		
		GeneralMenu general = new GeneralMenu(engine);
		general.setBuilding(building);
		general.setEmployee(employee);
		engine.addUi(general);
		
		//TimeDisplay timeDisplay = new TimeDisplay();
		//engine.addUi(timeDisplay);
				
		//Update per frame
		while(!engine.getWindow().shouldClose()) {	
			//Prepare new frame
			engine.prepareFrame();
			
			//Render
			ground.update(engine);
			manager.updateFrame();
			engine.render();
			//post processing and end render
			
			//Render Uis on top
			engine.renderUis();
			engine.update();
		}
		
		AudioMaster.cleanUp();
		engine.getWindow().closeWindow();
	}

}