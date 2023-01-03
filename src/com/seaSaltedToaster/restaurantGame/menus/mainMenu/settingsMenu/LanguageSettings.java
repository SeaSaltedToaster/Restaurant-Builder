package com.seaSaltedToaster.restaurantGame.menus.mainMenu.settingsMenu;

import com.seaSaltedToaster.restaurantGame.menus.languages.Language;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;

public class LanguageSettings extends SettingsPanel {

	public LanguageSettings(SettingsMenu settingsMenu) {
		super("language_settings", settingsMenu, 3);
		createOptions();
	}

	private void createOptions() {
		for(Language language : LanguageManager.getLanguages()) 
		{
			int index = LanguageManager.getLanguages().indexOf(language);
			LanguageOption option = new LanguageOption(language, index);
			this.addComponent(option);
		}
	}
	
}
