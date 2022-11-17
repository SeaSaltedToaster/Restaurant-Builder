package com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu;

import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MainMenu;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class SettingsButton extends UiComponent {
	
	//Objects
	private SettingsMenu settingsMenu;
	private int id;
	
	//Aniamtion
	private SmoothFloat resizer;

	public SettingsButton(SettingsMenu settingsMenu, int id, Engine engine) {
		super(1);
		this.settingsMenu = settingsMenu;
		this.id = id;
		createButton();
		this.setInteractable(true, engine);
	}
	
	@Override
	public void onClick() {
		if(id == 5) {
			settingsMenu.slide(false);
			
			MainMenu menu = settingsMenu.getManager().getMainMenu();
			menu.slide(true);
		} else {
			settingsMenu.openMenu(id);
		}
	}
	
	@Override
	public void updateSelf() {
		this.resizer.update(Window.DeltaTime);
		
		float value = resizer.getValue();
		setSize(value);
	}
	
	@Override
	public void onHover() {
		this.resizer.setTarget(0.79f);
	}

	@Override
	public void stopHover() {
		this.resizer.setTarget(0.66f);
	}
	
	private void setSize(float size) {
		RelativeScale xScale = (RelativeScale) this.getConstraints().getXScaleConstraint();
		xScale.setRelativeScale(size);
	}

	private void createButton() {
		UiConstraints btnCons = this.getConstraints();
		btnCons.setWidth(new RelativeScale(0.66f));
		btnCons.setHeight(new AspectRatio(1.0f));
		btnCons.setX(new AlignX(XAlign.CENTER));
		
		this.resizer = new SmoothFloat(0.66f);
	}

}
