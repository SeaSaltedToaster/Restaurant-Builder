package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformInteger extends Uniform {

	public UniformInteger(String name){
		super(name);
	}
	
	public void loadInt(int value){
		GL20.glUniform1i(super.getLocation(), value);
	}
	
}
