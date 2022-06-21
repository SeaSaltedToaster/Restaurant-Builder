package com.seaSaltedToaster.simpleEngine.input.listeners;

public class MousePosData {
	
	private double mouseX, mouseY;
	private long windowID;
	
	public MousePosData(double mouseX, double mouseY, long windowID) {
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.windowID = windowID;
	}

	public double getMouseX() {
		return mouseX;
	}

	public void setMouseX(double mouseX) {
		this.mouseX = mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}

	public long getWindowID() {
		return windowID;
	}

	public void setWindowID(long windowID) {
		this.windowID = windowID;
	}

}