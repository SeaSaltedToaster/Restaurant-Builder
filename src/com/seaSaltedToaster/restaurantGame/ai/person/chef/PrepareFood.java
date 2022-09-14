package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class PrepareFood extends Action {

	//Cooking steps
	private Timer timer;
	private ItemOrder order;
	
	//Our chef component
	private ChefComponent chef;
	
	public PrepareFood(ItemOrder order, ChefComponent chef) {
		this.order = order;
		this.timer = new Timer(order.getCookingTime());
		this.chef = chef;

	}
	
	@Override
	public void start() {
		//Start cook timer for the chef
		this.timer.start();
	}

	@Override
	public void update() {
		//Update timer every frame
		double delta = Window.DeltaTime;
		this.timer.update(delta);
	}

	@Override
	public boolean isDone() {
		//Check if the timer is done
		boolean isDone = timer.isFinished();
		
		//If it is
		if(isDone) {
			//Give cooked food to waiter list
			this.order.setCooked(true);
			this.order.setChefWhoCooked(chef.getEntity());
			
			//Add the order back to the restaurant logic body
			Restaurant restaurant = MainApp.restaurant;
			restaurant.chefOrders.add(order);
			this.chef.setOrder(null);
			
			//Add employee exp
			Employee employee = (Employee) chef;
			employee.addExp(15);
		}
		
		//Return isDone
		return isDone;
	}

}
