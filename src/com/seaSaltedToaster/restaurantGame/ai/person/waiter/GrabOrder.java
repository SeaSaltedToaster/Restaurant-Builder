package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;

public class GrabOrder extends Action {

	private ServerComponent server;
	private ItemOrder order;
	
	public GrabOrder(ItemOrder order, ServerComponent server) {
		this.order = order;
		this.server = server;
	}

	@Override
	public void start() {
		order.setDelivered(true);
		MainApp.restaurant.chefOrders.remove(order);
		server.setOrder(order);
	}

	@Override
	public void update() {

	}

	@Override
	public boolean isDone() {
		return true;
	}

}
