package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ChefComponent extends Employee {

	public static int chefsCreated = 0;
	
	private ItemOrder order;
	private Entity workstation;
	
	@Override
	public void init() {
		MainApp.restaurant.chefs.add(this);
		super.name = "Chef " + chefsCreated;
		this.model = BuildingList.getBuilding("chef1").getEntity().getModel();
	}

	@Override
	public void update() {
		
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
		return "Chef";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new ChefComponent();
	}

}
