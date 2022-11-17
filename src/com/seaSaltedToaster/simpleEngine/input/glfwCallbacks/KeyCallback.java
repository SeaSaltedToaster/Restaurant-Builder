package com.seaSaltedToaster.simpleEngine.input.glfwCallbacks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.seaSaltedToaster.simpleEngine.input.Keyboard;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;

public class KeyCallback extends GLFWKeyCallback {
	
	//Event Data
	private KeyEventData keyEventData;

	//Listeners
	private List<KeyListener> listeners;
		
	public KeyCallback() {
		//Create listener list
		this.listeners = new ArrayList<KeyListener>();
	}
		
	@Override
	public void invoke(long arg0, int arg1, int arg2, int arg3, int arg4) {
		//Refresh data on event
		keyEventData = new KeyEventData(arg1, arg2, arg3, arg4, arg0);
		Keyboard.LSHIFT = (arg4 == GLFW.GLFW_MOD_SHIFT);
		
		//Notify all active listeners
		for(KeyListener listener : listeners) {
			listener.notifyButton(keyEventData);
		}
	}
		
	public void addListener(KeyListener listener) {
		//Add listener to list
		listeners.add(listener);
	}

	public KeyEventData getKeyEventData() {
		return keyEventData;
	}

	public List<KeyListener> getListeners() {
		return listeners;
	}

}
