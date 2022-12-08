package com.seaSaltedToaster.simpleEngine.renderer;

import java.io.File;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalTime;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryUtil;

import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;

public class Window {
	
	public static long windowID;
	
	public static double DeltaTime;
	private static double lastFrameTime;
	
	public static double width, height;
	
	public void createWindow(int width, int height, String title) {
		boolean initState = GLFW.glfwInit();
		Window.width = width;
		Window.height = height;
		
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
	
	public static double getWidth() {
		return width;
	}

	public static double getHeight() {
		return height;
	}

	public static double getAspectRatio() {
		return getWidth() / getHeight();
	}
		
	public void closeWindow() {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(windowID);
	}

}

