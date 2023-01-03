package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu;

import java.util.List;

import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.create.DropdownBox;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.create.TextBox;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu.SettingsMenu;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class CreateMenu {

	//Other objects
	private MenuManager manager;
	
	//Body and side panel
	private UiComponent mainBody, topBar, bottomBar, backer;
	private Text title, title2;
	private CreateBack backButton;
	private CreateButton createButton;
	
	//Create portion
	private Text saveName, groundTxt, modeTxt;
	private TextBox nameBox;
	private DropdownBox groundType, modeType;
	
	//Animations
	private SmoothFloat allX, titleSa;
	
	public CreateMenu(MenuManager manager, Engine engine) {
		this.manager = manager;
		this.manager.setCreateMenu(this);
		createPanel(engine);
		addBarItems(engine);
		addCreate(engine);
		slide(false);
	}
	
	public void update() {
		this.allX.update(Window.DeltaTime);
		this.mainBody.getPosition().setX(allX.getValue());
		
		this.titleSa.update(Window.DeltaTime);
		if(title.isHovering()) {
			this.titleSa.setTarget(1.25f);
		} else {
			this.titleSa.setTarget(1.0f);
		}
		this.title.setScaleMultiplier(titleSa.getValue());
	}
	
	private void addCreate(Engine engine) {
		this.nameBox = new TextBox(engine);
		this.backer.addComponent(nameBox);
		
		this.saveName = new Text("Save Name :", 0.75f, 1);
		UiConstraints titleCons = saveName.getConstraints();
		titleCons.setX(new AlignX(XAlign.LEFT));
		titleCons.setY(new AlignY(YAlign.TOP, -0.75f));
		this.nameBox.addComponent(saveName);
		LanguageManager.addText(saveName.getTextString(), saveName);
		
		/*
		 * GROUND TYPE
		 */
		this.groundType = new DropdownBox(0.4f, 0.25f, 0.0f);
		this.groundType.addOptions("groundtype_grass", "groundtype_snow", "groundtype_arid");
		this.backer.addComponent(groundType);
		
		this.groundTxt = new Text("Map Style :", 0.75f, 1);
		UiConstraints titleCons2 = groundTxt.getConstraints();
		titleCons2.setX(new AlignX(XAlign.LEFT, 0.0f));
		titleCons2.setY(new AlignY(YAlign.TOP, -0.75f));
		this.groundType.addComponent(groundTxt);
		LanguageManager.addText(groundTxt.getTextString(), groundTxt);
		
		/*
		 * MODE TYPE
		 */
		this.modeType = new DropdownBox(0.4f, 0.25f, 0.0f);
		this.modeType.addOptions("modetype_career", "modetype_creative");
		this.backer.addComponent(modeType);
		
		this.modeTxt = new Text("Game Mode :", 0.75f, 1);
		UiConstraints titleCons3 = modeTxt.getConstraints();
		titleCons3.setX(new AlignX(XAlign.LEFT, 0.0f));
		titleCons3.setY(new AlignY(YAlign.TOP, -0.75f));
		this.modeType.addComponent(modeTxt);
		LanguageManager.addText(modeTxt.getTextString(), modeTxt);
	}


	private void addBarItems(Engine engine) {
		this.backButton = new CreateBack(this, engine);
		this.backButton.setInteractable(true, engine);
		this.topBar.addComponent(backButton);
		
		this.createButton = new CreateButton(null, this);
		this.createButton.setInteractable(true, engine);
		this.bottomBar.addComponent(createButton);
		
		this.title = new Text("saves_title", 2.25f, 1);
		this.title.setInteractable(true, engine);
		UiConstraints titleCons = title.getConstraints();
		titleCons.setWidth(new RelativeScale(0.5f));
		titleCons.setHeight(new RelativeScale(0.75f));
		titleCons.setX(new AlignX(XAlign.CENTER));
		titleCons.setY(new AlignY(YAlign.TOP, -0.125f));
		this.topBar.addComponent(title);
		LanguageManager.addText(title.getTextString(), title);
		
		this.title2 = new Text("saves_create_new", 2.25f, 1);
		this.title2.setInteractable(true, engine);
		UiConstraints titleCons2 = title2.getConstraints();
		titleCons2.setWidth(new RelativeScale(0.5f));
		titleCons2.setHeight(new RelativeScale(0.75f));
		titleCons2.setX(new AlignX(XAlign.CENTER));
		titleCons2.setY(new AlignY(YAlign.TOP, -0.125f));
		this.topBar.addComponent(title2);
		LanguageManager.addText(title2.getTextString(), title2);
		this.title2.setActive(false);
		
		this.titleSa = new SmoothFloat(1.0f);
		this.titleSa.setValue(1.0f);
		
		//Backer
		this.backer = new UiComponent(1);
		this.backer.setColor(1.0f);
		this.backer.setAlpha(0.0f);
		UiConstraints backerCons = backer.getConstraints();
		backerCons.setWidth(new RelativeScale(1.0f));
		backerCons.setHeight(new RelativeScale(0.85f));
		backerCons.setX(new AlignX(XAlign.CENTER));
		backerCons.setY(new AlignY(YAlign.BOTTOM));
		backerCons.setLayout(new VerticalLayout(0.0f, 0.25f));
		this.mainBody.addComponent(backer);
	}
	
	public void slide(boolean in) {
		if(in) {
			this.allX.setTarget(0.0f);
		} else {
			this.allX.setTarget(2.5f);
		}
	}

	private void createPanel(Engine engine) {
		this.mainBody = new UiComponent(1);
		this.mainBody.setScale(0.33f, 0.85f);
		this.mainBody.setColor(0.0f);
		this.mainBody.setAlpha(MenuSettings.TRANSP);
		this.mainBody.setTexture(SettingsMenu.PANEL1);
		UiConstraints cons = mainBody.getConstraints();
		cons.setLayout(new VerticalLayout(0.05f, 0.05f));
		engine.addUi(mainBody);
		
		this.topBar = createBar(YAlign.TOP);
		this.mainBody.addComponent(topBar);
		
		this.bottomBar = createBar(YAlign.BOTTOM);
		this.mainBody.addComponent(bottomBar);
		
		float startAt = 2.5f;
		this.allX = new SmoothFloat(startAt);
		this.allX.setValue(startAt);
	}

	private UiComponent createBar(YAlign align) {
		UiComponent bar = new UiComponent(1);
		bar.setAlpha(MenuSettings.S_TRANSP);
		bar.setColor(MenuSettings.S_COLOR);
		UiConstraints sidePanelCons = bar.getConstraints();
		sidePanelCons.setWidth(new RelativeScale(0.966f));
		sidePanelCons.setHeight(new RelativeScale(0.125f));
		sidePanelCons.setX(new AlignX(XAlign.CENTER));
		sidePanelCons.setY(new AlignY(align, 0.033f));
		return bar;
	}

	public DropdownBox getGroundType() {
		return groundType;
	}

	public DropdownBox getModeType() {
		return modeType;
	}

	public TextBox getNameBox() {
		return nameBox;
	}

	public MenuManager getManager() {
		return manager;
	}

}
