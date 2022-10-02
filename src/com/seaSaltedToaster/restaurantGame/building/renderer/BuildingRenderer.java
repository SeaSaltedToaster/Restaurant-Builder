package com.seaSaltedToaster.restaurantGame.building.renderer;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.AdvancedBuilder;
import com.seaSaltedToaster.restaurantGame.building.Building;
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
	
	//Other objects
	private List<Entity> foods;

	public BuildingRenderer(BuildingManager manager, Engine engine) {
		super(new BuildingShader(), engine);
		this.manager = manager;
		this.transform = new Matrix4f();
		this.foods = new ArrayList<Entity>();
	}
	
	@Override
	public void prepare() {
		this.selectedId = manager.getSelectRenderer().getSelectedId();
		super.prepareFrame(false);
	}
	
	@Override
	public void render(Object obj) {
		//Check if our we are selecting an object
		if(manager.getSelectedEntity() == null)
			this.selectedId = -127;
		
		//Loop through all layers to render them
		int index = 0; //id of the object
		for(BuildLayer layer : manager.getLayers()) {
			//If the layer is off, go to the next
			if(!layer.isOn()) {
				continue;
			}
			
			//List of buildings
			List<Entity> buildingsEntities = layer.getBuildings();
			
			//Update all components
			for(Entity building : buildingsEntities) {
				if(building == null) continue;
				building.updateComponents();
			}
			
			for(Entity entity : buildingsEntities) {
				if(entity == null) continue;
				renderEntity(entity, index, selectedId);
				index++;
			}
		}
		
//		//Render foods
//		for(Entity foodItem : foods) {
//			renderEntity(foodItem, -99, selectedId);
//		}
		
		//Preview pass
		AdvancedBuilder builder = manager.getBuilder();
		renderEntity(builder.getStart(), -1, selectedId);
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
			shader.loadUniform(buildingId.getPrimary(), "primaryColor");
			shader.loadUniform(buildingId.getSecondary(), "secondaryColor");
			
			if(index == -1) {
				Building building = buildingId.getType();
				shader.loadUniform(building.getDefPrimary(), "primaryColor");
				shader.loadUniform(building.getDefSecondary(), "secondaryColor");
			}
		}
		
		//Render
		ModelComponent comp = (ModelComponent) entity.getComponent("Model");
		super.renderVao(comp.getMesh());
	}
	
	@Override
	public void endRender() {
		super.endRendering();
	}

	public List<Entity> getFoods() {
		return foods;
	}

}
