package com.seaSaltedToaster.simpleEngine.input;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.input.glfwCallbacks.KeyCallback;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class Keyboard {
	
	//Mouse input callbacks
	private KeyCallback keyCallback;
		
	//Create mouse instance
	public Keyboard(Window window) {
		createCallbacks(window);
	}
		
	//Create GLFW callbacks
	private void createCallbacks(Window window) {
		GLFW.glfwSetKeyCallback(window.windowID, keyCallback = new KeyCallback());
	}
	
	public void addKeyListener(KeyListener keyListener) {
		keyCallback.addListener(keyListener);
	}
		
	//Update mouse value
	public void updateInput() {
		
	}

	public KeyCallback getKeyCallback() {
		return keyCallback;
	}

}
