package com.seaSaltedToaster.simpleEngine.utilities;

public class SmoothFloat {
	
	//Values
	private float target, value;
	private float amountPer = 0.125f;
	
	public SmoothFloat(float startValue) {
		this.target = startValue;
		this.value = startValue;
	}
	
	public void update(double delta) {
		float curOffset = target - value;
		if(curOffset == 0) {
			value = target;
			return;
		} else {
			value += (curOffset * amountPer);
		}
	}
	
	public void setTarget(float target) {
		this.target = target;
	}
	
	public void increaseTarget(float inc) {
		this.target += inc;
	}

	public float getValue() {
		return value;
	}
	
}
