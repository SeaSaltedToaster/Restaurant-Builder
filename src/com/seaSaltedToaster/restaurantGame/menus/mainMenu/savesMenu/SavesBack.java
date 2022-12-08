package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu;

import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class SavesBack extends UiComponent {

	//Objects
	private SavesMenu savesMenu;
	
	//Animation
	private SmoothFloat sizeAnim;
	
	public SavesBack(SavesMenu savesMenu, Engine engine) {
		super(1);
		this.savesMenu = savesMenu;
		createButton(engine);
	}
	
	@Override
	public void updateSelf() {
		this.sizeAnim.update(Window.DeltaTime);
		
		float val = this.sizeAnim.getValue();
		this.setScaleMultiplier(val);
	}
	
	@Override
	public void onClick() {
		if(!savesMenu.saveSide) {
			savesMenu.setState(true);
			return;
		}
		
		MenuManager manager = this.savesMenu.getManager();
		manager.getMainMenu().slide(true);
		savesMenu.slide(false);
	}
	
	@Override
	public void onHover() {
		this.sizeAnim.setTarget(1.33f);
	}
	
	@Override
	public void stopHover() {
		this.sizeAnim.setTarget(1.0f);
	}

	private void createButton(Engine engine) {
		int icon = engine.getTextureLoader().loadTexture("/uis/settings/back");
		this.setTexture(icon);
		
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new AspectRatio(1.0f));
		cons.setHeight(new RelativeScale(0.85f));
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP, 0.33f));
		
		this.sizeAnim = new SmoothFloat(1.0f);
		this.sizeAnim.setValue(1.0f);
	}

}
