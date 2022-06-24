package com.seaSaltedToaster.simpleEngine.uis.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;

public class UiRenderer {

	//Shading / Rendering
	private UiShader shader;
	private Vao vao;
	
	//Matrices
	private MatrixUtils utils;
	private Matrix4f transformation;
	
	public UiRenderer(VaoLoader loader) {
		this.shader = new UiShader();
		this.vao = getQuadMesh(loader);
		this.utils = new MatrixUtils();
		this.transformation = new Matrix4f();
	}
	
	public static Vao getQuadMesh(VaoLoader loader) {
		float[] positions = 
		{-1,1, -1, -1, 1, 1, 1, -1};
		Vao vao = loader.loadToVAO(positions, 2);
		return vao;
	}
	
	public void renderGui(UiComponent component) {
		begin();
		loadShaderVariables(component);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		end();
	}
	
	public void begin() {
		shader.useProgram();
		vao.bind(0,1,2,3,4);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
		
	public void end() {
		vao.unbind(0,1,2,3,4);
		shader.stopProgram();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	private void loadShaderVariables(UiComponent component) {
		Matrix4f transformationMatrix = getTransformation(component);
		shader.getTransformationMatrix().loadMatrix(transformationMatrix);
		shader.getColor().loadVec3(component.getColor());
		shader.getAlpha().loadFloat(component.getAlpha());
		
		if((Integer) component.getTexture() != null && (Integer) component.getTexture() != -1) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, component.getTexture());
		}
	}

	private Matrix4f getTransformation(UiComponent component) {
		this.transformation = utils.createTransformationMatrix(transformation, component.getPosition(), component.getScale());
		return transformation;
	}
	
}
