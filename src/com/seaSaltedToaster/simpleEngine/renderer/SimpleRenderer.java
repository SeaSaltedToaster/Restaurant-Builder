package com.seaSaltedToaster.simpleEngine.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;

public class SimpleRenderer {
	
	//Shaders
	private SimpleShader shader;
	
	//Matrices
	private Matrix4f viewMatrix, projectionMatrix;
	private MatrixUtils utils;
	
	public SimpleRenderer(Engine engine) {
		this.shader = new SimpleShader();
		this.utils = new MatrixUtils();
		this.viewMatrix = utils.createViewMatrix(engine.getCamera());
		this.projectionMatrix = utils.createProjectionMatrix(90, 0.1f, 10000f, engine);
	}
	
	public void render(Vao vao, Transform transform, Engine engine) {
		shader.useProgram();
		OpenGL.enableCull();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadMatrix(transformationMatrix);
		this.viewMatrix = utils.createViewMatrix(engine.getCamera());
		shader.getViewMatrix().loadMatrix(viewMatrix);
		this.projectionMatrix = utils.createProjectionMatrix(90, 0.1f, 10000f, engine);
		shader.getProjectionMatrix().loadMatrix(projectionMatrix);
		vao.render();
		OpenGL.disableCull();
		shader.stopProgram();
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
}
