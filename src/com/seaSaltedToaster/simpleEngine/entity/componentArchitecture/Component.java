package com.seaSaltedToaster.simpleEngine.entity.componentArchitecture;

import com.seaSaltedToaster.simpleEngine.entity.Entity;

public abstract class Component {
	
	public Entity entity;
	
	public abstract void init();
	public abstract void update();
	public abstract void reset();
	
	public abstract String getComponentType();
	public abstract boolean changesRenderer();
	
	public abstract Component copyInstance();
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
