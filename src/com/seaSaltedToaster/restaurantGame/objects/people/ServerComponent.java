package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.GiveTableOrder;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.TakeOrder;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ServerComponent extends Employee {
	
	private static final float GET_ORDER_WAIT = 3;

	@Override
	public void init() {
		MainApp.restaurant.servers.add(this);
		super.name = "Waiter";
		this.model = BuildingList.getBuilding("Waiter").getEntity().getModel();
	}
	
	public void takeOrder(ItemOrder order) {
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		
		Vector3f tablePosition = order.getTable().entity.getPosition();
		comp.getActions().add(new WaitAction(GET_ORDER_WAIT));
		comp.getActions().add(new GoToAction(tablePosition, entity, true));
		comp.getActions().add(new TakeOrder(order));
	}
	
	public void deliverOrder(ItemOrder order) {
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		
		Vector3f tablePosition = order.getTable().entity.getPosition();
		comp.getActions().add(new GoToAction(MainApp.restaurant.getChef().getPosition(), entity, true));
		comp.getActions().add(new WaitAction(GET_ORDER_WAIT));
		comp.getActions().add(new GoToAction(tablePosition, entity, true));
		comp.getActions().add(new GiveTableOrder(order));
	}

	@Override
	public String getComponentType() {
		return "Server";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new ServerComponent();
	}

	@Override
	public void fireEmployee() {
		
	}

}
