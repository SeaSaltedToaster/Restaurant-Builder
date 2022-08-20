package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForWorkstation extends Action {

	//Info
	private BuildLayer layer;
	private Entity server, workstation;
		
	public WaitForWorkstation(Entity server) {
		this.server = server;
		BuildingId id = (BuildingId) server.getComponent("BuildingId");
		this.layer = id.getLayer();
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		for(Entity entity : layer.getBuildings()) {
			if(entity.hasComponent("HostStand")) {
				this.workstation = entity;
				ServerComponent serverComp = (ServerComponent) server.getComponent("Server");
				serverComp.setWorkstation(workstation);
				return true;
			}
		}
		return false;
	}

}
