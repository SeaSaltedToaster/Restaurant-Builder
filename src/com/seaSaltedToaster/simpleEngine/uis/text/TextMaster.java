package com.seaSaltedToaster.simpleEngine.uis.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.uis.text.rendering.FontRenderer;
import com.seaSaltedToaster.simpleEngine.uis.text.rendering.TextMeshData;

public class TextMaster {
	
    private static Map<FontType, List<Text>> textList = new HashMap<FontType, List<Text>>();
    private static List<Text> textMap = new ArrayList<Text>();
    
    public static void clear() {
    	textList.clear();
    	textMap.clear();
    }
     
    public static void loadText(Text text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        Vao vao = VaoLoader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setTextMeshVao(vao);
        text.setVertexCount(data.getVertexCount());
        List<Text> textBatch = textList.get(font);
        if(textBatch == null){
            textBatch = new ArrayList<Text>();
            textList.put(font, textBatch);
        }
        textBatch.add(text);
        textMap.add(text);
    }
     
    @SuppressWarnings("unlikely-arg-type")
	public static void removeText(Text text){
        List<Text> textBatch = textList.get(text.getFont());
        textBatch.remove(text);
        if(textBatch.isEmpty()){
        	textList.remove(textList.get(text.getFont()));
        }
        textMap.remove(text);
    }
     
    public static void cleanUp(){
    	clear();
    }

	public static Map<FontType, List<Text>> getTexts() {
		return textList;
	}

	public static List<Text> getTextz() {
		return textMap;
	}

}
