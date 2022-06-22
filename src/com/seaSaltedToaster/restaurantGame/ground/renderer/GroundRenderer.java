package com.seaSaltedToaster.restaurantGame.ground.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class GroundRenderer {

	//Position
	public Transform transform = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));
	
	//Shader
	private GroundShader shader;
	private MatrixUtils utils;
	
	//ID
	private int selected;
	
	//Create shader
	public GroundRenderer(Engine engine) {
		this.shader = new GroundShader();
		this.utils = new MatrixUtils();
	}
	
	public void setHighlight(int id) {
		this.selected = id;
		shader.useProgram();
		shader.getSelected().loadFloat(selected);
		shader.stopProgram();
	}
	
	public void render(Vao vao, Engine engine) {
		shader.useProgram();
		OpenGL.enableCull();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadMatrix(transformationMatrix);
		shader.getViewMatrix().loadMatrix(engine.getViewMatrix());
		shader.getProjectionMatrix().loadMatrix(engine.getProjectionMatrix());
		vao.render();
		OpenGL.disableCull();
		shader.stopProgram();
	}
	
}
