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
		
	//Key States
	public static int PRESSED = GLFW.GLFW_PRESS;
	public static int RELEASED = GLFW.GLFW_RELEASE;
		
	//Mouse button integers
	public static int LEFT_BUTTON = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	public static int RIGHT_BUTTON = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	
	//Create mouse instance
	public Mouse(Window window) {
		createCallbacks(window);
	}
	
	//Create GLFW callbacks
	private void createCallbacks(Window window) {
		GLFW.glfwSetScrollCallback(Window.windowID, scrollCallback = new ScrollCallback());
		GLFW.glfwSetCursorPosCallback(Window.windowID, mousePositionCallback = new MousePositionCallback());
		GLFW.glfwSetMouseButtonCallback(Window.windowID, mouseButtonCallback = new MouseButtonCallback());
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
	
	public static double getMouseCoordsX() {
		double x = -1.0 + 2.0 * mouseX / Window.getWidth();
		return x;
	}
	    
	public static double getMouseCoordsY() {
	    double y = 1.0 - 2.0 * mouseY / Window.getHeight();
	    return y;
	}
	
	public static double normalizeMouseCoordX(double x) {
		double x2 = -1.0 + 2.0 * x / Window.getWidth();
		return x2;
	}
	    
	public static double normalizeMouseCoordY(double y) {
		double y2 = 1.0 - 2.0 * y / Window.getHeight();
	    return y2;
	}
	
	public void setVisible(boolean lock) {
		isLocked = lock;
	    if(isLocked) {
	    	GLFW.glfwSetInputMode(Window.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL); 
	    }
	    else {
	    	GLFW.glfwSetInputMode(Window.windowID, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED); 
	    }
	}

	public float getScrollValue() {
		return scrollValue;
	}

	public static double getMouseX() {
		return mousePositionCallback.getMouseX();
	}

	public static double getMouseY() {
		return mousePositionCallback.getMouseY();
	}

	public MouseButtonCallback getMouseButtonCallback() {
		return mouseButtonCallback;
	}

	public MousePositionCallback getMousePositionCallback() {
		return mousePositionCallback;
	}
	
}
