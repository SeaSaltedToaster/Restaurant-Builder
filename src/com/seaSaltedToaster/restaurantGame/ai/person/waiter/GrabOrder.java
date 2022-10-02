package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;

public class GrabOrder extends Action {

	//The waiter grabbing the order, and the order itself
	private ServerComponent server;
	private ItemOrder order;
	
	public GrabOrder(ItemOrder order, ServerComponent server) {
		this.order = order;
		this.server = server;
	}

	@Override
	public void start() {
		//Set to be on delivery
		order.setDelivered(true);
		
		//Remove it from the chef list
		MainApp.restaurant.chefOrders.remove(order);
		
		//Add the order to the server
		server.setOrder(order);
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//Nothing, all is done in start(), return true
		return true;
	}

}
