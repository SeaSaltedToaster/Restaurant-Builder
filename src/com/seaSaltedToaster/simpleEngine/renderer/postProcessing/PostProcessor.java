package com.seaSaltedToaster.simpleEngine.renderer.postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.Fbo;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class PostProcessor extends Renderer {

	//Framebuffer
	private Fbo fbo;
	private boolean doProcessing = false;
	
	//Quad
	private final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private Vao quad;
	
	public PostProcessor(Engine engine) {
		super(new PostShader(), engine);
		int fboQuality = 4;
		this.fbo = new Fbo(Window.getWidth()*fboQuality, Window.getHeight()*fboQuality, Fbo.DEPTH_RENDER_BUFFER);
		this.quad = engine.getLoader().loadToVAO(POSITIONS, 2);
	}

	@Override
	public void prepare() {
		if(!doProcessing) return;
		this.fbo.bindFrameBuffer();
		OpenGL.clearColor();
		OpenGL.clearDepth();
	}

	@Override
	public void render(Object obj) {
		if(!doProcessing) return;
		this.fbo.unbindFrameBuffer();
		this.quad.bind(0,1,2,3);
		
		//Uniforms
		super.prepareFrame(false);
		shader.loadUniform(-TimeHandler.BRIGHTNESS / 5f + 0.66f, "brightness");
		shader.loadUniform(1.0f, "contrast");
		
		//Color Tex
		int texture = fbo.getColourTexture();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		this.quad.unbind(0,1,2,3);
	}

	@Override
	public void endRender() {
		if(!doProcessing) return;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		super.endRendering();
	}

}
