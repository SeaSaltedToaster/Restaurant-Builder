package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class GiveChefOrder extends Action {
	
	private int orderId = -32767;

	public GiveChefOrder(ItemOrder order) {
		if(order != null)
			this.orderId = order.getId();
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		//Get order
		ItemOrder order = MainApp.restaurant.getOrder(orderId);
		
		if(order == null) return false; //TODO break case
		
		//Change order properties
		MainApp.restaurant.orders.remove(order);
		MainApp.restaurant.chefOrders.add(order);
		
		//Reset tree
		ActionComponent aComp = (ActionComponent) object.getComponent("Action");
		aComp.doTree(true);
		
		//Return
		return true;
	}

	@Override
	public String type() {
		return "GiveChefOrder";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "GIVE_CHEF_ORDER[" + orderId + "]");
	}

	@Override
	public void loadAction(String data) {
		String line = data.replace("[", "").replace("]", "").replace("GIVE_CHEF_ORDER", "").replace(";", "");
		this.orderId = Integer.parseInt(line);
	}

}
