package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import java.util.List;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;

public class ShadowEntityRenderer {
	
	private MatrixUtils utils;
	
	private ShadowShader shader;

	protected ShadowEntityRenderer(ShadowShader shader, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		this.shader = shader;
		this.utils = new MatrixUtils();
	}

	protected void render(List<Entity> entities, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		for (Entity entity : entities) {
			if(!entity.hasComponent("Model")) continue;
			ModelComponent comp = (ModelComponent) entity.getComponent("Model");
			if(comp.getMesh() == null) continue;
			
			prepareInstance(entity, viewMatrix, projectionMatrix);
			comp.getMesh().render();
		}
	}

	private void prepareInstance(Entity entity, Matrix4f viewMatrix, Matrix4f projectionMatrix) {
		Matrix4f transformationMatrix = utils.createTransformationMatrix(entity.getTransform());
		shader.loadUniform(transformationMatrix, "transformationMatrix");
		shader.loadUniform(viewMatrix, "viewMatrix");
		shader.loadUniform(projectionMatrix, "projectionMatrix");
	}

}
