package com.seaSaltedToaster;

import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MainMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.loadingScreen.LoadingScreen;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.CreateMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.SavesMenu;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu.SettingsMenu;

public class MenuManager {
	
	private MainMenu mainMenu;
	private SettingsMenu settingsMenu;
	private SavesMenu savesMenu;
	private LoadingScreen loadingScreen;
	private CreateMenu createMenu;
	
	public MenuManager() {
		super();
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public SettingsMenu getSettingsMenu() {
		return settingsMenu;
	}

	public void setSettingsMenu(SettingsMenu settingsMenu) {
		this.settingsMenu = settingsMenu;
	}

	public SavesMenu getSavesMenu() {
		return savesMenu;
	}

	public void setSavesMenu(SavesMenu savesMenu) {
		this.savesMenu = savesMenu;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public void setLoadingScreen(LoadingScreen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	public CreateMenu getCreateMenu() {
		return createMenu;
	}

	public void setCreateMenu(CreateMenu createMenu) {
		this.createMenu = createMenu;
	}

	

}
