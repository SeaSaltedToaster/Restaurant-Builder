package com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms;

import org.lwjgl.opengl.GL20;

public class UniformFloat extends Uniform{
		
	public UniformFloat(String name){
		super(name);
	}
	
	public void loadFloat(float value){
		GL20.glUniform1f(super.getLocation(), value);
	}

}
