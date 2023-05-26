package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class WaitToCook extends Action {

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		ActionComponent aComp = (ActionComponent) object.getComponent("Action");
		if(aComp.getActions().size() > 1)
		{
			return true;
		}
		
		Restaurant restaurant = MainApp.restaurant;
		for(ItemOrder order : restaurant.chefOrders) {
			if(order == null) continue;
			boolean canDeliver = (order.isTaken() && !order.isCooked());
			if(canDeliver) {
				ChefComponent comp = (ChefComponent) object.getComponent("Chef");
				comp.deliverOrder(order);
				return true;
			}
		}
		return false;
	}


	@Override
	public String type() {
		return "WaitToCook";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "WAIT_TO_COOK[]");
	}

	@Override
	public void loadAction(String data) {
		
	}

}
