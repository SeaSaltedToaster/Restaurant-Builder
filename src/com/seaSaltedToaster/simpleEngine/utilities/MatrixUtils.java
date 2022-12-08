package com.seaSaltedToaster.simpleEngine.utilities;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class MatrixUtils {

	public Matrix4f createTransformationMatrix(Transform transform) {
		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.setIdentity();
		transformationMatrix = transformationMatrix.multiply(Matrix4f.translate(transform.getPosition().x, transform.getPosition().y, transform.getPosition().z));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate((float) Math.toRadians(transform.getRotation().x), 1, 0, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate((float) Math.toRadians(transform.getRotation().y), 0, 1, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate((float) Math.toRadians(transform.getRotation().z), 0, 0, 1));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.scale(transform.getScale().x, transform.getScale().y, transform.getScale().z));
		return transformationMatrix;
	}

	public Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, Vector3f scale) {
		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.setIdentity();
		transformationMatrix = transformationMatrix.multiply(Matrix4f.translate(translation.x, translation.y, translation.z));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rx, 1, 0, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(ry, 0, 1, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rz, 0, 0, 1));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.scale(scale.x, scale.y, scale.z));
		return transformationMatrix;
	}
	
	public Matrix4f createTransformationMatrix(Matrix4f transformationMatrix, Vector3f translation, float rx, float ry, float rz, Vector3f scale) {
		transformationMatrix.setIdentity();
		transformationMatrix = transformationMatrix.multiply(Matrix4f.translate(translation.x, translation.y, translation.z));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rx, 1, 0, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(ry, 0, 1, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rz, 0, 0, 1));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.scale(scale.x, scale.y, scale.z));
		return transformationMatrix;
	}
	
	public Matrix4f createTransformationMatrix(Matrix4f transformationMatrix, Vector2f translation, Vector2f scale) {
		transformationMatrix.setIdentity();
		transformationMatrix = transformationMatrix.multiply(Matrix4f.translate(translation.x, translation.y, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.scale(scale.x, scale.y, 0));
		return transformationMatrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
	    viewMatrix.setIdentity();
	    Vector3f position = camera.getPosition();
	    Vector3f cameraPos = new Vector3f(-position.x, -position.y, -position.z);
	    Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
	    Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
	    Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
	    return viewMatrix;
	}
	
	public Matrix4f createViewMatrix(Vector3f pos, Vector3f rot) {
		Matrix4f viewMatrix = new Matrix4f();
	    viewMatrix.setIdentity();
	    Vector3f position = pos;
	    Vector3f cameraPos = new Vector3f(-position.x, -position.y, -position.z);
	    Matrix4f.rotate((float) Math.toRadians(rot.getX()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
	    Matrix4f.rotate((float) Math.toRadians(rot.getY()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
	    Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
	    return viewMatrix;
	}
	
	public Matrix4f createViewMatrix(Matrix4f viewMatrix, Camera camera) {
	    viewMatrix.setIdentity();
	    Vector3f position = camera.getPosition();
	    Vector3f cameraPos = new Vector3f(-position.x, -position.y, -position.z);
	    Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
	    Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
	    Matrix4f.translate(cameraPos, viewMatrix, viewMatrix);
	    return viewMatrix;
	}
	
	 public Matrix4f createProjectionMatrix(float FOV, float NEAR_PLANE, float FAR_PLANE, Engine engine) {
		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.setIdentity();
		float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
	
	 public Matrix4f createProjectionMatrix(float FOV, float NEAR_PLANE, float FAR_PLANE, float width, float height) {
		Matrix4f projectionMatrix = new Matrix4f();
		projectionMatrix.setIdentity();
		float aspectRatio = (float)  width / (float) height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}

}
