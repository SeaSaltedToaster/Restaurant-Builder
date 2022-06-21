package com.seaSaltedToaster.simpleEngine.renderer;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.simpleEngine.entity.Camera;
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
	
	public SimpleRenderer(Camera camera) {
		this.shader = new SimpleShader();
		this.utils = new MatrixUtils();
		this.viewMatrix = utils.createViewMatrix(camera);
		this.projectionMatrix = utils.createProjectionMatrix(90, 0.1f, 10000f);
	}
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 1);
	}

	public void render(Vao vao, Transform transform, Camera camera) {
		shader.useProgram();
		OpenGL.enableCull();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadMatrix(transformationMatrix);
		this.viewMatrix = utils.createViewMatrix(camera);
		shader.getViewMatrix().loadMatrix(viewMatrix);
		this.projectionMatrix = utils.createProjectionMatrix(90, 0.1f, 10000f);
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
