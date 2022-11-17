package com.seaSaltedToaster.simpleEngine.input;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.input.glfwCallbacks.KeyCallback;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class Keyboard {
	
	//Mouse input callbacks
	private KeyCallback keyCallback;
	
	//Keys
	public static boolean LSHIFT = false;
		
	//Create mouse instance
	public Keyboard(Window window) {
		createCallbacks(window);
	}
		
	//Create GLFW callbacks
	private void createCallbacks(Window window) {
		GLFW.glfwSetKeyCallback(Window.windowID, keyCallback = new KeyCallback());
	}
	
	public void addKeyListener(KeyListener keyListener) {
		keyCallback.addListener(keyListener);
	}
	
	public void clearListeners() {
		keyCallback.getListeners().clear();
	}
		
	//Update mouse value
	public void updateInput() {
		
	}

	public KeyCallback getKeyCallback() {
		return keyCallback;
	}
	
	public static String getKeyName(int key) {
		String keyName = GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
		if(keyName == null || keyName.equalsIgnoreCase("null")) return "";
		return keyName;
	}
	
	public static boolean checkSpecialChar(int keyID) {
		if(keyID == GLFW.GLFW_KEY_SPACE) {
			return true;
		}
		if(keyID == GLFW.GLFW_KEY_BACKSPACE) {
			return true;
		}
		return false;
	}

}
