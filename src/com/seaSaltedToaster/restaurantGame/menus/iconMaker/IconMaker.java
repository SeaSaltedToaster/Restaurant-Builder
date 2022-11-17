package com.seaSaltedToaster.restaurantGame.menus.iconMaker;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
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
	private Fbo[] fbos;
	private int fboQuality = 100;
	private Camera camera;
	
	//Default icon tint
	private Vector3f primary = new Vector3f(0,1,0);
	private Vector3f secondary = new Vector3f(0,0,1);
	
	//Details
	private float yAngle = 0.0f;
	private Vector3f clear = new Vector3f(0.0f);
	
	public IconMaker(Engine engine) {
		this.shader = new IconShader();
		this.utils = new MatrixUtils();		
		this.camera = new Camera();
		int max_models = 1024;
		this.fbos = new Fbo[max_models]; 
	}
	
	public void prepareFrame() {
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(clear, 0.0f);
	}
	
	public int createIcon(Vao vao, Building building) {
		if(vao == null) return 0;
		
		calculateCameraPosition(building.getIconZoom());
		camera.getPosition().y = -0.5f;
		
		int fboId = vao.id;
		Fbo fbo = getFbo(fboId);
		
		prePrepare(fbo);
		fbo.bindFrameBuffer();
		this.shader.useProgram();
		prepareFrame();
		
		Transform transform = new Transform();
		transform.getRotation().x = 180f;
		transform.getRotation().y = 90 + yAngle;
		
		shader.getPrimaryColor().loadValue(building.getDefPrimary());
		shader.getSecondaryColor().loadValue(building.getDefSecondary());
		
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadValue(transformationMatrix);
		shader.getViewMatrix().loadValue(utils.createViewMatrix(camera));
		shader.getProjectionMatrix().loadValue(utils.createProjectionMatrix(90, 0.1f, 1000f, fboQuality, fboQuality));
		
		vao.render();
		this.shader.stopProgram();
		fbo.unbindFrameBuffer();
		return fbo.getColourTexture();
	}
	
	public int createIcon(Vao vao, float camDist) {
		if(vao == null) return 0;
		calculateCameraPosition(camDist);
		camera.getPosition().y = -0.5f;
		
		int fboId = vao.id;
		Fbo fbo = getFbo(fboId);
		
		prePrepare(fbo);
		fbo.bindFrameBuffer();
		this.shader.useProgram();
		prepareFrame();
		
		Transform transform = new Transform();
		transform.getRotation().x = 180f;
		transform.getRotation().y = 90 + yAngle;
		
		shader.getPrimaryColor().loadValue(primary);
		shader.getSecondaryColor().loadValue(secondary);
		
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadValue(transformationMatrix);
		shader.getViewMatrix().loadValue(utils.createViewMatrix(camera));
		shader.getProjectionMatrix().loadValue(utils.createProjectionMatrix(90, 0.1f, 1000f, fboQuality, fboQuality));
		
		vao.render();
		this.shader.stopProgram();
		fbo.unbindFrameBuffer();
		return fbo.getColourTexture();
	}
	
	private Fbo getFbo(int fboId) {
		if(fbos[fboId] != null) {
			return fbos[fboId];
		}
		Fbo fbo = new Fbo(fboQuality, fboQuality, Fbo.DEPTH_RENDER_BUFFER);
		fbos[fboId] = fbo;
		return fbo;
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

	public Vector3f getClear() {
		return clear;
	}

	public void setClear(Vector3f clear) {
		this.clear = clear;
	}

	public float getYAngle() {
		return yAngle;
	}

	public void setYAngle(float yAngle) {
		this.yAngle = yAngle;
	}
	
	public void increaseYAngle(float amt) {
		setYAngle(this.yAngle + amt);
	}

}
