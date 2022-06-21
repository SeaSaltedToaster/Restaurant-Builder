package com.seaSaltedToaster.simpleEngine.utilities;

public class SmoothValue {
	
	//Settings
	private float value;
	private float target;
	private float speed = 5;
	
	public SmoothValue(float startValue) {
		this.value = startValue;
		this.target = startValue;
	}
	
	public void update(double delta) { 
		float offset = this.target - this.value;
	    float factor = (float) (delta * this.speed);
	    if (factor > 1.0F) {
	    	this.value = this.target;
	    } else {
	    	this.value += offset * factor;
	    } 
	}
	
	public float get() {
		return value;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void cancelTarget() {
	    this.target = this.value;
	}
	  
	public void invertActual() {
	    this.value = -this.value;
	}
	  
	public void setTarget(float target) {
	    this.target = target;
	}
	  
	public void increaseTarget(float increase) {
	    this.target += increase;
	}
	  
	public void force(float newValue) {
	    this.value = newValue;
	    this.target = newValue;
	}

	public void clampTarget(float min, float max) {
	    this.target = MathUtils.clamp(this.target, min, max);
	}
	
}