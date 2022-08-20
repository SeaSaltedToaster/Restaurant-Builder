package com.seaSaltedToaster.restaurantGame.ground.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.rendering.Renderer;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class GroundRenderer extends Renderer {
	
	//ID
	private Transform transform;
	private int selected = -1;
	
	//Create shader
	public GroundRenderer(Engine engine) {
		super(new GroundShader(), engine);
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
