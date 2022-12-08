package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import java.util.List;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;

public class ShadowEntityRenderer {
	
	private MatrixUtils utils;
	private Matrix4f transformMat4;
	
	private ShadowShader shader;

	protected ShadowEntityRenderer(ShadowShader shader, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		this.shader = shader;
		this.utils = new MatrixUtils();
		this.transformMat4 = new Matrix4f();
	}

	protected void render(List<Entity> entities, Matrix4f lightViewMatrix) {
		for (Entity entity : entities) {
			if(!entity.hasComponent("Model")) continue;
			ModelComponent comp = (ModelComponent) entity.getComponent("Model");
			if(comp.getMesh() == null) continue;
			
			prepareInstance(entity, lightViewMatrix);
			comp.getMesh().render();
		}
	}

	private void prepareInstance(Entity entity, Matrix4f lightViewMatrix) {
		Transform transform = entity.getTransform();
		this.transformMat4 = utils.createTransformationMatrix(transformMat4, transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.loadUniform(transformMat4, "transformationMatrix");
		shader.loadUniform(lightViewMatrix, "lightViewMatrix");
	}

}
