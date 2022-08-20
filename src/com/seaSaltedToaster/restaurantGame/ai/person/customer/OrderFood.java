package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.CustomerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class OrderFood extends Action {

	private TableComponent table;
	
	private Entity customer;
	private int seat;
	
	public OrderFood(TableComponent table, Entity entity, int seat) {
		this.table = table;
		this.customer = entity;
		this.seat = seat;
	}
	
	@Override
	public void start() {
		ItemOrder order = new ItemOrder(table.getEntity(), seat, FoodRegistry.getRandom());
		MainApp.restaurant.orders.add(order);
		
		CustomerComponent comp = (CustomerComponent) customer.getComponent("Customer");
		comp.personOrders.add(order);
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public boolean isDone() {
		return true;
	}

}
