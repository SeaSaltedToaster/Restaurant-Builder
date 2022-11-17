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
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;

public class UiRenderer extends Renderer {

	//Shading / Rendering
	private Vao vao;
	private Vector2f uiScale = new Vector2f(0,0);
		
	public UiRenderer(Engine engine) {
		super(new UiShader(), engine);
		this.vao = getQuadMesh(engine.getLoader());
	}
		
	public void renderGui(UiComponent component) {
		prepare();
		render(component);
		endRender();
	}
	
	@Override
	public void prepare() {		
		super.prepareFrame(false);
	}
	
	@Override
	public void render(Object obj) {
		//Bind
		vao.bind(0,1,2,3);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				
		//Load UI attribs
		UiComponent component = (UiComponent) obj;
		
		float scale = component.getScaleMultiplier();
		this.uiScale.setX(component.getScale().x * scale);
		this.uiScale.setY(component.getScale().y * scale);
		
		shader.loadUniform(component.getPosition(), "position");
		shader.loadUniform(uiScale, "scale");
		shader.loadUniform(component.getColor(), "color");
		shader.loadUniform(component.getAlpha(), "alpha");
		setScissorTest(component.getClippingBounds());
		if((Integer) component.getTexture() != null && (Integer) component.getTexture() != -1) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, component.getTexture());
		}
		
		//Render
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		
		//Unbind
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		vao.unbind(0,1,2,3);
	}
	
	
	@Override
	public void endRender() {
	    OpenGL.disableScissorTest();
		super.endRendering();
	}
	
	private void setScissorTest(int[] bounds) {
	    if (bounds == null) {
	      OpenGL.disableScissorTest();
	    } else {
	    	OpenGL.enableScissorTest(bounds[0], bounds[1], bounds[2], bounds[3]);
	    } 
	}
	
	public static Vao getQuadMesh(VaoLoader loader) {
		float[] positions = 
		{-1,1, -1, -1, 1, 1, 1, -1};
		Vao vao = loader.loadToVAO(positions, 2);
		return vao;
	}
	
}
