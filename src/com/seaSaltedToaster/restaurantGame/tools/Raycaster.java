package com.seaSaltedToaster.restaurantGame.tools;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Raycaster implements MouseListener, MousePosListener {

	//Objects
	private Engine engine;
	private MousePicker picker;
	
	//Building
	public BuildingManager builder;
	
	//Action objects
	public RayMode type;
	public Ground ground;
	
	public Raycaster(Engine engine) {
		this.engine = engine;
		this.engine.getMouse().getMouseButtonCallback().addListener(this);
		this.engine.getMouse().getMousePositionCallback().addListener(this);
		this.picker = new MousePicker(engine);
		this.type = RayMode.SELECT;
	}
	
	@Override
	public void notifyButton(MousePosData eventData) {
		//Update picker
		picker.update();
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		if(ray == null) return;
		Vector3f placePos = new Vector3f(Math.round(ray.x), Math.round(ray.y), Math.round(ray.z));;		
		
		builder.movePreview(placePos);
		ground.selectAt(placePos);
	}

	@Override
	public void notifyButton(MouseEventData eventData) {
		if(eventData.getAction() != GLFW.GLFW_PRESS 
				|| eventData.getKey() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return;
		//Update picker
		picker.update();
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		if(ray == null) return;
		Vector3f placePos = (ray);
		
		if(builder.isBuilding())
			builder.placeBuilding(placePos);
	}
		
}