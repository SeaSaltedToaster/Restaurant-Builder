package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu;

import java.io.File;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.MenuManager;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class CreateButton extends UiComponent {

	private Timer lastClick;
	private Text title;
	
	private SavesMenu savesMenu;
	private CreateMenu cMenu;
	private SmoothFloat hovScale;
	
	public CreateButton(SavesMenu menu, CreateMenu cMenu) {
		super(3);
		this.savesMenu = menu;
		this.cMenu = cMenu;
		this.lastClick = new Timer(1);
		this.lastClick.start();
		createButton();
	}
	
	@Override
	public void onClick() {
		if(lastClick.isFinished()) {
			if(cMenu == null) 
			{
				MenuManager manager = savesMenu.getManager();
				manager.getSavesMenu().slide(false);
				manager.getCreateMenu().slide(true);
			}
			else 
			{
				String name = cMenu.getNameBox().getText();
				File save = SaveSystem.createSave(name);
				
				SaveSystem.setGroundType(name, cMenu.getGroundType().getSelected().getDisplay().getTextString());
				MainApp.curSave = name;
				
				this.cMenu.slide(false);
				this.cMenu.getManager().getLoadingScreen().slide(true);
			}
			lastClick.stop();
			lastClick.start();
		}
	}
	
	@Override
	public void updateSelf() {
		this.lastClick.update(Window.DeltaTime);
		
		this.hovScale.update(Window.DeltaTime);
		this.setScaleMultiplier(hovScale.getValue());
	}
	
	@Override
	public void onHover() {
		this.hovScale.setTarget(1.15f);
	}
	
	@Override
	public void stopHover() {
		this.hovScale.setTarget(1.0f);
	}
	
	public void pop(boolean in) {
		if(in)
			hovScale.setTarget(1.0f);
		else
			hovScale.setTarget(0.0f);
	}

	private void createButton() {
		this.setAlpha(MenuSettings.S_TRANSP + 0.10f);
		this.setColor(MenuSettings.S_COLOR - 0.10f);
		
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.5f));
		cons.setHeight(new AspectRatio(0.25f));
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.MIDDLE));
		
		this.title = new Text("saves_create", 1.25f, 1);
		this.title.setInteractable(true, MainApp.restaurant.engine);
		UiConstraints titleCons = title.getConstraints();
		titleCons.setX(new AlignX(XAlign.CENTER));
		titleCons.setY(new AlignY(YAlign.TOP, 0.4f));
		this.addComponent(title);
		LanguageManager.addText(title.getTextString(), title);
		
		this.hovScale = new SmoothFloat(1.0f);
		this.hovScale.setValue(0.0f);
		this.hovScale.setTarget(1.0f);
	}

}
