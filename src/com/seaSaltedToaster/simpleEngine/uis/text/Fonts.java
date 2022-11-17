package com.seaSaltedToaster.simpleEngine.uis.text;

import com.seaSaltedToaster.simpleEngine.Engine;

public class Fonts {

	public static FontType ARIAL;
	
	public static void loadFonts(Engine engine) {
		ARIAL = new FontType(engine.getTextureLoader().loadTexture("/uis/berlin", -0.0f), "/uis/berlin");
	}
	
}
