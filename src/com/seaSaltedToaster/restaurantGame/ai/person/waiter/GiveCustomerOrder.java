package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class GiveCustomerOrder extends Action {

	private ItemOrder order;
	private ServerComponent server;
	
	public GiveCustomerOrder(ItemOrder order, ServerComponent server) {
		this.order = order;
		this.server = server;
	}

	@Override
	public void start() {
		TableComponent table = (TableComponent) order.getTable().getComponent("Table");
		table.setFood(order.getFoodItem().getVao(), order.getTableSpot());
		
		Employee employee = (Employee) server;
		employee.addExp(5);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
