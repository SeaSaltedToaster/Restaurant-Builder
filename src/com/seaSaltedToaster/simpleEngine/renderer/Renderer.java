package com.seaSaltedToaster.simpleEngine.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.shader.Shader;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.MatrixUtils;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public abstract class Renderer {
	
	//Shading
	protected Shader shader;
	
	//Engine
	protected Engine engine;
	protected MatrixUtils utils;

	public Renderer(Shader shader, Engine engine) {
		this.shader = shader;
		this.engine = engine;
		this.utils = new MatrixUtils();
	}
	
	//Preparing frame
	public abstract void prepare();
	
	protected void prepareFrame(boolean clearDisplay) {
		this.shader.useProgram();
		if(clearDisplay) {
			OpenGL.setDepthTest(true);
			OpenGL.clearColor();
			OpenGL.clearDepth();
			OpenGL.clearColor(new Vector3f(0.0f, 0.25f, 0.25f), 1.0f);
		}
	}
	
	//Render
	public abstract void render(Object obj);
	
	protected void loadComponents(Entity entity) {
		for(Component component : entity.getComponents()) {
			switch(component.getComponentType()) {
			case "Model" :
				loadMatrices(entity.getTransform());
				break;
			default :
				break;
			}
		}
	}
	
	protected void loadMatrices(Transform transform) {
		Matrix4f transformationMatrix = utils.createTransformationMatrix(transform);
		shader.loadUniform(transformationMatrix, "transformationMatrix");
		shader.loadUniform(engine.getViewMatrix(), "viewMatrix");
		shader.loadUniform(engine.getProjectionMatrix(), "projectionMatrix");
	}
	
	protected void renderVao(Vao vao) {
		if(vao == null) return;
		vao.render();
	}
	
	//Finish 
	public abstract void endRender();
	
	protected void endRendering() {
		this.shader.stopProgram();
	}
}
