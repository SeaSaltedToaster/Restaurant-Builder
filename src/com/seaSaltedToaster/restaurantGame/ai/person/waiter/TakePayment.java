package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;

public class TakePayment extends Action {
	
	private PayRequest payRequest;

	public TakePayment(PayRequest payRequest) {
		this.payRequest = payRequest;
	}

	@Override
	public void start() {
		//TODO pay
		System.out.println("Took payment " + payRequest.getPayAmount());
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
