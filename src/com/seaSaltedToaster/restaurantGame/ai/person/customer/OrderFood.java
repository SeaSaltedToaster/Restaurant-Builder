package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.CustomerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class OrderFood extends Action {

	//The table and seat to order to
	private TableComponent table;
	private int seat;
	
	//The entity ordering the food
	private Entity customer;
	
	public OrderFood(TableComponent table, Entity entity, int seat) {
		this.table = table;
		this.customer = entity;
		this.seat = seat;
	}
	
	@Override
	public void start() {
		//Create and add a new random food order
		ItemOrder order = new ItemOrder(table.getEntity(), seat, FoodRegistry.getRandom());
		MainApp.restaurant.orders.add(order);
		
		//Add the order to the customer component so they pay later
		CustomerComponent comp = (CustomerComponent) customer.getComponent("Customer");
		comp.personOrders.add(order);
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
