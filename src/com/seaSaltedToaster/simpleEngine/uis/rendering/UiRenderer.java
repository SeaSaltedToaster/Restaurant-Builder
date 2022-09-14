package com.seaSaltedToaster.simpleEngine.uis.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;

public class UiRenderer extends Renderer {

	//Shading / Rendering
	private Vao vao;
	
	//Matrices
	private Matrix4f transformation;
	
	public UiRenderer(Engine engine) {
		super(new UiShader(), engine);
		this.vao = getQuadMesh(engine.getLoader());
		this.transformation = new Matrix4f();
	}
		
	public void renderGui(UiComponent component) {
		prepare();
		render(component);
		endRender();
	}
	
	@Override
	public void prepare() {		
		super.prepareFrame(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	@Override
	public void render(Object obj) {
		//Load UI attribs
		UiComponent component = (UiComponent) obj;
		Matrix4f transformationMatrix = getTransformation(component);
		shader.loadUniform(transformationMatrix, "transformationMatrix");
		shader.loadUniform(component.getColor(), "color");
		shader.loadUniform(component.getAlpha(), "alpha");
		setScissorTest(component.getClippingBounds());
		if((Integer) component.getTexture() != null && (Integer) component.getTexture() != -1) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, component.getTexture());
		}
		
		//Render
		vao.bind(0,1,2,3);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		vao.unbind(0,1,2,3);
	}
	
	private void setScissorTest(int[] bounds) {
	    if (bounds == null) {
	      OpenGL.disableScissorTest();
	    } else {
	    	OpenGL.enableScissorTest(bounds[0], bounds[1], bounds[2], bounds[3]);
	    } 
	}
	
	private Matrix4f getTransformation(UiComponent component) {
		this.transformation = utils.createTransformationMatrix(transformation, component.getPosition(), component.getScale());
		return transformation;
	}
	
	@Override
	public void endRender() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	    GL11.glDisable(GL11.GL_SCISSOR_TEST);
		super.endRendering();
	}
	
	public static Vao getQuadMesh(VaoLoader loader) {
		float[] positions = 
		{-1,1, -1, -1, 1, 1, 1, -1};
		Vao vao = loader.loadToVAO(positions, 2);
		return vao;
	}
	
}
