package com.seaSaltedToaster.simpleEngine.utilities;

import org.lwjgl.glfw.GLFW;

public class Timer {
	
	//Length of timer
	private float length = 0;
	private boolean finished, isRunning = false;
	
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
		isRunning = true;
	}
	
	public void update(double delta) {
		currentTime += delta;
		finished = checkFinished();
	}
	
	public void stop() {
		this.startTime = 0;
		this.currentTime = 0;
		this.endTime = 0;
		isRunning = false;
	}
	
	private boolean checkFinished() {
		return (currentTime >= endTime);
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public float getLength() {
		return length;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getCurrentTime() {
		return currentTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

}
