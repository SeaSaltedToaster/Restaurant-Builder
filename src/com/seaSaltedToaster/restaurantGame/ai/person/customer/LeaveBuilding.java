package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class LeaveBuilding extends Action {

	private Entity person;
	
	//Target
	private Vector3f target = null;
	
	public LeaveBuilding(Entity person) {
		this.person = person;
	}

	@Override
	public void start() {
		for(BuildLayer layer : MainApp.restaurant.layers) {
			for(Entity entity : layer.getBuildings()) {
				if(entity.hasComponent("Door")) {
					target = entity.getTransform().getPosition().copy();
					System.out.println("Exit found at " + target.toString());
					return;
				}
			}
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		if(target == null) {
			start();
		}
		boolean isTargetFound = (target != null);
		if(isTargetFound) {
			ActionComponent comp = (ActionComponent) person.getComponent("Action");
			GoToAction action = new GoToAction(target.scale(-1.0f), person, true);
			comp.getActions().set(0, action);
			comp.getActions().add(new DeleteCustomer(person));
			return true;
		}
		return isTargetFound;
	}

}
