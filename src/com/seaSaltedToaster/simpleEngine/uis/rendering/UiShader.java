package com.seaSaltedToaster.simpleEngine.uis.rendering;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformSampler;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec2;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec3;

public class UiShader extends Shader {

	protected UniformVec3 color = new UniformVec3("color");
	
	protected UniformVec2 position = new UniformVec2("position");
	protected UniformVec2 scale = new UniformVec2("scale");

	protected UniformSampler guiTexture = new UniformSampler("guiTexture");
	
	protected UniformFloat alpha = new UniformFloat("alpha");
	protected UniformFloat width = new UniformFloat("uiWidth");
	protected UniformFloat height = new UniformFloat("uiHeight");
	
	public UiShader() {
		super("/shaders/ui/vert.glsl", "/shaders/ui/frag.glsl", "in_position");
		super.locateUniforms(position, scale, guiTexture, alpha, width, height, color); 
	}

	public UniformVec2 getPosition() {
		return position;
	}

	public UniformVec2 getScale() {
		return scale;
	}

	public UniformSampler getGuiTexture() {
		return guiTexture;
	}
	
	public UniformFloat getAlpha() {
		return alpha;
	}
	
	public UniformFloat getWidth() {
		return width;
	}
	
	public UniformFloat getHeight() {
		return height;
	}

	public UniformVec3 getColor() {
		return color;
	}
	
}
