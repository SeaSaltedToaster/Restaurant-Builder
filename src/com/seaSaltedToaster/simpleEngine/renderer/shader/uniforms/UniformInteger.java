package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformInteger extends Uniform {

	public UniformInteger(String name){
		super(name);
	}
	
	@Override
	public void loadValue(Object value){
		int num = (int) value;
		GL20.glUniform1i(super.getLocation(), num);
	}
	
}
