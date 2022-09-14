package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefStation;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForChefStation extends Action {

	//Information on the chef and their new station
	private BuildLayer layer;
	private Entity chef, workstation;
		
	public WaitForChefStation(Entity chef) {
		this.chef = chef;
		
		//Get the chef's information component
		BuildingId id = (BuildingId) chef.getComponent("BuildingId");
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
		//Search all entities until a chef station is found
		for(Entity entity : layer.getBuildings()) {
			if(entity == null) continue;
			
			//We find our chef station component
			if(entity.hasComponent("ChefStation")) {
				//Get chef station component
				ChefStation station = (ChefStation) entity.getComponent("ChefStation");
				
				//If the station is occupied, dont use it
				if(station.isOccupied()) {
					continue;
				}
				
				//Set the workstation as occupied and owned by our chef
				station.setOccupied(true);
				station.setOwner(chef);
				this.workstation = entity;
				
				//Set the chef station of the chef
				ChefComponent chefComp = (ChefComponent) chef.getComponent("Chef");
				chefComp.setWorkstation(workstation);
				System.out.println("Chef has located a workstation");
				
				//Send chef to their station and wait for an order
				ActionComponent comp = (ActionComponent) chef.getComponent("Action");
				comp.getActions().add(new GoToAction(chefComp.getWorkstation().getTransform().getPosition(), chef, false));
				comp.getActions().add(new WaitForChefOrder(chef));
				return true;
			}
		}
		return false;
	}

}
