package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class OrderFood extends Action {

	public OrderFood() {
		
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public String type() {
		return "OrderFood";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "ORDER_FOOD_DATA");
	}

	@Override
	public void loadAction(String data) {
		
	}

}
