package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ShadowRenderer {
	
	private static final int SHADOW_MAP_SIZE = 1024;
	private final int size = 100;
	private Engine engine;
	
	private ShadowFbo shadowFbo;
	private ShadowBox box;
	private ShadowShader shader;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f lightViewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	private MatrixUtils utils;
	
	private Matrix4f offset = createOffset();
	
	private ShadowEntityRenderer entityRenderer;

	public ShadowRenderer(Engine engine) {
		this.engine = engine;
		this.shader = new ShadowShader();
		this.shadowFbo = new ShadowFbo(SHADOW_MAP_SIZE, SHADOW_MAP_SIZE);
		this.entityRenderer = new ShadowEntityRenderer(shader, projectionMatrix, lightViewMatrix);		
		this.utils = new MatrixUtils();
		
		this.box = new ShadowBox(lightViewMatrix, engine.getCamera());
		this.updateOrthoProjectionMatrix(size, size, size);
		this.updateLightViewMatrix(new Vector3f(-25.0f), new Vector3f(0, 0, 0));
		this.projectionViewMatrix = Matrix4f.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
		this.projectionViewMatrix = Matrix4f.mul(offset, projectionViewMatrix, projectionViewMatrix);
	}

	public void prepare(Light sun) {
		box.update();
		
		shadowFbo.bindFrameBuffer();
		OpenGL.setDepthTest(true);
		OpenGL.clearDepth();
		shader.useProgram();
	}
	
	private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
		direction.normalize();
		center.negate();
		lightViewMatrix.setIdentity();
		float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
		Matrix4f.rotate(pitch, new Vector3f(1, 0, 0), lightViewMatrix, lightViewMatrix);
		float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
		yaw = direction.z > 0 ? yaw - 180 : yaw;
		Matrix4f.rotate((float) -Math.toRadians(yaw), new Vector3f(0, 1, 0), lightViewMatrix,
				lightViewMatrix);
		Matrix4f.translate(center, lightViewMatrix, lightViewMatrix);
	}
	
	private void updateOrthoProjectionMatrix(float width, float height, float length) {
		projectionMatrix.setIdentity();
		projectionMatrix.m00 = 2f / width;
		projectionMatrix.m11 = 2f / height;
		projectionMatrix.m22 = -2f / length;
		projectionMatrix.m33 = 1;
	}
	
	public void render(List<Entity> entities) {
		entityRenderer.render(entities, projectionViewMatrix);
	}
	
	public void stopRender() {
		finish();
	}

	public void cleanUp() {
		shadowFbo.cleanUp();
	}

	public int getShadowMap() {
		return shadowFbo.getShadowMap();
	}
	
	public Matrix4f getToShadowMapSpaceMatrix() {
		return projectionViewMatrix;
	}

	private void finish() {
		shader.stopProgram();
		shadowFbo.unbindFrameBuffer();
	}
	
	private static Matrix4f createOffset() {
		Matrix4f offset = new Matrix4f();
		offset.translate(new Vector3f(0.5f));
		offset.scale(new Vector3f(0.5f));
		return offset;
	}

}
