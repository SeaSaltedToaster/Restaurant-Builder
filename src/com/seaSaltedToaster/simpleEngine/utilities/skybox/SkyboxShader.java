package com.seaSaltedToaster.simpleEngine.utilities.skybox;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformFloat;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;

public class SkyboxShader extends Shader {
	
	private static final String VERTEX_FILE = "/shaders/skybox/vert.glsl";
	private static final String FRAGMENT_FILE = "/shaders/skybox/frag.glsl";
	
	protected UniformMatrix4f viewMatrix = new UniformMatrix4f("viewMatrix");
	protected UniformMatrix4f transformationMatrix = new UniformMatrix4f("transformationMatrix");
	protected UniformMatrix4f projectionMatrix = new UniformMatrix4f("projectionMatrix");

	protected UniformFloat skyboxSize = new UniformFloat("skyboxSize");
	protected UniformFloat dayValue = new UniformFloat("dayValue");
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, "in_position");
		super.locateUniforms(transformationMatrix, projectionMatrix, viewMatrix, skyboxSize, dayValue);
	}

	public static String getVertexFile() {
		return VERTEX_FILE;
	}

	public static String getFragmentFile() {
		return FRAGMENT_FILE;
	}

	public UniformMatrix4f getTransformationMatrix() {
		return transformationMatrix;
	}

	public UniformMatrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public UniformMatrix4f getViewMatrix() {
		return viewMatrix;
	}

	public UniformFloat getSkyboxSize() {
		return skyboxSize;
	}

	public UniformFloat getDayValue() {
		return dayValue;
	}


}
