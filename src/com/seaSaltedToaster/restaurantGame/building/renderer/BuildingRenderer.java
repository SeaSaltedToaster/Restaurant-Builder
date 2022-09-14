package com.seaSaltedToaster.restaurantGame.building.renderer;

import com.seaSaltedToaster.restaurantGame.building.AdvancedBuilder;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.utilities.Matrix4f;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class BuildingRenderer extends Renderer {
	
	//Shading / Objects
	private BuildingManager manager;
	private Matrix4f transform;
	private int selectedId = -127;

	public BuildingRenderer(BuildingManager manager, Engine engine) {
		super(new BuildingShader(), engine);
		this.manager = manager;
		this.transform = new Matrix4f();
	}
	
	@Override
	public void prepare() {
		this.selectedId = manager.getSelectRenderer().getSelectedId();
		super.prepareFrame(false);
	}
	
	@Override
	public void render(Object obj) {
		//Objects pass
		if(manager.getSelectedEntity() == null)
			this.selectedId = -127;
		int index = 0;
		for(BuildLayer layer : manager.getLayers()) {
			if(!layer.isOn()) {
				continue;
			}
			for(Entity entity : layer.getBuildings()) {
				if(entity == null) continue;
				entity.updateComponents();
			}
			layer.getBuildings().remove(null);
			for(Entity entity : layer.getBuildings()) {
				if(entity == null) continue;
				renderEntity(entity, index, selectedId);
				index++;
			}
		}
		
		//Preview pass
		AdvancedBuilder builder = manager.getBuilder();
		for(Entity entity : builder.getPreviews()) {
			if(entity != null)
				renderEntity(entity, -1, selectedId);
		}
		renderEntity(builder.getStart(), -1, selectedId);
		renderEntity(builder.getEnd(), -1, selectedId);
	}

	private void renderEntity(Entity entity, int index, int curId) {
		//Matrices
		Transform transform = entity.getTransform();
		super.loadMatrices(transform);
		
		//Transformation
		this.transform = utils.createTransformationMatrix(this.transform, transform.getPosition(), transform.getRotation().x, transform.getRotation().y, transform.getRotation().z, transform.getScale());
		shader.loadUniform(this.transform, "transformationMatrix");
		
		//Other uniforms
		shader.loadUniform(index, "currentId");
		shader.loadUniform(TimeHandler.DAY_VALUE, "dayValue");
		if(!manager.isBuilding())
			shader.loadUniform(curId, "selectedId");
		else
			shader.loadUniform(-127, "selectedId");
		
		//Load id related
		BuildingId buildingId = (BuildingId) entity.getComponent("BuildingId");
		if(buildingId != null) {
			shader.loadUniform(buildingId.getCustomColor(), "customColor");
		}
		
		//Render
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		super.renderVao(comp.getMesh());
	}
	
	@Override
	public void endRender() {
		super.endRendering();
	}

}
