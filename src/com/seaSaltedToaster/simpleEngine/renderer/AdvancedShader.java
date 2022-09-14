package com.seaSaltedToaster.simpleEngine.renderer;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;

public class AdvancedShader extends Shader {	
	
	private static final String vertexShader = "/shaders/vert.glsl";
	private static final String fragmentShader = "/shaders/frag.glsl";
		
	protected UniformMatrix4f transformation = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");

	public AdvancedShader() {
		super(vertexShader, fragmentShader, "position", "color", "normal");
		super.locateUniform(transformation);
		super.locateUniform(viewMatrix);
		super.locateUniform(projectionMatrix);
	}
		
	public UniformMatrix4f getTransformation() {
		return transformation;
	}
		
	public UniformMatrix4f getViewMatrix() {
		return viewMatrix;
	}

	public UniformMatrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

}