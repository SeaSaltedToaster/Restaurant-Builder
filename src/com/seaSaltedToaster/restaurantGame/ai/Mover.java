package com.seaSaltedToaster.restaurantGame.ai;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Mover {
	
	//The object / entity we are moving
	private Entity entity;
	
	//Speed of the entity
	private float speed = 5.0f;
	private float minTargetDist = 0.125f / 2.0f;
	
	//Movement information
	public boolean isMoving = false;
	private Vector3f target, offset;
	
	public Mover(Entity entity) {
		//Set entity and temporary target position
		this.entity = entity;
		this.target = entity.getTransform().getPosition();
	}
	
	public Vector3f update() {
		//If we arent moving or the target is invalid, return null
		if(target == null || !isMoving) return null;
		
		//Return the entity's position plus the per-frame movement
		return entity.getTransform().getPosition().add(offset);
	}
	
	public void setTarget(Vector3f target) {
		//The new start is the old target
		Vector3f start = this.target.copy();
		
		//The new target is set
		this.target = target.copy();
		
		//THe per-frame movement the entity will have
		this.offset = start.subtract(target);
		this.offset.scale(speed / -250f);
		this.offset.y = 0;
		
		//Say that we are moving now
		this.isMoving = true;
	}
	
	public void stop() {
		//Stop movement
		this.isMoving = false;
		entity.getTransform().setPosition(target.copy().scale(1.0f));
	}
	
	public boolean reachedTarget() {
		//Check if the entity is within target distance
		return (getTargetDistance() <= minTargetDist);
	}
	
	private float getTargetDistance() {
		//Get positions
		Vector3f targetPos = target;
		Vector3f personPos = entity.getTransform().getPosition();
		
		//Check and return the distance from the target
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

}
