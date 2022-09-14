package com.seaSaltedToaster.simpleEngine.renderer;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryUtil;

public class Window {
	
	public static long windowID;
	
	public static double DeltaTime;
	private static double lastFrameTime;
	
	public void createWindow(int width, int height, String title) {
		boolean initState = GLFW.glfwInit();
		
		if(initState == false) {
			throw new IllegalStateException("Could not create GLFW");
		}

		//Anti Aliasing
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 8);
		
		windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		
		if(windowID == MemoryUtil.NULL) {
			throw new IllegalStateException("Cant make window :(");
		}
		
		GLFW.glfwSetWindowSizeCallback(windowID, new WindowResizer());
		
		GLFW.glfwMakeContextCurrent(windowID);
		GLFW.glfwSwapInterval(0);
		GLFW.glfwShowWindow(windowID);
		GL.createCapabilities();
		
		//Enable anti aliasing
		GL11.glEnable(GL13.GL_MULTISAMPLE);  
	}
	
	public void updateWindow() {
		GLFW.glfwSwapBuffers(windowID);
		GLFW.glfwPollEvents();
		GLFW.glfwSwapInterval(1);
		Window.DeltaTime = getDelta();
	}
	
	private static double getDelta() {
		double currentTime = GLFW.glfwGetTime();
		double delta = (currentTime - lastFrameTime);
		lastFrameTime = currentTime;
		DeltaTime = delta;
		return delta;
	}
	
	public static double getCurrentWidth() {
		IntBuffer posX = BufferUtils.createIntBuffer(1);
	    GLFW.glfwGetWindowSize(windowID, posX, null);
	    return posX.get(0);
	}
	
	public static double getWidth() {
		return Window.getCurrentWidth();
	}

	public static double getCurrentHeight() {
		IntBuffer posY = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(windowID, null, posY);
	    return posY.get(0);
	}
	
	public static double getHeight() {
		return Window.getCurrentHeight();
	}
	
	public static double getAspectRatio() {
		return getCurrentWidth() / getCurrentHeight();
	}
		
	public void closeWindow() {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

}

