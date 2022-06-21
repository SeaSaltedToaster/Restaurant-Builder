package com.seaSaltedToaster.simpleEngine.input.glfwCallbacks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWScrollCallback;

import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;

public class ScrollCallback extends GLFWScrollCallback {
	
	//Listeners
	private List<ScrollListener> listeners;
	private float scrollValue = 0;
	
	public ScrollCallback() {
		//Create listener list
		this.listeners = new ArrayList<ScrollListener>();
	}
	
	public void poll() {
		scrollValue = 0;
		
		//Notify all active listeners
		for(ScrollListener listener : listeners) {
			listener.notifyScrollChanged(scrollValue);
		}
	}

	@Override //When value changes, this is called
	public void invoke(long arg0, double arg1, double arg2) {
		//ARG2 is the new scroll value
		scrollValue = (float) arg2;
		
		//Notify all active listeners
		for(ScrollListener listener : listeners) {
			listener.notifyScrollChanged(scrollValue);
		}
	}
	
	public void addListener(ScrollListener listener) {
		listeners.add(listener);
	}
	
	public float getScrollValue() {
		return scrollValue;
	}

}