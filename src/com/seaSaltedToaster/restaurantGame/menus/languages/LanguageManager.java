package com.seaSaltedToaster.restaurantGame.menus.languages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class LanguageManager {

	//Language management properties
	private static List<Language> languages = new ArrayList<Language>();
	private static Language currentLanguage;
	
	//Map of all text instance in the game, and their ID
	private static HashMap<String, Text> textMap = new HashMap<String, Text>();
	
	public static void start(List<Language> languageList, String defaultLanguage) {
		//Properties
		languages = languageList;
		textMap = new HashMap<String, Text>();
		
		//Sets default language
		currentLanguage = getLanguage(defaultLanguage);
		setLanguage(currentLanguage.getName());
	}
	
	//Sets a different language
	public static void setLanguage(String languageText) {
		//Sets current language
		Language language = getLanguage(languageText);
		currentLanguage = language;
		
		//Loops through all IDs and checks if there is a matching text
		for(Entry<String, Text> textEntry : textMap.entrySet()) {
			Text text = textEntry.getValue();
			String id = textEntry.getKey();
			setTextToCurrentLanguage(id, text, new Object[0]);
		}
		System.out.println("Set game language to " + languageText);
	}
	
	
	//Gets a language from its name
	public static Language getLanguage(String name) {
		for(Language language : languages) {
			if(language.getName().contains(name)) {
				return language;
			}
		}
		System.out.println("Unknown Language Error : " + name);
		return null;
	}
	
	//Adds a new text object and translates it if necessary
	public static void addText(String id, Text text) {
		textMap.put(id, text);
		setTextToCurrentLanguage(id, text, new Object[0]);
	}
	
	public static void addText(String id, Text text, Object[] objects) {
		textMap.put(id, text);
		setTextToCurrentLanguage(id, text, objects);
	}
	
	//Translate a text to the current language
	public static void setTextToCurrentLanguage(String id, Text text, Object[] objects) {
		for(Entry<String, LangTerm> langEntry : currentLanguage.getLanguageList().entrySet()) {
			if(langEntry.getKey().equals(id)) {
				
				String langVal = new String(langEntry.getValue().getTerm().trim());
				for(int i = 0; i < objects.length; i++)
				{
					String key = "(val" + (i+1) + ")";
					if(langVal.contains(key)) 
					{
						langVal.replace(key.trim(), "1");
						System.out.println(key + " : " + langVal);
					}
				}
				text.setTextString(langVal);
				
				return;
			}
		}
		//No match found
		System.out.println("No translation match in " + currentLanguage.getName() + " found for : " + id);
	}
	
	public static List<Language> getLanguages() {
		return languages;
	}
	
}
