package com.seaSaltedToaster.simpleEngine;

import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.input.Keyboard;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.models.wavefront.ObjLoader;
import com.seaSaltedToaster.simpleEngine.renderer.SimpleRenderer;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.SkyboxRenderer;

public class Engine {
	
	//Display
	private Window window;
	private Camera camera;
	
	//Models
	private VaoLoader loader;
	private ObjLoader objLoader;
	
	//Input
	private Mouse mouse;
	private Keyboard keyboard;
	
	//Renderer
	private SimpleRenderer renderer;
	private SkyboxRenderer skybox;
	
	public Engine(String title, int width, int height) {
		this.window = new Window();
		this.window.createWindow(width, height, title);
		this.camera = new Camera();
		
		this.loader = new VaoLoader();
		this.objLoader = new ObjLoader(this);
		
		this.mouse = new Mouse(window);
		this.keyboard = new Keyboard(window);
		
		this.renderer = new SimpleRenderer(this);
		this.skybox = new SkyboxRenderer(this);
	}
	
	public void prepareFrame() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 1);
		skybox.renderSkybox();
	}
	
	public void render(Vao vao, Transform transform) {
		this.renderer.render(vao, transform, this);
	}
	
	public void update() {
		this.camera.update();
		this.window.updateWindow();
	}
	
	public Matrix4f getViewMatrix() {
		return renderer.getViewMatrix();
	}

	public Matrix4f getProjectionMatrix() {
		return renderer.getProjectionMatrix();
	}
	
	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	public Keyboard getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(Keyboard keyboard) {
		this.keyboard = keyboard;
	}

	public ObjLoader getObjLoader() {
		return objLoader;
	}

	public void setObjLoader(ObjLoader objLoader) {
		this.objLoader = objLoader;
	}

	public VaoLoader getLoader() {
		return loader;
	}

	public void setLoader(VaoLoader loader) {
		this.loader = loader;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
