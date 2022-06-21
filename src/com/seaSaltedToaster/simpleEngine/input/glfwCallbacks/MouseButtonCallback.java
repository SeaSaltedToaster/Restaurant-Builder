package com.seaSaltedToaster.simpleEngine.input.glfwCallbacks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;

public class MouseButtonCallback extends GLFWMouseButtonCallback {
	
	//Event Data
	private MouseEventData mouseEventData;

	//Listeners
	private List<MouseListener> listeners;
	private List<MouseListener> toAdd;
	
	public MouseButtonCallback() {
		//Create listener list
		this.listeners = new ArrayList<MouseListener>();
		this.toAdd = new ArrayList<MouseListener>();
	}
	
	@Override
	public void invoke(long arg0, int arg1, int arg2, int arg3) {
		//Refresh data on event
		mouseEventData = new MouseEventData(arg1, arg2, arg3, arg0);
		
		//Notify all active listeners
		listeners.addAll(toAdd); toAdd.clear();
		for(Iterator<MouseListener> iterator = listeners.iterator(); iterator.hasNext();) {
			MouseListener listener = iterator.next();
			listener.notifyButton(mouseEventData);
		}
		listeners.addAll(toAdd); toAdd.clear();
	}
	
	public void clearListeners() {
		listeners.clear();
		toAdd.clear();
	}
	
	public void addListener(MouseListener listener) {
		//Add listener to list
		toAdd.add(listener);
	}
	
	public void removeListener(MouseListener listener) {
		//Add listener to list
		listeners.remove(listener);
	}

}
