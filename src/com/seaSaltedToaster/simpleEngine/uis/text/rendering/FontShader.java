package com.seaSaltedToaster.simpleEngine.uis.text.rendering;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec2;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec3;

public class FontShader extends Shader {

	private static final String VERTEX_FILE = "/shaders/text/fontVertex.glsl";
    private static final String FRAGMENT_FILE = "/shaders/text/fontFragment.glsl";
     
    private UniformVec3 color = new UniformVec3("color");
    private UniformFloat alpha = new UniformFloat("alpha");
    
    private UniformVec2 translation = new UniformVec2("translation");
    private UniformVec2 bounds = new UniformVec2("bounds");
     
    public FontShader() {
        super(VERTEX_FILE, FRAGMENT_FILE, "position", "textureCoords");
        super.locateUniforms(color, translation, alpha, bounds);
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
