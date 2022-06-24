package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class Building {
	
	//Objects
	private Entity entity;
	
	//Settings
	private boolean isWall = false;

	public Building(Entity entity, boolean isWall) {
		this.entity = entity;
		this.isWall = isWall;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

}
