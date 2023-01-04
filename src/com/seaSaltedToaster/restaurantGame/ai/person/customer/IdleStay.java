package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class IdleStay extends Action {

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
		return "Idle";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "IDLE_STAY");
	}

	@Override
	public void loadAction(String data) {
		
	}

}
