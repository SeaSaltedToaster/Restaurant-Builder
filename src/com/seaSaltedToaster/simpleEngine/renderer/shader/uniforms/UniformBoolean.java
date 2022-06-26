package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform{

	public UniformBoolean(String name){
		super(name);
	}
	
	public void loadBoolean(boolean bool){
		GL20.glUniform1f(super.getLocation(), bool ? 1f : 0f);
	}
	
}
