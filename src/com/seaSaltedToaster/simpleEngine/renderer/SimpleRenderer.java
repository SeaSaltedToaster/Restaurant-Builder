package com.seaSaltedToaster.simpleEngine.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;

public class SimpleRenderer {
	
	//Shaders
	private SimpleShader shader;
	
	//Matrices
	private MatrixUtils utils;
	
	public SimpleRenderer(Engine engine) {
		this.shader = new SimpleShader();
		this.utils = new MatrixUtils();
	}
	
	public void render(Vao vao, Transform transform, Light light, Engine engine) {
		if(vao == null) return;
		shader.useProgram();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadValue(transformationMatrix);
		shader.getViewMatrix().loadValue(engine.getViewMatrix());
		shader.getProjectionMatrix().loadValue(engine.getProjectionMatrix());
		vao.render();
		OpenGL.disableCull();
		shader.stopProgram();
	}
	
}
