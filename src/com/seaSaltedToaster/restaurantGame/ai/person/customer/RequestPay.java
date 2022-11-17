package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.PayRequest;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.CustomerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class RequestPay extends Action {
	
	//The customer paying and their table where the payment is laid
	private Entity entity;
	private TableComponent table;
		
	public RequestPay(Entity entity, TableComponent table) {
		this.entity = entity;
		this.table = table;
	}
	
	@Override
	public void start() {
		//Add randomized pay amount
		int payAmount = 25; 
		payAmount += Math.abs(Math.random() * 25);
		
		//Add money for each order by the customer
		CustomerComponent comp = (CustomerComponent) entity.getComponent("Customer");
		for(ItemOrder order : comp.personOrders) {
			payAmount += order.getFoodItem().getPrice();
		}
		
		//Add the pay request so the waiters take it
		PayRequest request = new PayRequest(table, payAmount);
		MainApp.restaurant.payRequests.add(request);
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//All is done in start(), return true
		return true;
	}

}
