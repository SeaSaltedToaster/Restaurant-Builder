package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class FinishCooking extends Action {
	
	private int orderId;

	public FinishCooking(int id) {
		this.orderId = id;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		ItemOrder order = MainApp.restaurant.getChefOrder(orderId);
		if(order == null) return false;
		
		//Change order properties
		order.setCooked(true);
		MainApp.restaurant.chefOrders.remove(order);
		MainApp.restaurant.orders.add(order);
		System.out.println("cooked");

		//Reset tree
		ActionComponent aComp = (ActionComponent) object.getComponent("Action");
		aComp.doTree(true);
		
		//Return
		return true;
	}

	@Override
	public String type() {
		return null;
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "TAKE_ORDER[" + orderId + "]");
	}

	@Override
	public void loadAction(String data) {
		String line = data.replace("[", "").replace("]", "").replace("TAKE_ORDER", "").replace(";", "");
		this.orderId = Integer.parseInt(line);
	}

}
