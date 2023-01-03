package com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.languages.Language;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class LanguageOption extends UiComponent {

	private Language language;
	private Text text;
	
	public LanguageOption(Language language, int index) {
		super(6);
		this.language = language;
		
		this.text = new Text(this.language.getName(), 1.15f, 7);
		UiConstraints tcons = text.getConstraints();
		tcons.setX(new AlignX(XAlign.CENTER));
		tcons.setY(new AlignY(YAlign.TOP));
		this.addComponent(text);
		
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP, 0.33f + (float) index * 0.2f));
		this.setAlpha(0.0f);
		this.setScale(0.066f, 0.05f);
		this.setInteractable(true, MainApp.restaurant.engine);
	}
	
	@Override
	public void onClick() {
		LanguageManager.setLanguage(language.getName());
	}

}
