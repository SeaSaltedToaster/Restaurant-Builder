package com.seaSaltedToaster.simpleEngine.input.glfwCallbacks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;

import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;

public class MousePositionCallback extends GLFWCursorPosCallback {

	//Event Data
	private MousePosData mouseEventData;

	//Listeners
	private List<MousePosListener> listeners;
		
	public MousePositionCallback() {
		//Create listener list
		this.listeners = new ArrayList<MousePosListener>();
		this.mouseEventData = new MousePosData(0,0,0);
	}
	
	@Override
	public void invoke(long arg0, double arg1, double arg2) {
		mouseEventData = new MousePosData(arg1, arg2, arg0);
		
		//Notify all active listeners
		for(MousePosListener listener : listeners) {
			listener.notifyButton(mouseEventData);
		}
	}
	
	public void clearListeners() {
		listeners.clear();
	}
			
	public void addListener(MousePosListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(MousePosListener listener) {
		listeners.remove(listener);
	}

	public double getMouseX() {
		return mouseEventData.getMouseX();
	}

	public double getMouseY() {
		return mouseEventData.getMouseY();
	}

}
