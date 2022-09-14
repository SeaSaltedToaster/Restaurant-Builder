package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;

public class TakePayment extends Action {
	
	private PayRequest payRequest;
	private ServerComponent server;
	
	public TakePayment(PayRequest payRequest, ServerComponent server) {
		this.payRequest = payRequest;
		this.server = server;
	}

	@Override
	public void start() {
		//TODO pay
		System.out.println("Took payment " + payRequest.getPayAmount());
		
		//Add exp
		Employee employee = (Employee) server;
		employee.addExp(5);
		
		//Add money
		MainApp.restaurant.money += payRequest.getPayAmount();
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
