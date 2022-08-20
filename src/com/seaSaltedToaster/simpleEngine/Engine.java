package com.seaSaltedToaster.simpleEngine;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.input.Keyboard;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.models.texture.TextureLoader;
import com.seaSaltedToaster.simpleEngine.models.wavefront.ObjLoader;
import com.seaSaltedToaster.simpleEngine.renderer.SimpleRenderer;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.rendering.AdvancedRenderer;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.rendering.UiRenderer;
import com.seaSaltedToaster.simpleEngine.uis.text.rendering.FontRenderer;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.SkyboxRenderer;

public class Engine {
	
	//Display
	private Window window;
	private Camera camera;
	
	//Models
	private VaoLoader loader;
	private ObjLoader objLoader;
	private TextureLoader texLoader;
	
	//Input
	private Mouse mouse;
	private Keyboard keyboard;
	
	//UIs
	private List<UiComponent> uis;
	private UiComponent mainParent;
	private UiRenderer uiRenderer;
	private FontRenderer fontRenderer;
	
	//Entities
	private List<Entity> entities;
	
	//Rendering
	private SkyboxRenderer skybox;
	private AdvancedRenderer advRenderer;
	
	//Lighting
	private Light light;
	
	//Matrices
	private Matrix4f viewMatrix, projectionMatrix;
	private MatrixUtils utils;
		
	//Matrix settings
	public static float FOV = 70f;
	public static float NEAR_PLANE = 0.1f;
	public static float FAR_PLANE = 100000f;
	
	public Engine(String title, int width, int height) {
		this.window = new Window();
		this.window.createWindow(width, height, title);
		this.camera = new Camera();
		
		this.loader = new VaoLoader();
		this.objLoader = new ObjLoader(this);
		this.texLoader = new TextureLoader();
		
		this.mouse = new Mouse(window);
		this.keyboard = new Keyboard(window);
		
		this.uis = new ArrayList<UiComponent>();
		this.mainParent = createMainUi();
		this.uiRenderer = new UiRenderer(this);
		this.fontRenderer = new FontRenderer(this);
		
		this.entities = new ArrayList<Entity>();
		
		this.utils = new MatrixUtils();
		this.viewMatrix = utils.createViewMatrix(camera);
		this.projectionMatrix = utils.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE, this);
		new SimpleRenderer(this);
		this.skybox = new SkyboxRenderer(this);
		
		this.light = new Light(new Vector3f(0.0f), new Vector3f(0.0f));
		this.advRenderer = new AdvancedRenderer(this);
	}
	
	private UiComponent createMainUi() {
		UiComponent ui = new UiComponent(0);
		ui.setScale(1f,1f);
		return ui;
	}

	public void prepareFrame() {
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(new Vector3f(1.0f), 0.0f);		
		skybox.renderSkybox();
	}
	
	public void render() {
		this.viewMatrix = utils.createViewMatrix(camera);
		this.projectionMatrix = utils.createProjectionMatrix(70, 0.1f, 100000f, this);
		
		this.advRenderer.prepare();
		for(Entity entity : entities) {
			this.advRenderer.render(entity);
		}
		this.advRenderer.endRender();
	}
	
	public void renderUis() {
		for(UiComponent ui : uis) {
			ui.updateComponent(this);
			ui.renderUI(this);
		}
	}
	
	public void update() {
		this.camera.update();
		this.window.updateWindow();
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	public void addUi(UiComponent ui) {
		this.mainParent.addComponent(ui);
		this.uis.add(ui);
	}
	
	public List<UiComponent> getUis() {
		return uis;
	}

	public Light getLight() {
		return light;
	}

	public Matrix4f getViewMatrix() {
		return this.viewMatrix;
	}

	public Matrix4f getProjectionMatrix() {
		return this.projectionMatrix;
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

	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}

	public UiRenderer getUiRenderer() {
		return uiRenderer;
	}

	public TextureLoader getTextureLoader() {
		return texLoader;
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
