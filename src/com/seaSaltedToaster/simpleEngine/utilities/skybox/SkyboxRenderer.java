package com.seaSaltedToaster.simpleEngine.utilities.skybox;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SkyboxRenderer {
	
	//Meshing
	private SkyboxShader shader;
	private Vao skybox;
	
	//Transform
	private final float SIZE = 1000f;
	private final Vector3f SIZE_VEC = new Vector3f(SIZE);
	private Matrix4f transformation = new Matrix4f();
	
	//Engine / Math
	private MatrixUtils utils;
	private Engine engine;
	
	public SkyboxRenderer(Engine engine) {
		this.engine = engine;
		this.utils = new MatrixUtils();
		this.shader = new SkyboxShader();
		this.skybox = engine.getObjLoader().loadObjModel("skybox");
		this.prepareInstance();
	}
	
	public void renderSkybox() {
		beginRendering();
		skybox.render();
		finishRendering();
	}
	
	private void beginRendering() {
		prepareInstance();
		shader.useProgram();
		shader.getSkyboxSize().loadFloat(SIZE);
		shader.getViewMatrix().loadMatrix(engine.getViewMatrix());
		shader.getProjectionMatrix().loadMatrix(engine.getProjectionMatrix());
		shader.getTransformationMatrix().loadMatrix(transformation);
		shader.getTime().loadFloat((float)GLFW.glfwGetTime());
	}

	
	private void finishRendering() {
		shader.stopProgram();
	}
	
	private void prepareInstance() {
		Vector3f cameraPosition = engine.getCamera().getPosition();
		this.transformation = utils.createTransformationMatrix(new Vector3f(cameraPosition.x, 0, cameraPosition.z), 0f, 0f, 0f, SIZE_VEC);
	}

}
