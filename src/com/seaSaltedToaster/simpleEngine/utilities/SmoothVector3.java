package com.seaSaltedToaster.simpleEngine.utilities;

public class SmoothVector3 {

	//Values
	private Vector3f target, value;
	private float amountPer = 0.125f;
	
	public SmoothVector3(Vector3f startValue) {
		this.target = startValue;
		this.value = startValue;
	}
	
	public void update(double delta) {
		Vector3f curOffset = target.subtract(value);
		if(curOffset.lengthSquared() == 0f) {
			value = target;
		} else {
			value.add(curOffset.scale(amountPer));
		}
	}
	
	public void setTarget(Vector3f target) {
		this.target = target;
	}
	
	public void increaseTarget(Vector3f inc) {
		this.target.add(inc);
	}
	
	public void increaseTarget(float x, float y, float z) {
		this.target.add(new Vector3f(x, y, z));
	}

	public Vector3f getValue() {
		return value;
	}
	
}
