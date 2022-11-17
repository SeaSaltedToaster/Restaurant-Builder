package com.seaSaltedToaster.simpleEngine.renderer.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.seaSaltedToaster.simpleEngine.renderer.shader.uniforms.Uniform;

public class Shader {
	
	//IDs
	private int programID;
	private int vertexShader, fragmentShader;
	
	//Uniforms
	private List<Uniform> uniforms;
	
	public Shader(String vertexFile, String fragmentFile, String... inVariables) {
		this.vertexShader = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		this.fragmentShader = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		this.programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShader);
		GL20.glAttachShader(programID, fragmentShader);
		for(int i = 0; i < inVariables.length; i++){
			GL20.glBindAttribLocation(programID, i, inVariables[i]);
		}
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		this.uniforms = new ArrayList<Uniform>();
	}
	
	public void loadUniform(Object value, String name) {
		for(Uniform uniform : uniforms) {
			if(uniform.getVariable().equalsIgnoreCase(name)) {
				uniform.loadValue(value);
				return;
			}
		}
	}
	
	public void locateUniforms(Uniform... uniforms) {
		for(Uniform uniform : uniforms) {
			uniform.getUniformLocation(programID);
			this.uniforms.add(uniform);
		}
	}
	
	public void locateUniform(Uniform uniform) {
		uniform.getUniformLocation(programID);
		this.uniforms.add(uniform);
	}
	
	public void useProgram() {
		GL20.glUseProgram(programID);
	}

	public void stopProgram() {
		GL20.glUseProgram(0);
	}

	public void delete() {
		stopProgram();
		GL20.glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type) {
		System.out.println("Loading shader " + file);
		StringBuilder shaderSource = new StringBuilder();
		
		try{
			InputStream in = Shader.class.getResourceAsStream(file);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(isr);
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader!");
			System.exit(-1);
		}
		
		return shaderID;
	}

}
