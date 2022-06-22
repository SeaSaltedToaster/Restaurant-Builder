package com.seaSaltedToaster.simpleEngine.utilities;

public class SmoothVector {

	private float speed = 5;
	private Vector3f current = new Vector3f();
	private Vector3f target = new Vector3f();
	
	public SmoothVector(Vector3f target) {
	  this.target.set(target);
	  this.current.set(target);
	}
	
	public void update(double delta) {
		Vector3f diff = target.subtract(current);
	  	float factor = (float) (delta * this.speed);
		if (factor > 1.0F) {
		    this.current.set(this.target);
		} else {
			diff.scale(factor);
	    	Vector3f.add(this.current, diff, this.current);
	  	} 
	}
	
	public Vector3f get() {
		return current;
	}

	public void cancelTarget() {
		this.target.set(this.current);
	}
	
	public void invertCurrent(float waterHeight) {
		this.current.y -= 2.0F * (this.current.y - waterHeight);
	}
	
	public Vector3f getTarget() {
		return this.target;
	}
	
	public void setTarget(Vector3f newTarget) {
		this.target.set(newTarget);
	}
	
	public void setTarget(float x, float y, float z) {
		this.target.set(x, y, z);
	}
	
	public void increaseTarget(float dx, float dy, float dz) {
		this.target.x += dx;
	  	this.target.y += dy;
	  	this.target.z += dz;
	}
	
	public void force(Vector3f newValue) {
		this.current.set(newValue);
	  	this.target.set(newValue);
	}
	
	public void force(float x, float y, float z) {
		this.current.set(x, y, z);
	  	this.target.set(x, y, z);
	}
	
}
