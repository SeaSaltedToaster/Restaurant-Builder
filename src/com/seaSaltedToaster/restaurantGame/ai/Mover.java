package com.seaSaltedToaster.restaurantGame.ai;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Mover {
	
	//The object / entity we are moving
	private Entity entity;
	
	//Speed of the entity
	private float minTargetDist = 0.25f;
	
	//Movement information
	public boolean isMoving = false;
	private Vector3f target, offset;
	
	public Mover(Entity entity) {
		//Set entity and temporary target position
		this.entity = entity;
		this.target = entity.getTransform().getPosition().copy();
	}
	
	public Vector3f update() {
		//If we arent moving or the target is invalid, return null
		if(target == null || !isMoving) return null;
		
		//Return the entity's position plus the per-frame movement
		Vector3f pos = entity.getPosition().copy();
		return pos.add(offset);
	}
	
	public void setTarget(Vector3f target) {
		//The new start is the old target
		Vector3f start = this.entity.getPosition().copy();
		start.y = 0;
		
		//The new target is set
		this.target = target.copy();
		
		//THe per-frame movement the entity will have
		this.offset = target.subtract(start);
		float div = 25.0f;
		this.offset.divide(div * getTargetDistance());
		
		//Say that we are moving now
		this.isMoving = true;
	}
	
	public void stop() {
		//Stop movement
		this.isMoving = false;
	}
	
	public boolean reachedTarget() {
		//Check if the entity is within target distance
		return (getTargetDistance() <= minTargetDist);
	}
	
	private float getTargetDistance() {
		//Get positions
		Vector3f targetPos = target.copy();
		Vector3f personPos = entity.getPosition().copy();
		
		//Check and return the distance from the target
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

}
