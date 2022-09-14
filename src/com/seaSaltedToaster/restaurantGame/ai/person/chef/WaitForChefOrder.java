package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForChefOrder extends Action {
	
	//Information on the chef entity and component
	private ChefComponent chef;
	private Entity chefEntity;
	
	//Order that we are getting
	private ItemOrder order = null;
	
	public WaitForChefOrder(Entity chefEntity) {
		this.chefEntity = chefEntity;
		this.chef = (ChefComponent) chefEntity.getComponent("Chef");
	}
	
	@Override
	public void start() {
		//Nothing
	}

	@Override
	public void update() {
		//Check if the logic body has available orders to fill
		Restaurant restaurant = MainApp.restaurant;
		if(restaurant.chefOrders.size() > 0) {
			//Loop all available chef orders
			for(ItemOrder newOrder : restaurant.chefOrders) {
				if(newOrder == null) return;
				
				//If it isnt cooked, cook it!
				if(!newOrder.isCooked()) {
					//We found our order, add it to the chef task
					this.order = newOrder;
					restaurant.chefOrders.remove(order);
					chef.setOrder(order);
					break;
				} else {
					//If cooked, we dont cook it again
				}
			}
		}
	}

	@Override
	public boolean isDone() {
		//If the order is found (not null)
		boolean isDone = (order != null);
		
		//if it is found
		if(isDone) {		
			//Add the next steps to the action list
			ActionComponent comp = (ActionComponent) chefEntity.getComponent("Action");
			comp.getActions().add(new PrepareFood(order, chef));
			comp.getActions().add(new WaitForChefOrder(chefEntity));
			
		}
		
		//Return isDone
		return isDone;
	}

}
