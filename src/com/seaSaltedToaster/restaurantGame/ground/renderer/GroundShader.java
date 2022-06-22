package com.seaSaltedToaster.restaurantGame.ground.renderer;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;

public class GroundShader extends Shader {

	private static final String vertexShader = "/shaders/map/vert.glsl";
	private static final String fragmentShader = "/shaders/map/frag.glsl";
	
	protected UniformMatrix4f transformation = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");

	protected UniformFloat selected = new UniformFloat("selected");
	
	public GroundShader() {
		super(vertexShader, fragmentShader, "position", "color", "normal", "id");
		super.locateUniform(transformation);
		super.locateUniform(viewMatrix);
		super.locateUniform(projectionMatrix);
		super.locateUniform(selected);
	}
	
	public UniformFloat getSelected() {
		return selected;
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
