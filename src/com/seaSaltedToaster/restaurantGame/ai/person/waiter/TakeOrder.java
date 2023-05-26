package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;

public class TakeOrder extends Action {
	
	public static int TAKE_ORDER_WAIT = 5;
	
	private ItemOrder order;
	private int orderId = -1;
	
	public TakeOrder(ItemOrder order) {
		if(order == null) return;
		this.order = order;
		this.orderId = order.getId();
	}

	@Override
	public void start() {
		if(order == null) {
			this.order = MainApp.restaurant.getOrder(orderId);
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		//Meet preconditions
		if(order == null || !meetsPreconditions()) return false;
		
		//Change the values of the ItemOrder
		this.order.setTaken(true);
		
		//Add the next few tasks
		Restaurant rest = MainApp.restaurant;
		ActionComponent aComp = (ActionComponent) object.getComponent("Action");
		aComp.getActions().add(new WaitAction(TAKE_ORDER_WAIT));
		aComp.getActions().add(new GoToAction(rest.getChef().getPosition(), object, false));
		aComp.getActions().add(new GiveChefOrder(order));
		
		//Return
		return true;
	}

	private boolean meetsPreconditions() {
		Restaurant rest = MainApp.restaurant;
		boolean hasChef = (rest.chefs.size() > 0);
		return hasChef;
	}

	@Override
	public String type() {
		return "TakeOrder";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getId();

		if(order == null)
			system.saveAction(index, super.actionIndex, type(), "TAKE_ORDER[" + -32767 + "]");
		else
			system.saveAction(index, super.actionIndex, type(), "TAKE_ORDER[" + order.getId() + "]");
	}

	@Override
	public void loadAction(String data) {
		String line = data.replace("[", "").replace("]", "").replace("TAKE_ORDER", "").replace(";", "");
		this.orderId = Integer.parseInt(line);
	}

}
