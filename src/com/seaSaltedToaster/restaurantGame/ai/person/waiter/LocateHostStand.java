package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class LocateHostStand extends Action {

	//Objects
	private boolean foundStation = false;
	private Entity hostStand;
	
	//Server
	private Entity waiter;
	
	public LocateHostStand(Entity waiter) {
		this.waiter = waiter;
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		//Get layer
		BuildLayer layer = MainApp.restaurant.layers.get(0);
		
		//Search all buildings on this layer
		for(Entity entity : layer.getBuildings()) {
			
			//Check if they have a host stand component
			if(entity.hasComponent("HostStand")) {
				
				//It does, no add the waiter to it
				this.hostStand = entity;
				ServerComponent serverComp = (ServerComponent) waiter.getComponent("Server");
				serverComp.setWorkstation(hostStand);
				
				//Continue in branch
				ActionComponent actionComp = (ActionComponent) waiter.getComponent("Action");
				actionComp.getActions().add(1, new GoToAction(hostStand.getPosition(), waiter, false));
				
				//Exit and return
				this.foundStation = true;
				return;
			}
		}
	}

	@Override
	public boolean isDone() {
		return foundStation;
	}

}
