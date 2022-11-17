package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;

public class GiveCustomerOrder extends Action {

	//Order we and giving and the waiter who is giving it
	private ItemOrder order;
	private ServerComponent server;
	
	public GiveCustomerOrder(ItemOrder order, ServerComponent server) {
		this.order = order;
		this.server = server;
	}

	@Override
	public void start() {
		//Get the table object and set the food at the spot to be filled
		TableComponent table = (TableComponent) order.getTable().getComponent("Table");
		table.setFood(order.getFoodItem().getVao(), order.getTableSpot());
		
		//Add waiter exp
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
