package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.food.Food;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.food.PartyOrder;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class OrderFood extends Action {

	public OrderFood() {
		
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		PartyOrder partyOrder = new PartyOrder();
		PartySeating seating = (PartySeating) object.getComponent("PartySeating");
		
		//Party Leader
		Food food = FoodRegistry.getRandom();
		SeatComponent seat = seating.getSeating().get(object);
		
		ItemOrder order = new ItemOrder(seat.getTable(), seat, food);
		partyOrder.add(seat, order);
		MainApp.restaurant.orders.add(order);
		
		//Return
		return true;
	}

	@Override
	public String type() {
		return "OrderFood";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "ORDER_FOOD_DATA");
	}

	@Override
	public void loadAction(String data) {
		
	}

}
