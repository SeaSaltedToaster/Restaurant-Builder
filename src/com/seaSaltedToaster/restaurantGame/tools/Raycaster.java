package com.seaSaltedToaster.restaurantGame.tools;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Raycaster implements MouseListener {

	//Raycasting
	private final float MAX_DIST = 1000;
	
	//Objects
	private Engine engine;
	private MousePicker picker;
	
	//Action objects
	public Ground ground;
	
	public Raycaster(Engine engine) {
		this.engine = engine;
		this.engine.getMouse()
		.getMouseButtonCallback().addListener(this);
		this.picker = new MousePicker(engine);
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
		
		//Get ray type
		ground.selectAt(ray);
		
		//Set entity pos
		MainApp.transform.setPosition(ray);
	}
	
	
}
	