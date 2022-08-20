package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class DeleteCustomer extends Action {

	private Entity customer;
	
	public DeleteCustomer(Entity customer) {
		this.customer = customer;
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		//TODO remove person
		BuildingId id = (BuildingId) customer.getComponent("BuildingId");
		int index = id.getLayer().getBuildings().indexOf(customer);
		id.getLayer().getBuildings().set(index, null);
		return true;
	}

}
