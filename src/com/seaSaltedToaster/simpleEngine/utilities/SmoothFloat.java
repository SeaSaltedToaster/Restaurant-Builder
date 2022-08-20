package com.seaSaltedToaster.simpleEngine.utilities;

public class SmoothFloat {
	
	//Values
	private float target, value;
	private float amountPer = 0.125f;
	
	public SmoothFloat(float startValue) {
		this.target = startValue;
		this.value = 0;
	}
	
	public void update(double delta) {
		float curOffset = target - value;
		if(curOffset == 0.0f || value == target) {
			value = target;
		} else if(curOffset > 0) {
			value += (curOffset * amountPer);
		} else if(curOffset < 0) {
			value -= (-curOffset * amountPer);
		}
	}
	
	public void setAmountPer(float amountPer) {
		this.amountPer = amountPer;
	}

	public float getTarget() {
		return target;
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

	public void setValue(float value) {
		this.value = value;
	}
	
}