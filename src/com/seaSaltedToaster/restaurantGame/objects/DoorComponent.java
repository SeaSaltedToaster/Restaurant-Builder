package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class DoorComponent extends Component {

	//Timing
	private Timer timer;
	private int waitTime = 1;
	
	@Override
	public void init() {
		this.timer = new Timer(waitTime);
		this.timer.start();
	}

	@Override
	public void update() {
		this.timer.update(Window.DeltaTime);
		
		Restaurant restaurant = MainApp.restaurant;
		if(timer.isFinished() && !restaurant.atCapacity() && restaurant.capacity() > 0) {
			spawnPerson(restaurant); //TODO SPAWN PEOPLE
		}
	}

	private void spawnPerson(Restaurant restaurant) {
		//Random
		float random = (float) Math.abs(Math.random());
		if(random < 0.75f) {
			timer.stop();
			timer.start();
			return;
		}
		
		//Create entity
		Building spawn = BuildingList.getBuilding("Customer_TEST");
		Entity newEnt = spawn.getEntity().copyEntity();
		newEnt.getTransform().setPosition(entity.getTransform().getPosition().copy());
		
		//Get layer
		restaurant.layers.get(0).addBuilding(newEnt, spawn, -127);
		
		//Timer
		timer.stop();
		timer.start();		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getComponentType() {
		return "Door";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new DoorComponent();
	}

}
