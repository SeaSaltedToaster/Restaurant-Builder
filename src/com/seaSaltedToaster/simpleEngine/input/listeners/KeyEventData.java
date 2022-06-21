package com.seaSaltedToaster.simpleEngine.input.listeners;

import org.lwjgl.glfw.GLFW;

public class KeyEventData {
	
	private int key;
	private int scancode; 
	private int action; 
	
	private int modifiers;
	private long windowID;
	
	public KeyEventData(int key, int scancode, int action, int modifiers, long windowID) {
		this.key = key;
		this.scancode = scancode;
		this.action = action;
		this.modifiers = modifiers;
		this.windowID = windowID;
	}
	
	public String getKeyName() {
		return GLFW.glfwGetKeyName(key, scancode);
	}

	public int getKey() {
		return key;
	}

	public int getScancode() {
		return scancode;
	}

	public int getAction() {
		return action;
	}

	public int getModifiers() {
		return modifiers;
	}

	public long getWindowID() {
		return windowID;
	}

}
