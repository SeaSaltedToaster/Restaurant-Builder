package com.seaSaltedToaster.simpleEngine.uis.text;

import java.util.ArrayList;
import java.util.List;

public class Line {
	private double maxLength;
    private double spaceSize;
 
    private List<Word> words = new ArrayList<Word>();
    private double currentLineLength = 0;

    public Line(double spaceWidth, double fontSize, double maxLength) {
        this.spaceSize = spaceWidth * fontSize;
        this.maxLength = maxLength;
    }

    public boolean attemptToAddWord(Word word) {
        double additionalLength = word.getWordWidth();
        additionalLength += !words.isEmpty() ? spaceSize : 0;
        if (currentLineLength + additionalLength <= maxLength) {
            words.add(word);
            currentLineLength += additionalLength;
            return true;
        } else {
            return false;
        }
    }

    public double getMaxLength() {
        return maxLength;
    }

    public double getLineLength() {
        return currentLineLength;
    }
 
    public List<Word> getWords() {
        return words;
    }
}
