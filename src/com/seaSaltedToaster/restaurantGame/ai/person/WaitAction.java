package com.seaSaltedToaster.restaurantGame.ai.person;

import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class WaitAction extends Action {

	//Timing
	private Timer timer;
	
	public WaitAction(float time) {
		this.timer = new Timer(time);
	}
	
	@Override
	public void start() {
		this.timer.start();
	}

	@Override
	public void update() {
		double delta = Window.DeltaTime;
		this.timer.update(delta);
	}

	@Override
	public boolean isDone() {
		return timer.isFinished();
	}

}
