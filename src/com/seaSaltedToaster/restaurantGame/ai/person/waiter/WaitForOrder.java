package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class WaitForOrder extends Action {

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
		
		for(ItemOrder order : restaurant.orders) {
			if(order == null) continue;

			boolean canDeliver = (!order.isDelivered() && order.isCooked());
			if(canDeliver) {
				ServerComponent comp = (ServerComponent) object.getComponent("Server");
				comp.deliverOrder(order);
				order.setDelivered(true);
				return true;
			}
			
			boolean isValid = canTake(order);
			if(isValid) {
				ServerComponent comp = (ServerComponent) object.getComponent("Server");
				comp.takeOrder(order);
				order.setTaken(true);
				return true;
			}
		}
		
		return false;
	}

	private boolean canTake(ItemOrder order) {
		return (order != null && !order.isTaken() && order.getTable() != null);
	}

	@Override
	public String type() {
		return "WaitForOrder";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "WAIT_FOR_ORDER[]");
	}

	@Override
	public void loadAction(String data) {
		ActionComponent comp = (ActionComponent) object.getComponent("Action");
		comp.getActions().clear();
	}

}
