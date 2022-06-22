package com.seaSaltedToaster.simpleEngine.uis.text;

import com.seaSaltedToaster.simpleEngine.uis.text.rendering.TextMeshCreator;
import com.seaSaltedToaster.simpleEngine.uis.text.rendering.TextMeshData;

public class FontType {
	
	private int textureAtlas;
    private TextMeshCreator loader;

    public FontType(int textureAtlas, String fontFile) {
        this.textureAtlas = textureAtlas;
        this.loader = new TextMeshCreator(fontFile);
    }
 
    public int getTextureAtlas() {
        return textureAtlas;
    }

    public TextMeshData loadText(Text text) {
        return loader.createTextMesh(text);
    }

}
