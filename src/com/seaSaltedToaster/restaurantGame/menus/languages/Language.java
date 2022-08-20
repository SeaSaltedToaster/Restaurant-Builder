package com.seaSaltedToaster.restaurantGame.menus.languages;

import java.util.HashMap;

public class Language {
	
	//First string is the ID of the text, second is the translated text string
	private HashMap<String, String> languageList = new HashMap<String, String>();
	private String name;

	public Language(HashMap<String, String> languageList, String name) {
		this.languageList = languageList;
		this.name = name;
	}

	public HashMap<String, String> getLanguageList() {
		return languageList;
	}

	public void setLanguageList(HashMap<String, String> languageList) {
		this.languageList = languageList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
