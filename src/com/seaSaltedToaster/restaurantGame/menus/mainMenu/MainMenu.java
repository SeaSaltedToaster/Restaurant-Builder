package com.seaSaltedToaster.restaurantGame.menus.mainMenu;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class MainMenu {
	
	//Main body 
	private UiComponent background;
	private int backgroundImage;
	private Text version;
	
	//Other
	private MenuManager manager;
	private Engine engine;
	
	//Buttons
	private MenuButton playB, settingsB, quitB, titleB;
	private int playBg, settingsBg, quitBg, titleImage;
	
	//Animations
	private UiComponent fadeBox, buttonContainer;
	private SmoothFloat fadeIn, allX;
	private boolean isClosingFade = false, movingX = false;
	
	public MainMenu(MenuManager manager, Engine engine) {
		this.manager = manager;
		this.manager.setMainMenu(this);
		this.engine = engine;
		createBackground(engine);
		addTitle(engine);
		addButtons(engine);
	}
	
	public void update() {	
		//Fading in or out
		this.fadeIn.update(Window.DeltaTime);
		this.fadeBox.setAlpha(fadeIn.getValue());
		
		//Closing the game
		if(isClosingFade && this.fadeIn.getValue() > 2.0f) {
			MainApp.quitApp = true;
		}
		
		//Moving all components
		this.allX.update(Window.DeltaTime);
		this.buttonContainer.getPosition().setX(allX.getValue());
	}

	private void addButtons(Engine engine) {
		//BUTTON BACKGROUND
		this.playBg = engine.getTextureLoader().loadTexture("/uis/playBg");
		this.settingsBg = engine.getTextureLoader().loadTexture("/uis/settingsBg");
		this.quitBg = engine.getTextureLoader().loadTexture("/uis/quitBg");
		
		//TITLE BODY
		this.titleImage = engine.getTextureLoader().loadTexture("/uis/titleBody");
		
		//TITLE
		this.titleB = new MenuButton(this, -1, "");
		this.titleB.setTexture(titleImage);
		UiConstraints cons = this.titleB.getConstraints();
		cons.setY(new AlignY(YAlign.TOP, 0.1f));
		this.buttonContainer.addComponent(titleB);
		
		//PLAY OR CONTINUE
		this.playB = new MenuButton(this, 0, "menu_play");
		this.playB.setTexture(playBg);
		this.buttonContainer.addComponent(playB);
		
		//SETTINGS
		this.settingsB = new MenuButton(this, 1, "menu_settings");
		this.settingsB.setTexture(settingsBg);
		this.buttonContainer.addComponent(settingsB);
		
		//EXIT
		this.quitB = new MenuButton(this, 2, "menu_quit");
		this.quitB.setTexture(quitBg);
		this.buttonContainer.addComponent(quitB);
	}

	private void addTitle(Engine engine) {
		//BUTTON CONTAINER
		this.buttonContainer = new UiComponent(0);
		this.buttonContainer.setAlpha(0.0f);
		this.buttonContainer.setScale(0.33f, 1.0f);
		UiConstraints cons = this.buttonContainer.getConstraints();
		cons.setLayout(new VerticalLayout(0.3f, 0.0125f));
		this.background.addComponent(buttonContainer);
		
		this.allX = new SmoothFloat(0.0f);
		this.allX.setAmountPer(0.0625f);
	
		//Version
		float textSize = 1.0f;
		this.version = new Text("Restaurantario Version 0.0.9a - AI Update P1", textSize, 0);
		this.version.setColor(0.0f);
		UiConstraints cons2 = version.getConstraints();
		cons2.setX(new AlignX(XAlign.LEFT, 0.0125f));
		cons2.setY(new AlignY(YAlign.BOTTOM, 0.033f));
		this.background.addComponent(version);
		LanguageManager.addText("version", version);
	}

	private void createBackground(Engine engine) {
		this.backgroundImage = engine.getTextureLoader().loadTexture("/uis/menuBackground18");
		this.background = new UiComponent(0);
		this.background.setTexture(backgroundImage);
		this.background.setScale(1.0f, 1.0f);
		engine.addUi(background);
		
		this.fadeBox = new UiComponent(0);
		this.fadeBox.setColor(1.0f);
		this.fadeBox.setScale(1.0f, 1.0f);
		engine.addUi(fadeBox);
		
		this.fadeIn = new SmoothFloat(0.0f);
		this.fadeIn.setValue(2.0f);
		this.fadeIn.setAmountPer(0.025f);
		this.fadeIn.setTarget(0.0f);
	}
	
	public void slide(boolean in) {
		if(in) {
			this.allX.setTarget(0.0f);
		} else {
			this.allX.setTarget(-2.5f);
		}
	}

	public MenuManager getManager() {
		return manager;
	}

	public Engine getEngine() {
		return engine;
	}

	public boolean isClosingFade() {
		return isClosingFade;
	}

	public void setClosingFade(boolean isClosingFade) {
		this.isClosingFade = isClosingFade;
	}

	public UiComponent getBackground() {
		return background;
	}

	public UiComponent getFadeBox() {
		return fadeBox;
	}

	public SmoothFloat getFadeIn() {
		return fadeIn;
	}

	public SmoothFloat getAllX() {
		return allX;
	}

	public void setAllX(SmoothFloat allX) {
		this.allX = allX;
	}

	public boolean isMovingX() {
		return movingX;
	}

	public void setMovingX(boolean movingX) {
		this.movingX = movingX;
	}

}
