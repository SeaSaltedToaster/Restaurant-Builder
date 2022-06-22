package com.seaSaltedToaster.simpleEngine.uis.text;

import java.util.ArrayList;
import java.util.List;

public class Word {

	private List<Character> characters = new ArrayList<Character>();
    private double width = 0;
    private double fontSize;
     
    public Word(double fontSize){
        this.fontSize = fontSize;
    }
     
    public void addCharacter(Character character){
        characters.add(character);
        width += character.getxAdvance() * fontSize;
    }

    public List<Character> getCharacters(){
        return characters;
    }

    public double getWordWidth(){
        return width;
    }
}
