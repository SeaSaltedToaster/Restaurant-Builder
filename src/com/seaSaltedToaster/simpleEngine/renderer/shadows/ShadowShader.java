package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.UniformMatrix4f;

public class ShadowShader extends Shader {
	
	private static final String VERTEX_FILE = "/shaders/shadows/vert.glsl";
	private static final String FRAGMENT_FILE = "/shaders/shadows/frag.glsl";
	
	private UniformMatrix4f transformationMatrix = new UniformMatrix4f("transformationMatrix");
	private UniformMatrix4f lightViewMatrix = new UniformMatrix4f("lightViewMatrix");

	protected ShadowShader() {
		super(VERTEX_FILE, FRAGMENT_FILE, "in_position");
		super.locateUniforms(transformationMatrix, lightViewMatrix);
	}
	
}
