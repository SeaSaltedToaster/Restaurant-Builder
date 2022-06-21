package com.seaSaltedToaster.restaurantGame.ground.renderer;

import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;

public class GroundRenderer {

	//Shader
	private GroundShader shader;
	private Transform groundPosition;
	private MatrixUtils utils;
	
	//Create shader
	public GroundRenderer(Engine engine) {
		this.shader = new GroundShader();
		this.groundPosition = new Transform();
		this.utils = new MatrixUtils();
	}
	
	public void render(Ground ground, Camera camera) {
		//Regular pass
		Vao vao = ground.getMesh();
		shader.useProgram();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(groundPosition.getPosition(), groundPosition.getRotation().x, groundPosition.getRotation().y, groundPosition.getRotation().z, groundPosition.getScale());
		shader.getTransformationMatrix().loadMatrix(transformationMatrix);
		Matrix4f viewMatrix = utils.createViewMatrix(camera);
		shader.getViewMatrix().loadMatrix(viewMatrix);
		Matrix4f projectionMatrix = utils.createProjectionMatrix(90, 0.1f, 10000f);
		shader.getProjectionMatrix().loadMatrix(projectionMatrix);
		vao.render();
		shader.stopProgram();
	}
	
}
