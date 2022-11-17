package com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu;

import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
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

public class SettingsPanel extends UiComponent {

	//Objects
	private Text title;
	private float textSize = 2.0f;
	
	//Animation6
	private SmoothFloat titleWobble;
		
	public SettingsPanel(String name, SettingsMenu settingsMenu, int id) {
		super(1);
		createPanel();
		addTitle(name);
		settingsMenu.getMainBody().addComponent(this);
	}
	
	public void update(boolean active) {
		this.titleWobble.update(Window.DeltaTime);
		
		float wobbleAmount = titleWobble.getValue();
		this.title.setScaleMultiplier(wobbleAmount);
		for(UiComponent child : this.getChildren()) {
			child.setScaleMultiplier(wobbleAmount);
		}
	}

	public void open(boolean open) {
		if(open) {
			this.setActive(true);
			this.titleWobble.setValue(1.25f);
			this.titleWobble.setTarget(1.0f);
		}
		else {
			this.setActive(false);
			this.titleWobble.setTarget(1.25f);
		}
	}
	
	private void addTitle(String name) {
		this.title = new Text(name, textSize, 1);
		this.title.setColor(0.0f);
		this.addComponent(title);
		LanguageManager.addText(name, title);
		
		UiConstraints titleCons = title.getConstraints();
		titleCons.setX(new AlignX(XAlign.CENTER, 0.0f));
		titleCons.setY(new AlignY(YAlign.TOP, 0.05f));
		
		this.titleWobble = new SmoothFloat(1.25f);
		this.titleWobble.setValue(1.25f);
		title.setScaleMultiplier(titleWobble.getValue());
	}

	private void createPanel() {
		this.setAlpha(MenuSettings.TRANSP);
		this.setColor(MenuSettings.COLOR_M);
		this.setTexture(SettingsMenu.PANEL1);
		
		UiConstraints sidePanelCons = this.getConstraints();
		sidePanelCons.setWidth(new RelativeScale(0.825f));
		sidePanelCons.setHeight(new RelativeScale(1.0f));
		sidePanelCons.setX(new AlignX(XAlign.RIGHT));
		sidePanelCons.setLayout(new VerticalLayout(-0.175f,0.0f));
	}

}
