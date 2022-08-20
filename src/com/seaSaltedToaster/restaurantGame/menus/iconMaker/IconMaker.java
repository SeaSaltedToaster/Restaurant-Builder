package com.seaSaltedToaster.restaurantGame.menus.iconMaker;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class IconMaker {
	
	//Rendering
	private IconShader shader;
	private MatrixUtils utils;
	
	//FBO
	private int fboQuality = 1280;
	private Camera camera;
	
	public IconMaker(Engine engine) {
		this.shader = new IconShader();
		this.utils = new MatrixUtils();		
		this.camera = new Camera();
	}
	
	public void prepareFrame() {
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(new Vector3f(0.0f), 0.0f);
	}
	
	public int createIcon(Vao vao, float camDist) {
		calculateCameraPosition(camDist);
		camera.getPosition().y = -0.5f;
		
		Fbo fbo = new Fbo(fboQuality, fboQuality, Fbo.DEPTH_RENDER_BUFFER); 
		prePrepare(fbo);
		fbo.bindFrameBuffer();
		this.shader.useProgram();
		prepareFrame();
		Transform transform = new Transform();
		transform.getRotation().x = 180f;
		transform.getRotation().y = 90;
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadValue(transformationMatrix);
		shader.getViewMatrix().loadValue(utils.createViewMatrix(camera));
		shader.getProjectionMatrix().loadValue(utils.createProjectionMatrix(90, 0.1f, 1000f, fboQuality, fboQuality));
		vao.render();
		this.shader.stopProgram();
		fbo.unbindFrameBuffer();
		return fbo.getColourTexture();
	}
	
	private void prePrepare(Fbo fbo) {
		fbo.bindFrameBuffer();
		shader.useProgram();
		prepareFrame();
		shader.stopProgram();
		fbo.unbindFrameBuffer();
	}

	private void calculateCameraPosition(float camDist) {
		float theta = -camera.getYaw(); //angleAroundPlayer;
		float offsetX = (float) (getHorizontalDistance(camDist) * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (getHorizontalDistance(camDist) * Math.cos(Math.toRadians(theta)));
		
		camera.getPosition().x = offsetX;
		camera.getPosition().y = getVerticalDistance(camDist);
		camera.getPosition().z = offsetZ;
	}
	
	private float getHorizontalDistance(float camDist) {
		return (float) (camDist * Math.cos(Math.toRadians(camera.getPitch())));
	}
	
	private float getVerticalDistance(float camDist) {
		return (float) (camDist * Math.sin(Math.toRadians(-camera.getPitch())));
	}

}
