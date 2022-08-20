package com.seaSaltedToaster.restaurantGame.ai;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Mover {
	
	//Objects
	private Entity entity;
	
	//Speed
	public boolean isMoving = false;
	private float speed = 5.0f;
	private float minTargetDist = 0.125f / 2.0f;
	
	//Movement
	private Vector3f target, offset;
	
	public Mover(Entity entity) {
		this.entity = entity;
		this.target = entity.getTransform().getPosition();
	}
	
	public Vector3f update() {
		if(target == null || !isMoving) return null;
		return entity.getTransform().getPosition().add(offset);
	}
	
	public void setTarget(Vector3f target) {
		Vector3f start = this.target.copy();
		this.target = target.copy();
		this.offset = start.subtract(target);
		this.offset.scale(speed / -250f);
		offset.y = 0;
		this.isMoving = true;
	}
	
	public void stop() {
		this.isMoving = false;
		this.entity.getTransform().setPosition(target);
	}
	
	public boolean reachedTarget() {
		return (getTargetDistance() <= minTargetDist);
	}
	
	private float getTargetDistance() {
		Vector3f targetPos = target;
		Vector3f personPos = entity.getTransform().getPosition();
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

}
