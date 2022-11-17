package com.seaSaltedToaster.restaurantGame.menus.languages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LanguageReader {
	
	//Language file resource path
	private String LANGUAGE_FOLDER;
	
	public LanguageReader(String langugeFolder) {
		this.LANGUAGE_FOLDER = langugeFolder;
	}
	
	//Load all given language files
	public List<Language> loadLanguageFiles(String... files) {
		//Create language list
		List<Language> languages = new ArrayList<Language>();
		
		//Read data from all files
		for(String file : files) {
			Language language = loadLanguageFile(LANGUAGE_FOLDER + file + ".txt");
			languages.add(language);
		}
		
		return languages;
	}
	
	private Language loadLanguageFile(String file) {
		//Create ID list
		HashMap<String, String> languageList = new HashMap<String, String>();
		
		//Get all lines in the file
		String[] fileContents = openFile(file).split(";");
		
		//Seperating the data in each line
		for(String line : fileContents) {
			//Splits ID and Text
			String[] splitLine = line.split(",");
			
			String id = splitLine[0];
			String text = splitLine[1];
			languageList.put(id.trim(), text);
		}
		
		return new Language(languageList, file.replace(".txt", "").substring(LANGUAGE_FOLDER.toCharArray().length));
	}
	
	private String openFile(String file) {
		//Readers
		InputStreamReader isr;
		BufferedReader bufferedReader;
		
		//Open file
		System.out.println("Loaded langauge file " + file);
		isr = new InputStreamReader(LanguageReader.class.getResourceAsStream(file));
		bufferedReader = new BufferedReader(isr);
		
		//Create array of lines in file
		String fileLine = "";
		
		try {
			String line;
		    while ((line = bufferedReader.readLine()) != null) {
		    	fileLine += line;
		    }
			bufferedReader.close();
		} catch (IOException e) { e.printStackTrace(); }
		
		//Return full file
		return fileLine;
	}
	
}
