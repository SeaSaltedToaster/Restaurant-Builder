package com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class SettingsMenu {
	
	//Settings
	public static int PANEL1;
	
	//Body and side panel
	private UiComponent mainBody, sidePanel;
	
	//Buttons
	private int[] icons;
	private List<SettingsButton> buttons;
	private List<SettingsPanel> panels;
	
	//Other
	private MenuManager manager;
	
	//Animations
	private SmoothFloat allX;
	
	public SettingsMenu(MenuManager manager, Engine engine) {
		this.manager = manager;
		this.manager.setSettingsMenu(this);
		createPanel(engine);
		this.buttons = new ArrayList<SettingsButton>();
		addButtons(engine);
	}
	
	public void update() {
		this.allX.update(Window.DeltaTime);
		this.mainBody.getPosition().setX(allX.getValue());
		
		for(SettingsPanel panel : panels) {
			panel.update(panel.isActive());
		}
	}
	
	private void addButtons(Engine engine) {
		this.icons = new int[16];
		this.icons[0] = engine.getTextureLoader().loadTexture("/uis/settings/monitor");
		this.icons[1] = engine.getTextureLoader().loadTexture("/uis/settings/gear");
		this.icons[2] = engine.getTextureLoader().loadTexture("/uis/settings/volumeIcon");
		this.icons[3] = engine.getTextureLoader().loadTexture("/uis/settings/language");
		this.icons[4] = engine.getTextureLoader().loadTexture("/uis/settings/keyboard");
		this.icons[5] = engine.getTextureLoader().loadTexture("/uis/settings/back");
		
		for(int i = 0; i < 6; i++) {
			SettingsButton button = new SettingsButton(this, i, engine);
			button.setTexture(icons[i]);
			this.sidePanel.addComponent(button);
			this.buttons.add(button);
		}
		
		this.panels = new ArrayList<SettingsPanel>();
		this.panels.add(new DisplaySettings(this));
		this.panels.add(new GraphicsSettings(this));
		this.panels.add(new AudioSettings(this));
		this.panels.add(new LanguageSettings(this));
		this.panels.add(new ControlSettings(this));
		openMenu(0);
	}

	private void createPanel(Engine engine) {
		this.mainBody = new UiComponent(1);
		this.mainBody.setScale(0.66f, 0.66f);
		this.mainBody.setAlpha(0.0f);
		engine.addUi(mainBody);
		
		this.sidePanel = new UiComponent(1);
		this.sidePanel.setAlpha(MenuSettings.TRANSP);
		this.sidePanel.setColor(MenuSettings.COLOR_S);
		this.sidePanel.setTexture(PANEL1);
		UiConstraints sidePanelCons = sidePanel.getConstraints();
		sidePanelCons.setWidth(new RelativeScale(0.125f));
		sidePanelCons.setHeight(new RelativeScale(1.0f));
		sidePanelCons.setX(new AlignX(XAlign.LEFT));
		sidePanelCons.setLayout(new VerticalLayout(-0.175f,0.0f));
		this.mainBody.addComponent(sidePanel);
		
		float startAt = 2.5f;
		this.allX = new SmoothFloat(startAt);
		this.allX.setValue(startAt);
		
		PANEL1 = engine.getTextureLoader().loadTexture("/uis/settings/panel1");
	}
	
	public void openMenu(int id) {
		for(SettingsPanel panel : panels) {
			panel.open(false);
		}
		panels.get(id).open(true);
	}
	
	public void slide(boolean in) {
		if(in) {
			this.allX.setTarget(0.0f);
		} else {
			this.allX.setTarget(2.5f);
		}
	}

	public UiComponent getMainBody() {
		return mainBody;
	}

	public UiComponent getSidePanel() {
		return sidePanel;
	}

	public List<SettingsButton> getButtons() {
		return buttons;
	}

	public MenuManager getManager() {
		return manager;
	}

	public SmoothFloat getAllX() {
		return allX;
	}
 
}
