package com.seaSaltedToaster.simpleEngine.renderer.shadows;

import java.util.ArrayList;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;

public class ShadowMapRenderer extends Renderer {

	private Fbo fbo;
	
	public ShadowMapRenderer(Engine engine) {
		super(new ShadowMapShader(), engine);
		this.fbo = new Fbo(1000, 1000, Fbo.DEPTH_RENDER_BUFFER);
	}

	@Override
	public void prepare() {
		this.fbo.bindFrameBuffer();
		this.shader.useProgram();
	}

	@Override
	public void render(Object obj) {
		ArrayList<Entity> list = (ArrayList<Entity>) obj;
		for(Entity entity : list) {
			if(entity == null || entity.getModel() == null) continue;
			
			this.shader.loadUniform(engine.getProjectionMatrix(), "projectionMatrix");
			this.shader.loadUniform(engine.getViewMatrix(), "viewMatrix");
			this.shader.loadUniform(utils.createTransformationMatrix(entity.getTransform()), "transformationMatrix");
			
			Vao vao = entity.getModel();
			vao.render();
		}
	}

	@Override
	public void endRender() {
		this.shader.stopProgram();
		this.fbo.unbindFrameBuffer();
	}

	public int getShadowMap() {
		return fbo.getColourTexture();
	}

	public void clear() {
		OpenGL.clearBuffers(1, 0, 0, 1);
	}

}
