package com.seaSaltedToaster.simpleEngine.uis.text.rendering;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.uis.text.Line;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.uis.text.Word;
import com.seaSaltedToaster.simpleEngine.uis.text.Character;

public class TextMeshCreator {
	
	public static final double LINE_HEIGHT = 0.03f;
	public static final int SPACE_ASCII = 32;
 
    private MetaFile metaData;
    
    private float xScale;
    private float lowestY, highestY;
    private float leftX, rightX;
 
    public TextMeshCreator(String metaFile) {
        metaData = new MetaFile(metaFile);
    }
 
    public TextMeshData createTextMesh(Text text) {
    	reset();
        List<Line> lines = createStructure(text);
        TextMeshData data = createQuadVertices(text, lines);
        calculateTextBoundScale(text);
        return data;
    }
 
    private void reset() {
		lowestY = 0;
		highestY = 0;
		leftX = 0;
		rightX = 0;
		xScale = 0;
	}

	private void calculateTextBoundScale(Text text) {
		float scaleY = 0;
		if(highestY > lowestY) {
			scaleY = (highestY - lowestY);
		} else if(lowestY > highestY) {
			scaleY = (lowestY - highestY);
		}
		
		text.setScale(xScale, scaleY);
	}

	private List<Line> createStructure(Text text) {
        List<Line> lines = new ArrayList<Line>();
        Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getLineMaxSize());
        Word currentWord = new Word(text.getFontSize());
        for (char c : text.getTextString().toCharArray()) {
            int ascii = (int) c;
            if (ascii == SPACE_ASCII) {
                boolean added = currentLine.attemptToAddWord(currentWord);
                if (!added) {
                    lines.add(currentLine);
                    currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getLineMaxSize());
                    currentLine.attemptToAddWord(currentWord);
                }
                currentWord = new Word(text.getFontSize());
                continue;
            }
            Character character = metaData.getCharacter(ascii);
            currentWord.addCharacter(character);
        }
        completeStructure(lines, currentLine, currentWord, text);
        return lines;
    }
 
    private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, Text text) {
        boolean added = currentLine.attemptToAddWord(currentWord);
        if (!added) {
            lines.add(currentLine);
            currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getLineMaxSize());
            currentLine.attemptToAddWord(currentWord);
        }
        lines.add(currentLine);
    }
 
    private TextMeshData createQuadVertices(Text text, List<Line> lines) {
        text.setNumberOfLines(lines.size());
        double curserX = 0f;
        double curserY = 0f;
        List<Float> vertices = new ArrayList<Float>();
        List<Float> textureCoords = new ArrayList<Float>();
        for (Line line : lines) {
            curserX = (line.getMaxLength() - line.getLineLength()) / 2;
            for (Word word : line.getWords()) {
                for (Character letter : word.getCharacters()) {
                    addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
                    addTexCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
                            letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
                    curserX += letter.getxAdvance() * text.getFontSize();
                }
                curserX += metaData.getSpaceWidth() * text.getFontSize();
            }
            curserX = 0;
            curserY += LINE_HEIGHT * text.getFontSize();
        }      
        return new TextMeshData(listToArray(vertices), listToArray(textureCoords));
    }
 
    private void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize,
            List<Float> vertices) {
    	xScale += character.getSizeX() * fontSize;
        double x = curserX + (character.getxOffset() * fontSize);
        double y = curserY + (character.getyOffset() * fontSize);
        double maxX = x + (character.getSizeX() * fontSize);
        double maxY = y + (character.getSizeY() * fontSize);
        checkBoundScaleX(maxX - x, x);
        checkBoundScaleY(Math.abs(maxY - y), Math.abs(y));
        double properX = (2 * x) - 1;
        double properY = (-2 * y) + 1;
        double properMaxX = (2 * maxX) - 1;
        double properMaxY = (-2 * maxY) + 1;
        addVertices(vertices, properX, properY, properMaxX, properMaxY);
    }
 
    private void checkBoundScaleX(double maxX, double x) {
		if(leftX == 0.0f && rightX == 0.0f) {
			leftX = (float) maxX;
			rightX = (float) x;
		} else {
			if(x < leftX) 
				leftX = (float) x;
			else if(maxX > rightX)
				rightX = (float) maxX;
		}
	}

	private void checkBoundScaleY(double maxY, double y) {
		if(highestY == 0.0f && lowestY == 0.0f) {
			highestY = (float) maxY;
			lowestY = (float) maxY;
		} else {
			if(y < lowestY) 
				lowestY = (float) y;
			else if(maxY > highestY)
				highestY = (float) maxY;
		}
	}

	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) {
        vertices.add((float) x);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) maxY);
        vertices.add((float) maxX);
        vertices.add((float) y);
        vertices.add((float) x);
        vertices.add((float) y);
    }
 
    private static void addTexCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) {
        texCoords.add((float) x);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) maxY);
        texCoords.add((float) maxX);
        texCoords.add((float) y);
        texCoords.add((float) x);
        texCoords.add((float) y);
    }
 
     
    private static float[] listToArray(List<Float> listOfFloats) {
        float[] array = new float[listOfFloats.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = listOfFloats.get(i);
        }
        return array;
    }

}
