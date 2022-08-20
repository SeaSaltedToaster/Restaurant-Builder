package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class FillOrder extends Action {
	
	//Objects
	private Entity waiterEntity;
	private ServerComponent server;
	private ChefComponent chef;
	private boolean orderDelivering = false;
	
	public FillOrder(Entity waiterEntity) {
		this.waiterEntity = waiterEntity;
		this.server = (ServerComponent) waiterEntity.getComponent("Server");
	}

	@Override
	public void start() {
		Restaurant restaurant = MainApp.restaurant;
		if(restaurant.chefs.size() > 0) {
			this.chef = restaurant.chefs.get(0); //TODO get any random chef
			if(chef.getWorkstation() != null) {
				ActionComponent comp = (ActionComponent) waiterEntity.getComponent("Action");
				comp.getActions().add(new WaitAction(3));
				comp.getActions().add(new GoToAction(chef.getEntity().getTransform().getPosition(), waiterEntity, true));
				comp.getActions().add(new WaitAction(3));
				comp.getActions().add(new GiveChefOrder(chef, server, waiterEntity));
				comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
				comp.getActions().add(new WaitAction(1));
				comp.getActions().add(new WaitForOrder(waiterEntity));
				this.orderDelivering = true;
				System.out.println("Getting order for chef");
			}
		}
	}

	@Override
	public void update() {
		if(!orderDelivering)
			start();
	}

	@Override
	public boolean isDone() {
		System.out.println("Going to chef");
		return orderDelivering;
	}

}
