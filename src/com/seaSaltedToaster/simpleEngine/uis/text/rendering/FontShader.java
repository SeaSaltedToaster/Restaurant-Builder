package com.seaSaltedToaster.seaSaltedEngine.uis.text.rendering;

import com.seaSaltedToaster.seaSaltedEngine.rendering.shading.Shader;
import com.seaSaltedToaster.seaSaltedEngine.rendering.shading.uniforms.UniformFloat;
import com.seaSaltedToaster.seaSaltedEngine.rendering.shading.uniforms.UniformVec2;
import com.seaSaltedToaster.seaSaltedEngine.rendering.shading.uniforms.UniformVec3;

public class FontShader extends Shader {

	private static final String VERTEX_FILE = "/com/seaSaltedToaster/engineResources/shaders/text/fontVertex.glsl";
    private static final String FRAGMENT_FILE = "/com/seaSaltedToaster/engineResources/shaders/text/fontFragment.glsl";
     
    private UniformVec3 color = new UniformVec3("color");
    private UniformFloat alpha = new UniformFloat("alpha");
    
    private UniformVec2 translation = new UniformVec2("translation");
    private UniformVec2 bounds = new UniformVec2("bounds");
     
    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
        super.bindAttributes("position", "textureCoords");
        super.storeAllUniformLocations(color, translation, alpha, bounds);
    }

	public UniformVec3 getColor() {
		return color;
	}
	
	public UniformVec2 getBounds() {
		return bounds;
	}

	public UniformVec2 getTranslation() {
		return translation;
	}

	public UniformFloat getAlpha() {
		return alpha;
	}

}
