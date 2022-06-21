package com.seaSaltedToaster.simpleEngine.input;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.input.glfwCallbacks.MouseButtonCallback;
import com.seaSaltedToaster.simpleEngine.input.glfwCallbacks.MousePositionCallback;
import com.seaSaltedToaster.simpleEngine.input.glfwCallbacks.ScrollCallback;
import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class Mouse {

	//Mouse input callbacks
	private static ScrollCallback scrollCallback;
	private static MousePositionCallback mousePositionCallback;
	private MouseButtonCallback mouseButtonCallback;
	
	//Current values
	private boolean isLocked;
	private static double mouseX = 0, mouseY = 0;
	private float scrollValue = 0;
	
	//Display
	private Window window;
	
	//Key States
	public static int PRESSED = GLFW.GLFW_PRESS;
	public static int RELEASED = GLFW.GLFW_RELEASE;
		
	//Mouse button integers
	public static int LEFT_BUTTON = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	public static int RIGHT_BUTTON = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	
	//Create mouse instance
	public Mouse(Window window) {
		this.window = window;
		createCallbacks(window);
	}
	
	//Create GLFW callbacks
	private void createCallbacks(Window window) {
		GLFW.glfwSetScrollCallback(window.windowID, scrollCallback = new ScrollCallback());
		GLFW.glfwSetCursorPosCallback(window.windowID, mousePositionCallback = new MousePositionCallback());
		GLFW.glfwSetMouseButtonCallback(window.windowID, mouseButtonCallback = new MouseButtonCallback());
	}
	
	public void clearListeners() {
		mouseButtonCallback.clearListeners();
		mousePositionCallback.clearListeners();
	}
	
	//Update mouse value
	public void updateInput() {
		mouseX = mousePositionCallback.getMouseX();
		mouseY = mousePositionCallback.getMouseY();
		
		scrollCallback.poll();
		scrollValue = scrollCallback.getScrollValue();
	}
	
	public void addScrollListener(ScrollListener listener) {
		scrollCallback.addListener(listener);
	}
	
	public double getMouseCoordsX() {
		double x = -1.0 + 2.0 * mouseX / window.getCurrentWidth();
		return x;
	}
	    
	public double getMouseCoordsY() {
	    double y = 1.0 - 2.0 * mouseY / window.getCurrentHeight();
	    return y;
	}
	
	public double normalizeMouseCoordX(double x) {
		double x2 = -1.0 + 2.0 * x / window.getCurrentWidth();
		return x2;
	}
	    
	public double normalizeMouseCoordY(double y) {
		double y2 = 1.0 - 2.0 * y / window.getCurrentHeight();
	    return y2;
	}
	
	public void setVisible(boolean lock) {
		isLocked = lock;
	    if(isLocked) {
	    	GLFW.glfwSetInputMode(window.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL); 
	    }
	    else {
	    	GLFW.glfwSetInputMode(window.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED); 
	    }
	}

	public float getScrollValue() {
		return scrollValue;
	}

	public double getMouseX() {
		return mousePositionCallback.getMouseX();
	}

	public double getMouseY() {
		return mousePositionCallback.getMouseY();
	}

	public MouseButtonCallback getMouseButtonCallback() {
		return mouseButtonCallback;
	}

	public MousePositionCallback getMousePositionCallback() {
		return mousePositionCallback;
	}
	
}
