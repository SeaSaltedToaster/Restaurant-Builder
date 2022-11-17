package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;

public class TakePayment extends Action {
	
	//Pay request and the server filling the request
	private PayRequest payRequest;
	private ServerComponent server;
	
	public TakePayment(PayRequest payRequest, ServerComponent server) {
		this.payRequest = payRequest;
		this.server = server;
	}

	@Override
	public void start() {		
		//Add exp to waiter
		Employee employee = (Employee) server;
		employee.addExp(5);
		
		//Add money to the restaurant body
		MainApp.restaurant.money += payRequest.getPayAmount();
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
