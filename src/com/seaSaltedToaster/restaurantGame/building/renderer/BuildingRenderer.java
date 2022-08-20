package com.seaSaltedToaster.restaurantGame.building.renderer;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.restaurantGame.building.AdvancedBuilder;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class BuildingRenderer {
	
	//Shading / Objects
	private BuildingShader shader;
	private MatrixUtils utils;
	private Matrix4f transform, view;
	
	//Buildings
	private Engine engine;
	private BuildingManager manager;
	
	//Framebuffer
	private Fbo fbo;
	private SelectionShader fboShader;
	private short[] pixels;
	private int selectedId = -127;

	public BuildingRenderer(BuildingManager manager, Engine engine) {
		this.engine = engine;
		this.manager = manager;
		this.shader = new BuildingShader();
		this.utils = new MatrixUtils();
		this.fbo = new Fbo(Window.getCurrentWidth(), Window.getCurrentHeight(), Fbo.DEPTH_RENDER_BUFFER);
		this.fboShader = new SelectionShader();
		this.pixels = new short[3];
		this.transform = new Matrix4f();
		this.view = new Matrix4f();
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
		shader.useProgram();
		int index = 0;
		for(BuildLayer layer : manager.getLayers()) {
			layer.updateLayer();
			if(!layer.isOn()) {
				continue;
			}
			for(Entity entity : layer.getBuildings()) {
				entity.updateComponents();
			}
			layer.getBuildings().remove(null);
			for(Entity entity : layer.getBuildings()) {
				if(entity == null) continue;
				renderEntity(entity, index, selectedId);
				index++;
			}
		}
		AdvancedBuilder builder = manager.getBuilder();
		for(Entity entity : builder.getPreviews()) {
			if(entity != null)
				renderEntity(entity, -1, selectedId);
		}
		renderEntity(builder.getStart(), -1, selectedId);
		renderEntity(builder.getEnd(), -1, selectedId);
		shader.stopProgram();
		
		//FBO Pass
		prepareFrame();
		this.fbo.bindFrameBuffer();
		fboShader.useProgram();
		float fboIndex = 0;
		for(BuildLayer layer : manager.getLayers()) {
			if(!layer.isOn()) continue;
			for(Entity entity : layer.getBuildings()) {
				if(entity == null) continue;
				Vector3f color = getIdColor(fboIndex);
				renderEntityForFBO(entity, color);
				fboIndex++;
			}
		}
		fboShader.stopProgram();
		this.fbo.unbindFrameBuffer();
		
		//Get selected objects
		short[] bytes = readPixelColour();
		int id = decodeIdFromColor(bytes) / 256;
		this.selectedId = id;
		Entity selected = manager.getBuilding(id);
		if(selected != null)
			manager.setSelectedEntity(selected);
		else
			manager.setSelectedEntity(null);
	}
		
	private void renderEntityForFBO(Entity entity, Vector3f color) {
		GL11.glViewport(0, 0, (int) Window.getCurrentWidth(), (int) Window.getCurrentHeight());
		Transform transform = entity.getTransform();
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		
		this.transform = utils.createTransformationMatrix(this.transform, transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		fboShader.getTransformation().loadValue(this.transform);
		this.view = utils.createViewMatrix(this.view, engine.getCamera());
		fboShader.getViewMatrix().loadValue(this.view);
		fboShader.getProjectionMatrix().loadValue(engine.getProjectionMatrix());
		
		fboShader.getColor().loadValue(color);
		comp.getMesh().render();
	}

	private void renderEntity(Entity entity, int index, int curId) {
		Transform transform = entity.getTransform();
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		this.transform = utils.createTransformationMatrix(this.transform, transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadValue(this.transform);
		this.view = utils.createViewMatrix(this.view, engine.getCamera());
		shader.getViewMatrix().loadValue(this.view);
		shader.getProjectionMatrix().loadValue(engine.getProjectionMatrix());
		
		shader.getCurrentId().loadValue(index);
		shader.getDayValue().loadValue(TimeHandler.DAY_VALUE);
		if(!manager.isBuilding())
			shader.getSelectedId().loadValue(curId);
		else
			shader.getSelectedId().loadValue(-127);
		comp.getMesh().render();
	}
	
	private short[] readPixelColour() {
		this.fbo.bindToRead();
		int mouseX = (int) Mouse.getMouseX();
		int mouseY = (int) -Mouse.getMouseY() + (int) Window.getCurrentHeight();
		GL11.glReadPixels(mouseX, mouseY, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
	    this.fbo.unbindFrameBuffer();
	    return pixels;
	}
	
	private Vector3f getIdColor(float index) {
		float[] color = new float[16];
		encodeIdIntoColor(index, color);
		float x = color[0];
		float y = color[1];
		float z = color[2];
		return new Vector3f(x/256,y/256,z/256);
	}
		  
	private void encodeIdIntoColor(float id, float[] color) {
		float index = (float) id;
		color[2] = (index % 256);
		index = index / 256;
		if(index < 1) {
			color[1] = 0f;
			color[0] = 0f;
		}
	    color[1] = (index % 256);
		index = index / 256;
		if(index < 1) {
			color[0] = 0f;
		}
	    color[0] = (index % 256);
	}
		  
	private static int decodeIdFromColor(short[] colour) {
		int id = convertUnsignedByte(colour[2]);
	    id += (convertUnsignedByte(colour[1]) * (256)) + 1;
	    id += convertUnsignedByte(colour[0]) * (256 * 256);
	    return id;
	}
	
	private static int convertUnsignedByte(short b) {
		return b & 0xFF;
	}

}
