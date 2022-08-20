package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForChefOrder extends Action {
	
	//Waiter
	private ChefComponent chef;
	private Entity chefEntity;
	
	//Order
	private ItemOrder order = null;
	
	public WaitForChefOrder(Entity chefEntity) {
		this.order = null;
		this.chefEntity = chefEntity;
	}
	
	@Override
	public void start() {
		this.chef = (ChefComponent) chefEntity.getComponent("Chef");
	}

	@Override
	public void update() {
		Restaurant restaurant = MainApp.restaurant;
		if(restaurant.chefOrders.size() > 0) {
			for(ItemOrder newOrder : restaurant.chefOrders) {
				if(!newOrder.isCooked()) {
					System.out.println("Starting to cook order");
					this.order = newOrder;
					this.order.setCookingLocation(chef.getWorkstation().getTransform().getPosition());
					restaurant.chefOrders.remove(order);
					chef.setOrder(order);
					break;
				} else {
					//if cooked, give to waiter
				}
			}
		}
	}

	@Override
	public boolean isDone() {
		boolean isDone = (order != null);
		if(isDone) {		
			ActionComponent comp = (ActionComponent) chefEntity.getComponent("Action");
			comp.getActions().add(new PrepareFood(order));
			comp.getActions().add(new WaitForChefOrder(chefEntity));
			
		}
		return isDone;
	}

}
