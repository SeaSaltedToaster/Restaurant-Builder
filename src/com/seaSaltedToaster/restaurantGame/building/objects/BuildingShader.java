package com.seaSaltedToaster.restaurantGame.building.objects;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformInteger;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformVec3;

public class BuildingShader extends Shader {
	
	private static final String vertexShader = "/shaders/buildings/vert.glsl";
	private static final String fragmentShader = "/shaders/buildings/frag.glsl";
	
	protected UniformMatrix4f transformation = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");
	
	protected UniformInteger currentId = new UniformInteger("currentId");
	protected UniformInteger selectedId = new UniformInteger("selectedId");
	protected UniformFloat dayValue = new UniformFloat("dayValue");
	
	protected UniformVec3 primaryColor = new UniformVec3("primaryColor");
	protected UniformVec3 secondaryColor = new UniformVec3("secondaryColor");
	
	public BuildingShader() {
		super(vertexShader, fragmentShader, "position", "color", "normal");
		super.locateUniform(transformation);
		super.locateUniform(viewMatrix);
		super.locateUniform(projectionMatrix);
		super.locateUniform(currentId);
		super.locateUniform(selectedId);
		super.locateUniform(dayValue);
		super.locateUniform(primaryColor);
		super.locateUniform(secondaryColor);
	}
	
	public UniformVec3 getPrimaryColor() {
		return primaryColor;
	}

	public UniformVec3 getsecondaryColor() {
		return secondaryColor;
	}

	public UniformFloat getDayValue() {
		return dayValue;
	}

	public UniformInteger getCurrentId() {
		return currentId;
	}

	public UniformInteger getSelectedId() {
		return selectedId;
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
