package com.seaSaltedToaster.simpleEngine.uis.animations.drivers;

import org.lwjgl.glfw.GLFW;

public class SinDriver extends ValueDriver {

	public SinDriver(float length) {
		super(length);
	}

	@Override
	protected float calculateValue(float paramFloat) {
		return (float) Math.sin(GLFW.glfwGetTime());
	}

}
