package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.ai.person.chef.FinishCooking;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ChefComponent extends Employee {

	//Static data
	public static int chefsCreated = 0;
	
	//Individual data
	private ItemOrder order;
	private Entity workstation;
	
	@Override
	public void init() {
		MainApp.restaurant.chefs.add(this);
		super.name = "Chef " + chefsCreated++;
		this.model = BuildingList.getBuilding("Chef").getEntity().getModel();
	}
	
	public void deliverOrder(ItemOrder order) {
		int cookTime = (int) order.getCookingTime(); //TODO cook times
		
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		comp.getActions().add(new WaitAction(cookTime));
		comp.getActions().add(new FinishCooking(order.getId()));
	}
	
	@Override
	public void fireEmployee() {
		//Send order back
		Restaurant restaurant = MainApp.restaurant;
		if(order != null) {
			order.setCooked(false);
			restaurant.chefOrders.add(order);
		}
		restaurant.chefs.remove(this);
		
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
