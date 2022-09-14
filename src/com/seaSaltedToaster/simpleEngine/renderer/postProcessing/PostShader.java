package com.seaSaltedToaster.simpleEngine.renderer.postProcessing;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformSampler;

public class PostShader extends Shader {

	private static final String vertexShader = "/shaders/post/vert.glsl";
	private static final String fragmentShader = "/shaders/post/frag.glsl";

	private UniformFloat brightness = new UniformFloat("brightness");
	private UniformFloat contrast = new UniformFloat("contrast");
	
	private UniformSampler colorTexture = new UniformSampler("colorTexture");
	
	
	public PostShader() {
		super(vertexShader, fragmentShader, "position");
		super.locateUniforms(brightness, contrast, colorTexture);
	}

	public UniformFloat getBrightness() {
		return brightness;
	}
	
	public UniformFloat getContrast() {
		return contrast;
	}

	public UniformSampler getColorTexture() {
		return colorTexture;
	}
	
}
