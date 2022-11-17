package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu.SettingsMenu;
import com.seaSaltedToaster.restaurantGame.save.LoadSystem;
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

public class SavesMenu implements ScrollListener {
	
	//Other objects
	private MenuManager manager;
	private Engine engine;
	
	//Body and side panel
	private UiComponent mainBody, topBar;
	private Text title;
	private SavesBack backButton;
	
	//Saves lists
	private UiComponent backer;
	private List<SaveButton> buttons;
	
	//Animations
	private SmoothFloat allX, titleSa, scroll;
	
	public SavesMenu(MenuManager manager, Engine engine) {
		this.manager = manager;
		this.manager.setSavesMenu(this);
		this.engine = engine;
		createPanel(engine);
		addBarItems(engine);
		addSaves(engine);
		engine.getMouse().addScrollListener(this);
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
		
		//Scroll pane
		this.scroll.update(Window.DeltaTime);
		VerticalLayout layout = (VerticalLayout) backer.getConstraints().getLayout();
		layout.setEdgeSpace(scroll.getValue()); 
	}
	
	@Override
	public void notifyScrollChanged(float scrollValue) {
		this.scroll.increaseTarget(scrollValue * 0.05f);
	}
	
	private void addSaves(Engine engine) {
		//Backer
		this.backer = new UiComponent(1);
		this.backer.setColor(1.0f);
		this.backer.setAlpha(0.0f);
		UiConstraints backerCons = backer.getConstraints();
		backerCons.setWidth(new RelativeScale(1.0f));
		backerCons.setHeight(new RelativeScale(0.85f));
		backerCons.setX(new AlignX(XAlign.CENTER));
		backerCons.setY(new AlignY(YAlign.BOTTOM));
		backerCons.setLayout(new VerticalLayout(-0.15f, 0.066f));
		this.mainBody.addComponent(backer);
		
		//Scroll pane
		this.scroll = new SmoothFloat(-0.15f);
		this.scroll.setValue(-0.15f);
		
		//Get files
		String saveLocation = System.getProperty("user.home") + "/Desktop";
		File file = new File(saveLocation + "/RestaurantGame/saves");
		
		//Add buttons
		this.buttons = new ArrayList<SaveButton>();
		for(int i = 0; i < file.listFiles().length; i++) {
			File child = file.listFiles()[i];
			int icon = LoadSystem.getSaveIcon(child.getName(), engine);
			
			SaveButton btn = new SaveButton(child, icon, this);
			btn.setInteractable(true, engine);
			
			this.buttons.add(btn);
			this.backer.addComponent(btn);
		}
	}
	
	private void addBarItems(Engine engine) {
		this.backButton = new SavesBack(this, engine);
		this.backButton.setInteractable(true, engine);
		this.topBar.addComponent(backButton);
		
		this.title = new Text("saves_title", 2.25f, 1);
		this.title.setInteractable(true, engine);
		UiConstraints titleCons = title.getConstraints();
		titleCons.setWidth(new RelativeScale(0.5f));
		titleCons.setHeight(new RelativeScale(0.75f));
		titleCons.setX(new AlignX(XAlign.CENTER));
		titleCons.setY(new AlignY(YAlign.TOP, -0.125f));
		this.topBar.addComponent(title);
		LanguageManager.addText(title.getTextString(), title);
		
		this.titleSa = new SmoothFloat(1.0f);
		this.titleSa.setValue(1.0f);
	}
	
	public void slide(boolean in) {
		if(in) {
			this.allX.setTarget(0.0f);
		} else {
			this.allX.setTarget(2.5f);
		}
		
		for(SaveButton btn : buttons) {
			btn.pop(in);
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

	public MenuManager getManager() {
		return manager;
	}

	public Engine getEngine() {
		return engine;
	}

	public UiComponent getMainBody() {
		return mainBody;
	}

	public SmoothFloat getAllX() {
		return allX;
	}
	
}
