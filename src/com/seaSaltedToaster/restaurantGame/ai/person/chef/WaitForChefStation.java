package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForChefStation extends Action {

	//Info
	private BuildLayer layer;
	private Entity chef, workstation;
		
	public WaitForChefStation(Entity chef) {
		this.chef = chef;
		BuildingId id = (BuildingId) chef.getComponent("BuildingId");
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
			if(entity.hasComponent("ChefStation")) {
				this.workstation = entity;
				ChefComponent chefComp = (ChefComponent) chef.getComponent("Chef");
				chefComp.setWorkstation(workstation);
				System.out.println("Chef has located a workstation");
				return true;
			}
		}
		return false;
	}

}
