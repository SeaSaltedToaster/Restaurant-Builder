package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;

public class GiveChefOrder extends Action {
	
	//Server with the order ready
	private ServerComponent server;

	public GiveChefOrder(ServerComponent server) {
		this.server = server;
	}

	@Override
	public void start() {
		//Add the order to the chef list and remove it from the waiter
		MainApp.restaurant.chefOrders.add(server.getOrder());
		server.setOrder(null);
		
		
		//Add 5 waiter exp
		Employee employee = (Employee) server;
		employee.addExp(5);
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
