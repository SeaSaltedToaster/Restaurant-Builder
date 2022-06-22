package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class UniformVec3 extends Uniform {

	public UniformVec3(String variable) {
		super(variable);
	}
	
	public void loadVec3(float x, float y, float z) {
		GL20.glUniform3f(getLocation(), x, y, z);
	}
	
	public void loadVec3(Vector3f vec) {
		GL20.glUniform3f(getLocation(), vec.x, vec.y, vec.z);
	}

}
