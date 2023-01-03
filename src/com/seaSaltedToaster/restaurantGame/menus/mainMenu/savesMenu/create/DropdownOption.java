package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.create;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class DropdownOption extends UiComponent {

	private DropdownBox parent;
	private Text display;
	
	public DropdownOption(String text, float stage, DropdownBox parent) {
		super(3);
		this.parent = parent;
		this.setScale(0.1f, 0.05f);
		this.setAlpha(0.0f);
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP, stage));
		
		this.display = new Text(text, 0.75f, 3);
		this.display.setColor(0.0f);
		this.addComponent(display);
		
		UiConstraints cons2 = this.display.getConstraints();
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.TOP, 0.4f));
		
		this.setInteractable(true, MainApp.restaurant.engine);
		LanguageManager.addText(text, display);
	}
	
	@Override
	public void onClick() {
		this.parent.setSelected(this);
	}

	public DropdownBox getParent() {
		return parent;
	}

	public Text getDisplay() {
		return display;
	}

}
