package com.seaSaltedToaster.restaurantGame.ai.person;

import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class WaitAction extends Action {

	//Timer that will count our wait
	private Timer timer;
	
	public WaitAction(float time) {
		this.timer = new Timer(time);
	}
	
	@Override
	public void start() {
		//Start our timer
		this.timer.start();
	}

	@Override
	public void update() {
		//Update current time every frame
		double delta = Window.DeltaTime;
		this.timer.update(delta);
	}

	@Override
	public boolean isDone() {
		//Return finishing state
		return timer.isFinished();
	}

}
