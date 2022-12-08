package com.seaSaltedToaster.restaurantGame.ground.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.renderer.shadows.ShadowRenderer;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class GroundRenderer extends Renderer {
	
	//ID
	private Transform transform;
	private static GroundShader shader;
	private int selected = -1;
	
	//Engine
	private Engine engine;
	private Ground ground;
	
	//Create shader
	public GroundRenderer(Ground ground, Engine engine) {
		super(shader = new GroundShader(), engine);
		this.engine = engine;
		this.ground = ground;
		this.transform = new Transform();
	}
	
	public void setHighlight(int id) {
		this.selected = id;
		GroundRenderer.shader.useProgram();
		GroundRenderer.shader.loadUniform(id, "selected");
		GroundRenderer.shader.stopProgram();
	}

	@Override
	public void prepare() {
		super.prepareFrame(false);
	}

	@Override
	public void render(Object obj) {
		shader.loadUniform(TimeHandler.DAY_VALUE, "dayValue");
		this.loadMatrices(transform);
		
		ShadowRenderer renderer = engine.getShadowRenderer();
		shader.getToShadowMapSpace().loadValue(renderer.getToShadowMapSpaceMatrix());
		
		int map = renderer.getShadowMap();
	    GL13.glActiveTexture(GL13.GL_TEXTURE0);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, map);
	    
		Vao vao = ground.getMesh();
		super.renderVao(vao);
	}

	@Override
	public void endRender() {
		super.endRendering();
	}

	public int getSelected() {
		return selected;
	}
	
}
