package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class GiveChefOrder extends Action {
	
	private ServerComponent server;

	public GiveChefOrder(ChefComponent chef, ServerComponent server, Entity waiterEntity) {
		this.server = server;
	}

	@Override
	public void start() {
		MainApp.restaurant.chefOrders.add(server.getOrder());
		server.setOrder(null);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
