package com.seaSaltedToaster.restaurantGame.ai.person.chef;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class PrepareFood extends Action {

	//Cooking steps
	private Timer timer;
	private ItemOrder order;
	
	public PrepareFood(ItemOrder order) {
		this.order = order;
		this.timer = new Timer(order.getCookingTime());

	}
	
	@Override
	public void start() {
		//TODO add steps for cooking food
		this.timer.start();
	}

	@Override
	public void update() {
		double delta = Window.DeltaTime * 1000.0f;
		this.timer.update(delta);
	}

	@Override
	public boolean isDone() {
		boolean isDone = timer.isFinished();
		if(isDone) {
			//Give food to waiter list
			this.order.setCooked(true);
			Restaurant restaurant = MainApp.restaurant;
			restaurant.chefOrders.add(order);
			System.out.println("Finished cooking order");
		}
		return isDone;
	}

}
