package com.seaSaltedToaster.simpleEngine.utilities.skybox;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;

public class SkyboxRenderer extends Renderer {
	
	//Meshing
	private Vao skybox;
	
	//Daytime
	private TimeHandler timing;
		
	//Transform
	private Transform transform = new Transform();
	private float size = 250f;
	
	public SkyboxRenderer(Engine engine) {
		super(new SkyboxShader(), engine);
		this.skybox = engine.getObjLoader().loadObjModel("models/skybox");
		this.timing = new TimeHandler();
		
		this.transform = new Transform();
		this.transform.setScale(size);
	}
	
	public void renderSkybox() {
		//Update time
		timing.updateTime();
		
		//Render
		this.prepare();
		this.render(skybox);
		this.endRender();
	}
	
	@Override
	public void prepare() {
		super.prepareFrame(false);
	}

	@Override
	public void render(Object obj) {
		//Load matrices
		super.loadMatrices(transform);
		shader.loadUniform(size, "skyboxSize");
		shader.loadUniform(TimeHandler.DAY_VALUE, "dayValue");
		
		//Render
		Vao vao = (Vao) obj;
		super.renderVao(vao);
	}

	@Override
	public void endRender() {
		super.endRendering();
	}

}
