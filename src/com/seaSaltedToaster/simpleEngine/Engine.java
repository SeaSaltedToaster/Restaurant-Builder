package com.seaSaltedToaster.simpleEngine;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.Scene;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.input.Keyboard;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.models.texture.TextureLoader;
import com.seaSaltedToaster.simpleEngine.models.wavefront.ObjLoader;
import com.seaSaltedToaster.simpleEngine.renderer.AdvancedRenderer;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.renderer.postProcessing.PostProcessor;
import com.seaSaltedToaster.simpleEngine.renderer.shadows.ShadowRenderer;
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
	
	//Current scene
	private Scene currentScene;
	
	//Rendering
	private SkyboxRenderer skybox;
	private AdvancedRenderer advRenderer;
	
	//Lighting
	private Light light;
	
	//Graphical FX
	private PostProcessor postProcessor;
	private ShadowRenderer shadowRenderer;
	
	//Matrices
	private Matrix4f viewMatrix, projectionMatrix;
	private MatrixUtils utils;
		
	//Matrix settings
	public static float FOV = 70f;
	public static float NEAR_PLANE = 0.1f;
	public static float FAR_PLANE = 1000f;
	
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
		this.skybox = new SkyboxRenderer(this);
		
		this.light = new Light(new Vector3f(10000, 15000, -10000), new Vector3f(0.0f));
		this.postProcessor = new PostProcessor(this);
		this.shadowRenderer = new ShadowRenderer(this);
		
		this.advRenderer = new AdvancedRenderer(this);
	}
	
	public void renderScene() {
		this.currentScene.renderScene(this);
	}
	
	public void updateScene() {
		this.currentScene.updateScene(this);
	}
	
	private UiComponent createMainUi() {
		UiComponent ui = new UiComponent(0);
		ui.setScale(1f,1f);
		return ui;
	}

	public void prepareFrame() {
		clearFrame();
	}
	
	private void clearFrame() {
		OpenGL.setDepthTest(true);
		OpenGL.clearColor();
		OpenGL.clearDepth();
		OpenGL.clearColor(new Vector3f(0.0f), 0.0f);
	}
	
	public void render() {
		this.viewMatrix = utils.createViewMatrix(camera);
		this.projectionMatrix = utils.createProjectionMatrix(70, 0.1f, 1000f, this);
		
		this.advRenderer.prepare();
		for(Entity entity : this.currentScene.getBatches()) {
			this.advRenderer.render(entity);
		}
		this.advRenderer.endRender();
	}
	
	public void startPostProcess() {
		this.postProcessor.prepare();
		this.skybox.renderSkybox();
	}
	
	public void postProcess() {
		this.postProcessor.render(null);
		this.postProcessor.endRender();
	}
	
	public void renderShadows(List<Entity> others) {
		this.shadowRenderer.prepare(light);
		this.shadowRenderer.render(others);
		this.shadowRenderer.render(entities);
		this.shadowRenderer.stopRender();
	}
	
	public void renderUis() {
		for(UiComponent ui : this.currentScene.getComponents()) {
			ui.updateComponent(this);
			ui.renderUI(this);
		}
	}
	
	public void update() {
		this.camera.update();
		this.window.updateWindow();
	}
	
	public Scene getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(Scene newScene) {
		if(this.currentScene != null)
			this.currentScene.unloadScene(this);
		
		this.currentScene = newScene;
		this.currentScene.loadScene(this);
	}

	public void addEntity(Entity entity) {
		this.currentScene.getBatches().add(entity);
	}
	
	public List<Entity> getEntities() {
		return this.currentScene.getBatches();
	}

	public void addUi(UiComponent ui) {
		this.currentScene.addComponent(ui);
	}
	
	public List<UiComponent> getUis() {
		return uis;
	}

	public PostProcessor getPostProcessor() {
		return postProcessor;
	}

	public ShadowRenderer getShadowRenderer() {
		return shadowRenderer;
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
