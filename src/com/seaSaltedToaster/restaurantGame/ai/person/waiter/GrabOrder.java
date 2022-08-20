package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;

public class GrabOrder extends Action {

	private ItemOrder order;
	
	public GrabOrder(ItemOrder order) {
		this.order = order;
	}

	@Override
	public void start() {
		order.setDelivered(true);
		MainApp.restaurant.chefOrders.remove(order);
	}

	@Override
	public void update() {

	}

	@Override
	public boolean isDone() {
		return true;
	}

}
