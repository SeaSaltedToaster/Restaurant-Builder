package com.seaSaltedToaster.restaurantGame.building.renderer;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SelectionRenderer extends Renderer {

	//Framebuffer
	private Fbo fbo;
	private short[] pixels;
	private int selectedId = -127;
	
	//Manager
	private BuildingManager manager;
	private Matrix4f transform;
	
	public SelectionRenderer(BuildingManager manager, Engine engine) {
		super(new SelectionShader(), engine);
		this.manager = manager;
		this.transform = new Matrix4f();
		
		this.fbo = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		this.pixels = new short[3];
	}

	@Override
	public void prepare() {
		prepareFbo();
		this.fbo.bindFrameBuffer();
		super.prepareFrame(true);
	}

	private void prepareFbo() {
		fbo.bindFrameBuffer();
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(new Vector3f(1,0,0), 1);	
		fbo.unbindFrameBuffer();		
	}

	@Override
	public void render(Object obj) {
		float fboIndex = 0;
		for(BuildLayer layer : manager.getLayers()) {
			if(!layer.isOn()) continue;
			for(Entity entity : layer.getBuildings()) {
				if(entity == null) continue;
				Vector3f color = getIdColor(fboIndex);
				renderToFBO(entity, color);
				fboIndex++;
			}
		}
	}
	
	private void renderToFBO(Entity entity, Vector3f color) {
		//Matrices
		GL11.glViewport(0, 0, (int) Window.getWidth(), (int) Window.getHeight());
		Transform transform = entity.getTransform();
		super.loadMatrices(transform);
		shader.loadUniform(color, "objColor");
		
		//Transformation
		this.transform = utils.createTransformationMatrix(this.transform, transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.loadUniform(this.transform, "transformationMatrix");
		
		//Render
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		super.renderVao(comp.getMesh());
		
		
	}

	@Override
	public void endRender() {
		super.endRendering();
		this.fbo.unbindFrameBuffer();
		
		//Get selected objects
		short[] bytes = readPixelColour();
		int id = decodeIdFromColor(bytes) / 255;
		this.selectedId = id;
		Entity selected = manager.getBuilding(id);
		if(selected != null)
			manager.setSelectedEntity(selected);
		else
			manager.setSelectedEntity(null);
	}
	
	private short[] readPixelColour() {
		this.fbo.bindToRead();
		int mouseX = (int) Mouse.getMouseX();
		int mouseY = (int) -Mouse.getMouseY() + (int) Window.getHeight();
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
		return new Vector3f(x/255,y/255,z/255);
	}
		  
	private void encodeIdIntoColor(float id, float[] color) {
		float index = (float) id;
		color[2] = (index % 255);
		index = index / 255;
		if(index < 1) {
			color[1] = 0f;
			color[0] = 0f;
		}
	    color[1] = (index % 255);
		index = index / 255;
		if(index < 1) {
			color[0] = 0f;
		}
	    color[0] = (index % 255);
	}
		  
	private static int decodeIdFromColor(short[] colour) {
		int id = convertUnsignedByte(colour[2]);
	    id += (convertUnsignedByte(colour[1]) * (255)) + 1;
	    id += convertUnsignedByte(colour[0]) * (255 * 255);
	    return id;
	}
	
	private static int convertUnsignedByte(short b) {
		return b & 0xFF;
	}

	public Fbo getFbo() {
		return fbo;
	}

	public int getSelectedId() {
		return selectedId;
	}

}
