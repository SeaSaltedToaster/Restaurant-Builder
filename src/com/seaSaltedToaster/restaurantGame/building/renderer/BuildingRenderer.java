package com.seaSaltedToaster.restaurantGame.building.renderer;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildingRenderer {
	
	//Shading / Objects
	private BuildingShader shader;
	private MatrixUtils utils;
	
	//Buildings
	private Engine engine;
	private BuildingManager manager;
	
	//Framebuffer
	public static Fbo fbo;

	public BuildingRenderer(BuildingManager manager, Engine engine) {
		this.engine = engine;
		this.manager = manager;
		this.shader = new BuildingShader();
		this.utils = new MatrixUtils();
		BuildingRenderer.fbo = new Fbo(Window.getCurrentWidth(), Window.getCurrentHeight(), Fbo.DEPTH_RENDER_BUFFER);
	}
	
	public void prepareFrame() {
		fbo.bindFrameBuffer();
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(new Vector3f(1,0,0), 1);	
		fbo.unbindFrameBuffer();
	}
	
	public void render() {
		//Standard pass
		for(Entity entity : manager.getPlacedBuildings()) {
			renderEntity(entity);
		}
		Building preview = manager.getPreview();
		if(preview != null)
			renderEntity(preview.getEntity());
		
		//FBO Pass
		prepareFrame();
		BuildingRenderer.fbo.bindFrameBuffer();
		for(Entity entity : manager.getPlacedBuildings()) {
			renderEntity(entity);
		}
		if(preview != null)
			renderEntity(preview.getEntity());
		BuildingRenderer.fbo.unbindFrameBuffer();
	}
	
	private void renderEntity(Entity entity) {
		Transform transform = entity.getTransform();
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		shader.useProgram();
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadMatrix(transformationMatrix);
		shader.getViewMatrix().loadMatrix(utils.createViewMatrix(engine.getCamera()));
		shader.getProjectionMatrix().loadMatrix(engine.getProjectionMatrix());
		comp.getMesh().render();
		shader.stopProgram();
	}

}
