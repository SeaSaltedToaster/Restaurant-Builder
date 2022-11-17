package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class FillOrder extends Action {
	
	//The waiter entity and component
	private Entity waiterEntity;
	private ServerComponent server;
	
	//The chef we are delivering it to, and whether we are on our way
	private ChefComponent chef;
	private boolean orderDelivering = false;
	
	public FillOrder(Entity waiterEntity) {
		this.waiterEntity = waiterEntity;
		this.server = (ServerComponent) waiterEntity.getComponent("Server");
	}

	@Override
	public void start() {
		//Get any available chefs
		Restaurant restaurant = MainApp.restaurant;
		if(restaurant.chefs.size() > 0) {
			//Get the first available chef
			this.chef = restaurant.chefs.get(0); //TODO get any random chef
			
			//Check if they found their station yet
			if(chef.getWorkstation() != null) {
				//Get the waiter's action component
				ActionComponent comp = (ActionComponent) waiterEntity.getComponent("Action");
				
				//Wait and then go to the chef personally
				comp.getActions().add(new WaitAction(3));
				comp.getActions().add(new GoToAction(chef.getEntity().getTransform().getPosition(), waiterEntity, true));
				
				//Wait and then give the chef the order
				comp.getActions().add(new WaitAction(3));
				comp.getActions().add(new GiveChefOrder(server));
				
				//Go back to the waiter station
				comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
				comp.getActions().add(new WaitAction(1));
				
				//Wait for next order, set delivered to true
				comp.getActions().add(new WaitForOrder(waiterEntity));
				this.orderDelivering = true;
				
				//Add exp to the waiter
				Employee employee = (Employee) server;
				employee.addExp(5);
			}
		}
	}

	@Override
	public void update() {
		//If we didnt find a chef, check again
		if(!orderDelivering)
			start();
	}

	@Override
	public boolean isDone() {
		//Return if we found a chef to give an order to
		return orderDelivering;
	}

}
