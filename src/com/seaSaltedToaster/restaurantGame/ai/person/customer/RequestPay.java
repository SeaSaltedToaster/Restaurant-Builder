package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.PayRequest;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.CustomerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class RequestPay extends Action {

	private Entity entity;
	private TableComponent table;
	
	private int payAmount = 0;
	
	public RequestPay(Entity entity, TableComponent table) {
		this.entity = entity;
		this.table = table;
	}
	
	@Override
	public void start() {
		CustomerComponent comp = (CustomerComponent) entity.getComponent("Customer");
		for(ItemOrder order : comp.personOrders) {
			payAmount += order.getFoodItem().price;
		}
		System.out.println("Paid " + payAmount); //TODO give money
		
		PayRequest request = new PayRequest(table, payAmount);
		MainApp.restaurant.payRequests.add(request);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
