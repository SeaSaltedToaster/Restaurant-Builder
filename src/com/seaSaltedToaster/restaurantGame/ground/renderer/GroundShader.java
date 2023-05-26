package com.seaSaltedToaster.restaurantGame.ground.renderer;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformInteger;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformSampler;

public class GroundShader extends Shader {

	private static final String vertexShader = "/shaders/map/vert.glsl";
	private static final String fragmentShader = "/shaders/map/frag.glsl";
	
	protected UniformMatrix4f transformation = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");
	
	protected UniformMatrix4f lightViewMatrix = new UniformMatrix4f("lightViewMatrix");
	protected UniformSampler shadowMap = new UniformSampler("shadowMap");
	
	protected UniformInteger selected = new UniformInteger("selected");
	protected UniformFloat dayValue = new UniformFloat("dayValue");
	
	public GroundShader() {
		super(vertexShader, fragmentShader, "position", "color", "normal", "id");
		super.locateUniform(transformation);
		super.locateUniform(viewMatrix);
		super.locateUniform(projectionMatrix);
		super.locateUniform(selected);
		super.locateUniform(dayValue);
		
		super.locateUniform(lightViewMatrix);		
		super.locateUniform(shadowMap);
	}

	public UniformSampler getShadowMap() {
		return shadowMap;
	}

	public UniformMatrix4f getToShadowMapSpace() {
		return lightViewMatrix;
	}

	public UniformFloat getDayValue() {
		return dayValue;
	}

	public UniformInteger getSelected() {
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
