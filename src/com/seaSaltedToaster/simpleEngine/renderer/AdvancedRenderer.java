package com.seaSaltedToaster.simpleEngine.renderer;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;

public class AdvancedRenderer extends Renderer {

	public AdvancedRenderer(Engine engine) {
		super(new AdvancedShader(), engine);
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
