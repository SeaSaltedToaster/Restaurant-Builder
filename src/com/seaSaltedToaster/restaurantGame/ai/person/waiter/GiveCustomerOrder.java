package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class GiveCustomerOrder extends Action {

	private ItemOrder order;
	
	public GiveCustomerOrder(ItemOrder order, Entity waiterEntity) {
		this.order = order;
	}

	@Override
	public void start() {
		TableComponent table = (TableComponent) order.getTable().getComponent("Table");
		table.setFood(order.getFoodItem().getVao(), order.getTableSpot());
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
