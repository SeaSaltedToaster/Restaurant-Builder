package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class UniformVec3 extends Uniform {

	public UniformVec3(String variable) {
		super(variable);
	}
	
	@Override
	public void loadValue(Object value) {
		Vector3f vec = (Vector3f) value;
		loadVec3(vec.x, vec.y, vec.z);
	}
	
	public void loadVec3(float x, float y, float z) {
		GL20.glUniform3f(getLocation(), x, y, z);
	}
	
}
