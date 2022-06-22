package com.seaSaltedToaster.simpleEngine.renderer;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

public class WindowResizer extends GLFWWindowSizeCallback {

	@Override
	public void invoke(long arg0, int arg1, int arg2) {
		//When the window is resized, reset viewport
		GL11.glViewport(0, 0, arg1, arg2);
	}

}
