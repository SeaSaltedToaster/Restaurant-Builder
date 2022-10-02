package com.seaSaltedToaster.simpleEngine.renderer;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public class WindowResizer extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long arg0, int arg1, int arg2) {
		//When the window is resized, reset viewport
		GL11.glViewport(0, 0, arg1, arg2);
		
		IntBuffer posX = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(arg0, posX, null);
		int newX = posX.get(0);
		
		IntBuffer posY = BufferUtils.createIntBuffer(1);
		GLFW.glfwGetWindowSize(arg0, null, posY);
		int newY = posY.get(0);
		
		Window.width = newX;
		Window.height = newY;
	}

}
