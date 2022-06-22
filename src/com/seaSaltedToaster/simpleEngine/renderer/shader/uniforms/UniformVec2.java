package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;

public class UniformVec2 extends Uniform {

	public UniformVec2(String variable) {
		super(variable);
	}
	
	public void loadVec2(float x, float y) {
		GL20.glUniform2f(getLocation(), x, y);
	}
	
	public void loadVec2(Vector2f vec) {
		GL20.glUniform2f(getLocation(), vec.x, vec.x);
	}

}
