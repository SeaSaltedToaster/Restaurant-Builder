package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ServerComponent extends Employee {
	
	public static int waitersCreated = 0;

	private ItemOrder order;
	private Entity workstation;
		
	@Override
	public void init() {
		MainApp.restaurant.servers.add(this);
		super.name = "Waiter " + waitersCreated++;
		this.model = BuildingList.getBuilding("Waiter").getEntity().getModel();
	}
	
	@Override
	public void fireEmployee() {
		//Remove tasks and restaurant logic
		Restaurant restaurant = MainApp.restaurant;
		if(order != null) {
			restaurant.orders.add(order);
		}
		restaurant.servers.remove(this);
		
		//Delete entity
		BuildingId id = (BuildingId) this.entity.getComponent("BuildingId");
		id.getLayer().remove(entity);
	}
	
	public Entity getWorkstation() {
		return workstation;
	}

	public void setWorkstation(Entity workstation) {
		this.workstation = workstation;
	}

	public ItemOrder getOrder() {
		return order;
	}

	public void setOrder(ItemOrder order) {
		this.order = order;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Server";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new ServerComponent();
	}

}
