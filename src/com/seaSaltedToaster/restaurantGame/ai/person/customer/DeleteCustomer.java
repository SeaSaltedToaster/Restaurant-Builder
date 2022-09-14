package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class DeleteCustomer extends Action {
	
	//The entity we want to delete
	private Entity customer;
	
	public DeleteCustomer(Entity customer) {
		this.customer = customer;
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
		//Get the customer's id and build layer
		BuildingId id = (BuildingId) customer.getComponent("BuildingId");
		
		//Get their index in the building list and delete then (set to null)
		int index = id.getLayer().getBuildings().indexOf(customer);
		id.getLayer().getBuildings().set(index, null);
		
		//Return true, as it will always be done instantly in one take
		return true;
	}

}
