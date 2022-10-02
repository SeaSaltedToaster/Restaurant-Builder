package com.seaSaltedToaster.restaurantGame.menus.iconMaker;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec3;

public class IconShader extends Shader {

	private static final String vertexShader = "/shaders/icon/vert.glsl";
	private static final String fragmentShader = "/shaders/icon/frag.glsl";
	
	protected UniformMatrix4f transformation = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");
	
	protected UniformVec3 primaryColor = new UniformVec3("primaryColor");
	protected UniformVec3 secondaryColor = new UniformVec3("secondaryColor");
	
	public IconShader() {
		super(vertexShader, fragmentShader, "position", "color", "normal");
		super.locateUniform(transformation);
		super.locateUniform(viewMatrix);
		super.locateUniform(projectionMatrix);
		super.locateUniform(primaryColor);
		super.locateUniform(secondaryColor);
	}

	public UniformVec3 getPrimaryColor() {
		return primaryColor;
	}

	public UniformVec3 getSecondaryColor() {
		return secondaryColor;
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
