package com.seaSaltedToaster.simpleEngine.rendering;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.SimpleShader;

public class AdvancedRenderer extends Renderer {

	public AdvancedRenderer(Engine engine) {
		super(new SimpleShader(), engine);
	}

	@Override
	public void prepare() {
		//Nothing
		super.prepareFrame(false);
	}

	@Override
	public void render(Object obj) {
		//Nothing
		if(obj instanceof Vao) {
			super.renderVao((Vao) obj);
		}
		if(obj instanceof Entity) {
			Entity entity = (Entity) obj;
			super.loadComponents(entity);
			ModelComponent model = (ModelComponent) entity.getComponent("Model");
			super.renderVao(model.getMesh());
		}
	}

	@Override
	public void endRender() {
		//Nothing
		super.endRendering();
	}

}
