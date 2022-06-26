package com.seaSaltedToaster.restaurantGame.building.renderer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.restaurantGame.building.Building;
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

public class BuildingRenderer {
	
	//Shading / Objects
	private BuildingShader shader;
	private MatrixUtils utils;
	
	//Buildings
	private Engine engine;
	private BuildingManager manager;
	
	//Framebuffer
	private Fbo fbo;
	private SelectionShader fboShader;
	private short[] fboPixels;
	private int selectedId;

	public BuildingRenderer(BuildingManager manager, Engine engine) {
		this.engine = engine;
		this.manager = manager;
		this.shader = new BuildingShader();
		this.utils = new MatrixUtils();
		this.fbo = new Fbo(Window.getCurrentWidth(), Window.getCurrentHeight(), Fbo.DEPTH_RENDER_BUFFER);
		this.fboShader = new SelectionShader();
		this.fboPixels = new short[16];
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
			if(!layer.isOn()) continue;
			for(Entity entity : layer.getBuildings()) {
				renderEntity(entity, index, selectedId);
				index++;
			}
		}
		Building preview = manager.getPreview();
		if(preview != null) {
			renderEntity(preview.getEntity(), -1, selectedId);
		}
		shader.stopProgram();
		
		//FBO Pass
		prepareFrame();
		this.fbo.bindFrameBuffer();
		fboShader.useProgram();
		float fboIndex = 0;
		for(BuildLayer layer : manager.getLayers()) {
			if(!layer.isOn()) continue;
			for(Entity entity : layer.getBuildings()) {
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
		System.out.println(id);
		this.selectedId = id;
		Entity selected = manager.getBuilding(id);
		if(selected != null)
			manager.setSelectedEntity(selected);
		else
			manager.setSelectedEntity(null);
	}
		
	private void renderEntityForFBO(Entity entity, Vector3f color) {
		Transform transform = entity.getTransform();
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		fboShader.getTransformation().loadMatrix(transformationMatrix);
		fboShader.getViewMatrix().loadMatrix(utils.createViewMatrix(engine.getCamera()));
		fboShader.getProjectionMatrix().loadMatrix(engine.getProjectionMatrix());
		fboShader.getColor().loadVec3(color);
		comp.getMesh().render();
	}

	private void renderEntity(Entity entity, int index, int curId) {
		Transform transform = entity.getTransform();
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.getTransformation().loadMatrix(transformationMatrix);
		shader.getViewMatrix().loadMatrix(utils.createViewMatrix(engine.getCamera()));
		shader.getProjectionMatrix().loadMatrix(engine.getProjectionMatrix());
		shader.getCurrentId().loadInt(index);
		shader.getSelectedId().loadInt(curId);
		comp.getMesh().render();
	}
	
	private short[] readPixelColour() {
		this.fbo.bindToRead();
		int mouseX = (int) Mouse.getMouseX();
		int mouseY = (int) -Mouse.getMouseY() + (int) Window.getCurrentHeight();
		GL11.glReadPixels(mouseX, mouseY, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, fboPixels);
	    this.fbo.unbindFrameBuffer();
	    return fboPixels;
	}
	
	private Vector3f getIdColor(float index) {
		byte[] color = new byte[16];
		encodeIdIntoColor(index, color);
		float x = color[0];
		float y = color[1];
		float z = color[2];
		return new Vector3f(x/256,y/256,z/256);
	}
		  
	private void encodeIdIntoColor(float id, byte[] color) {
		float index = id;
		color[2] = (byte)(index % 256);
		index /= 256;
	    color[1] = (byte)(index % 256);
	    index /= 256;
	    color[0] = (byte)(index % 256);
	}
		  
	private static int decodeIdFromColor(short[] colour) {
		int id = convertUnsignedByte(colour[2]);
	    id += convertUnsignedByte(colour[1]) * (256);
	    id += convertUnsignedByte(colour[0]) * (256 * 256);
	    return id;
	}
	
	private static int convertUnsignedByte(short b) {
		return b & 0xFF;
	}

}
