package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForWorkstation extends Action {

	//Layer the workstation is on
	private BuildLayer layer;
	
	//The server and their new station once we find one
	private Entity server, workstation;
		
	public WaitForWorkstation(Entity server) {
		this.server = server;
		
		BuildingId id = (BuildingId) server.getComponent("BuildingId");
		this.layer = id.getLayer();
	}
	
	@Override
	public void start() {
		//Nothing
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//Search all buildings on this layer
		for(Entity entity : layer.getBuildings()) {
			//Check if they have a host stand component
			if(entity.hasComponent("HostStand")) {
				//It does, no add the waiter to it
				this.workstation = entity;
				ServerComponent serverComp = (ServerComponent) server.getComponent("Server");
				serverComp.setWorkstation(workstation);
				return true;
			}
		}
		//We didnt find one :/
		return false;
	}

}
