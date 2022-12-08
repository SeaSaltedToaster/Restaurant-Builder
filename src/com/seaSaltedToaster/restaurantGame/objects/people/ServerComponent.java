package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ServerComponent extends Employee {
	
	@Override
	public void init() {
		MainApp.restaurant.servers.add(this);
		super.name = "Cum";
		this.model = BuildingList.getBuilding("Waiter").getEntity().getModel();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return "Server";
	}

	@Override
	public boolean changesRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Component copyInstance() {
		// TODO Auto-generated method stub
		return new ServerComponent();
	}

	@Override
	public void fireEmployee() {
		PathfinderComponent pthComp = (PathfinderComponent) this.entity.getComponent("Pathfinder");
		pthComp.goTo(new Vector3f(5,0,0));
	}

}
