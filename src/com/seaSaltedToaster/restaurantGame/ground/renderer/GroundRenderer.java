package com.seaSaltedToaster.restaurantGame.ground.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class GroundRenderer extends Renderer {
	
	//ID
	private Transform transform;
	private int selected = -1;
	
	//Engine
	private Engine engine;
	
	//Create shader
	public GroundRenderer(Engine engine) {
		super(new GroundShader(), engine);
		this.engine = engine;
		this.transform = new Transform();
	}
	
	public void setHighlight(int id) {
		this.selected = id;
		this.shader.useProgram();
		this.shader.loadUniform(id, "selected");
		this.shader.stopProgram();
	}

	@Override
	public void prepare() {
		super.prepareFrame(false);
	}

	@Override
	public void render(Object obj) {
		shader.loadUniform(TimeHandler.DAY_VALUE, "dayValue");
		this.loadMatrices(transform);
		shader.loadUniform(engine.getShadowRenderer().getToShadowMapSpaceMatrix(), "toShadowMapSpace");
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, engine.getShadowRenderer().getShadowMap());
		shader.loadUniform(engine.getShadowRenderer().getShadowMap(), "shadowMap");
		
		Vao vao = (Vao) obj;
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
