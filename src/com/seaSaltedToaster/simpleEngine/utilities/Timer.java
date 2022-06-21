package com.seaSaltedToaster.simpleEngine.utilities;

import org.lwjgl.glfw.GLFW;

public class Timer {
	
	//Length of timer
	private float length = 0;
	private boolean finished;
	
	//Current timer options
	private double startTime;
	private double currentTime;
	private double endTime;
	
	public Timer(float length) {
		this.length = length;
	}
	
	public void start() {
		this.startTime = GLFW.glfwGetTime();
		this.currentTime = GLFW.glfwGetTime();
		this.endTime = startTime + length;
	}
	
	public void update(double delta) {
		currentTime += delta;
		finished = checkFinished();
	}
	
	public void stop() {
		this.startTime = 0;
		this.currentTime = 0;
		this.endTime = 0;
	}
	
	private boolean checkFinished() {
		return (currentTime >= endTime);
	}

	public boolean isFinished() {
		return finished;
	}

}
