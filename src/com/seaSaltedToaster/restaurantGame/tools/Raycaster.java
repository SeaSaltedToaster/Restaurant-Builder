package com.seaSaltedToaster.restaurantGame.tools;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingViewer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Raycaster implements MouseListener, MousePosListener {

	//Objects
	private Engine engine;
	
	//Raycasting
	private MousePicker picker;
	public static Vector3f lastRay;
	
	//Building
	public BuildingManager builder;
	
	//Selected object
	private BuildingViewer viewer;
	
	//Action objects
	public Ground ground;
	
	public Raycaster(Engine engine) {
		this.engine = engine;
		this.engine.getMouse().getMouseButtonCallback().addListener(this);
		this.engine.getMouse().getMousePositionCallback().addListener(this);
		this.picker = new MousePicker(engine);
		
		this.viewer = new BuildingViewer(engine);
		engine.addUi(viewer);
		viewer.open(null);
	}
	
	@Override
	public void notifyButton(MousePosData eventData) {
		if(MainApp.menuFocused) {
			ground.selectAt(null);
			return;
		}
		
		//Update picker
		float groundHeight = BuildLayer.HEIGHT_OFFSET * builder.getCurLayer();
		picker.update(groundHeight);
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		ground.getRenderer().setHighlight(-1);
		if(ray == null) return;
		
		builder.movePreview(ray);
		
		Vector3f placePosition = new Vector3f(Math.round(ray.x), Math.round(ray.y), Math.round(ray.z));
		lastRay = placePosition;
		ground.selectAt(placePosition);
	}

	@Override
	public void notifyButton(MouseEventData eventData) {
		if(MainApp.menuFocused) {
			ground.selectAt(null);
			return;
		}
				
		//Key states
		boolean isLeftDown = eventData.getKey() == GLFW.GLFW_MOUSE_BUTTON_LEFT;
		boolean isPlaceDown = eventData.getAction() == GLFW.GLFW_PRESS;
		
		//Check if building is in the way
		if(builder.getSelectedEntity() != null && isLeftDown && isPlaceDown && !builder.isBuilding()) {
			Entity selected = builder.getSelectedEntity();
			viewer.open(selected);
			//builder.delete(selected);
			return;
		} else if(isLeftDown && isPlaceDown) {
			viewer.open(null);
		}
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		if(ray == null) return;
		Vector3f placePosition = new Vector3f(Math.round(ray.x), Math.round(ray.y), Math.round(ray.z));
		
		//Place
		if(isPlaceDown && isLeftDown) {
			if(builder.isBuilding()) {
				boolean placed = builder.startPlacement(placePosition);
			}
		}
		
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		
		//Check for delete
		if(builder.getSelectedEntity() != null && !builder.isBuilding() && eventData.getKey() == GLFW.GLFW_KEY_X) {
			Entity selected = builder.getSelectedEntity();
			builder.delete(selected);
			return;
		}
		
		if(eventData.getKey() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return;
		//Update picker
		float groundHeight = BuildLayer.HEIGHT_OFFSET * builder.getCurLayer();
		picker.update(groundHeight);
	}
		
}