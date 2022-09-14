package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ShadowRenderer {
	
	private static final int SHADOW_MAP_SIZE = 2048;
	private Engine engine;
	
	private ShadowFbo shadowFbo;
	private ShadowShader shader;
	private ShadowBox shadowBox;
	
	private Matrix4f projectionMatrix = new Matrix4f();
	private Matrix4f lightViewMatrix = new Matrix4f();
	private Matrix4f projectionViewMatrix = new Matrix4f();
	private MatrixUtils utils;
	
	private Matrix4f offset = createOffset();

	private ShadowEntityRenderer entityRenderer;

	public ShadowRenderer(Engine engine) {
		this.engine = engine;
		shader = new ShadowShader();
		shadowBox = new ShadowBox(lightViewMatrix);
		shadowFbo = new ShadowFbo(SHADOW_MAP_SIZE, SHADOW_MAP_SIZE);
		entityRenderer = new ShadowEntityRenderer(shader, projectionMatrix, lightViewMatrix);
		this.utils = new MatrixUtils();
		this.projectionMatrix = utils.createProjectionMatrix(70, 0.1f, 100f, engine);
	}

	public void prepare(Light sun) {
		shadowBox.update(engine.getCamera());
		Vector3f sunPosition = sun.getPosition();
		Vector3f lightDirection = new Vector3f(-sunPosition.x, -sunPosition.y, -sunPosition.z);
		System.out.println(lightDirection.toString());
		
		updateOrthoProjectionMatrix(shadowBox.getWidth(), shadowBox.getHeight(), shadowBox.getLength());
		updateLightViewMatrix(lightDirection, shadowBox.getCenter());
		
		shadowFbo.bindFrameBuffer();
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		shader.useProgram();
	}
	
	public void render(List<Entity> entities) {
		entityRenderer.render(entities, projectionMatrix, lightViewMatrix);
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

	protected Matrix4f getLightSpaceTransform() {
		return lightViewMatrix;
	}
	
	public Matrix4f getToShadowMapSpaceMatrix() {
		Matrix4f.mul(projectionMatrix, lightViewMatrix, projectionViewMatrix);
		return Matrix4f.mul(offset, projectionViewMatrix, null);
	}

	private void finish() {
		shader.stopProgram();
		shadowFbo.unbindFrameBuffer();
	}
	
	private void updateLightViewMatrix(Vector3f direction, Vector3f center) {
		center.negate();
		lightViewMatrix.setIdentity();
		createViewMatrix(direction, direction);
	}
	
	public Matrix4f createViewMatrix(Vector3f pos, Vector3f direction) {
		Matrix4f viewMatrix = new Matrix4f();
	    viewMatrix.setIdentity();
	    Vector3f position = pos;
	    Vector3f cameraPos = new Vector3f(-position.x, -position.y, -position.z);
		float pitch = (float) Math.acos(new Vector2f(direction.x, direction.z).length());
	    Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		float yaw = (float) Math.toDegrees(((float) Math.atan(direction.x / direction.z)));
		yaw = direction.z > 0 ? yaw - 180 : yaw;
	    Matrix4f.rotate((float) -Math.toRadians(yaw), new Vector3f(0,1,0), viewMatrix, viewMatrix);
	    Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
	    return viewMatrix;
	}

	private void updateOrthoProjectionMatrix(float width, float height, float length) {
		projectionMatrix.setIdentity();
		projectionMatrix.m00 = 2f / width;
		projectionMatrix.m11 = 2f / height;
		projectionMatrix.m22 = -2f / length;
		projectionMatrix.m33 = 1;
	}

	private static Matrix4f createOffset() {
		Matrix4f offset = new Matrix4f();
		offset.translate(new Vector3f(0.5f, 0.5f, 0.5f));
		offset.scale(new Vector3f(0.5f, 0.5f, 0.5f));
		return offset;
	}

}
