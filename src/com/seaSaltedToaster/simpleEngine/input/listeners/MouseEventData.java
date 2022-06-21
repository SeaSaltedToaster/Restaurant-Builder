package com.seaSaltedToaster.simpleEngine.input.listeners;

public class MouseEventData {
	
	private int key;
	private int action; 
	
	private int modifiers;
	private long windowID;
	
	public MouseEventData(int key, int action, int modifiers, long windowID) {
		this.key = key;
		this.action = action;
		this.modifiers = modifiers;
		this.windowID = windowID;
	}
	
	public boolean inputStatus(int tryKey, int tryAction) {
		return (tryKey == key) && (tryAction == action);
	}

	public int getKey() {
		return key;
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
