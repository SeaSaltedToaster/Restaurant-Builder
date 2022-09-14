package com.seaSaltedToaster.restaurantGame.ai.person;

public abstract class Action {
	
	//Two action methods
	public abstract void start();
	public abstract void update();
	
	//Check if action is over
	public abstract boolean isDone();

}
