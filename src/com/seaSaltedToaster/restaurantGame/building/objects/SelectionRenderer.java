package com.seaSaltedToaster.restaurantGame.building.objects;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
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
	private ByteBuffer pixels;
	private int selectedId = -127;
	
	//Manager
	private BuildingManager manager;
	private Matrix4f transform;
	
	public SelectionRenderer(BuildingManager manager, Engine engine) {
		super(new SelectionShader(), engine);
		this.manager = manager;
		this.transform = new Matrix4f();
		
		this.fbo = new Fbo(Window.getWidth(), Window.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		this.pixels = ByteBuffer.wrap(new byte[16]);
	}

	@Override
	public void prepare() {
		prepareFbo();
		this.fbo.unbindFrameBuffer();
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
		for(BuildLayer layer : manager.getLayers()) {
			if(!layer.isOn()) continue;
			for(Entity entity : layer.getBuildings()) {
				if(entity == null || !entity.hasComponent("BuildingId")) continue;
				BuildingId buildingId = (BuildingId) entity.getComponent("BuildingId");
				Vector3f color = getIdColor(buildingId.getId());
				renderToFBO(entity, color);
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
		byte[] bytes = readPixelColour();
		int id = (int) (decodeIdFromColor(bytes) / 256);
		this.selectedId = id;
		Entity selected = BuildingManager.getBuildingWithID(id);
		if(selected != null && !MainApp.menuFocused)
			manager.setSelectedEntity(selected);
		else
			manager.setSelectedEntity(null);
		System.out.println((selected != null) + " got : " + id);
	}
	
	private byte[] readPixelColour() {
		this.fbo.bindToRead();
		int mouseX = (int) Mouse.getMouseX();
		int mouseY = (int) -Mouse.getMouseY() + (int) Window.getHeight();
		pixels.clear();
		GL11.glReadPixels(mouseX, mouseY, 1, 1, 6408, 5121, pixels);
	    this.fbo.unbindFrameBuffer();
	    byte[] pix = pixels.array();
	    return pix;
	}
	
	  private static int F1 = 256;
	  
	  private static int F2 = F1 * F1;
	
	private Vector3f getIdColor(float index) {
		float[] color = new float[4];
		int id = (int) index;
		if(id == selectedId)
		color[3] = -1;
		color[2] = (byte)(id % F1);
	    id /= F1;
	    color[1] = (byte)(id % F1);
	    id /= F1;
	    color[0] = (byte)(id % F1);
	    
		return new Vector3f(color[0],color[1],color[2]);
	}
		  		  
	private static float decodeIdFromColor(byte[] colour) {
	    int id = convertUnsignedByte(colour[2]);
	    id += convertUnsignedByte(colour[1]) * F1;
	    id += convertUnsignedByte(colour[0]) * F2;
	    	    
	    for(byte val : colour) {
	    	System.out.print(val + ", ");
	    }
	    System.out.println(";");
	    return id;
	}
	
	private static int convertUnsignedByte(byte b) {
		return (int) b & 0xFF;
	}

	public Fbo getFbo() {
		return fbo;
	}

	public int getSelectedId() {
		return selectedId;
	}

}
