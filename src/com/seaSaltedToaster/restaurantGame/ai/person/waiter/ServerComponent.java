package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.PayRequest;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.TakePayment;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.WaitForOrder;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ServerComponent extends Employee {
	
	//Universal number of waiters in the world
	public static int waitersCreated = 0;

	//Order to waiter is executing
	private ItemOrder order;
	
	//Workstation the waiter is bound to
	private Entity workstation;
		
	@Override
	public void init() {
		MainApp.restaurant.servers.add(this);
		super.name = "Waiter " + waitersCreated++;
		this.model = BuildingList.getBuilding("Waiter").getEntity().getModel();
	}
	
	@Override
	public void update() {
		//Superclass method update
		super.increaseXP();
		
		//Check for station
		BuildLayer layer = MainApp.restaurant.layers.get(0);
		ActionComponent actionComp = (ActionComponent) this.entity.getComponent("Action");
		
		//Check if
//		if( (!layer.getBuildings().contains(workstation) || workstation == null) && (actionComp.getCurAction() == null || actionComp.getCurAction().getClass() != LocateHostStand.class) ) {
//			actionComp.startBehaviourTree();
//		}
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
	
	public void executePayBranch(PayRequest payRequest) {
		ActionComponent comp = (ActionComponent) this.entity.getComponent("Action");
		comp.getActions().add(new GoToAction(payRequest.getTable().getEntity().getTransform().getPosition(), this.entity, true));
		comp.getActions().add(new TakePayment(payRequest, this));
		comp.getActions().add(new WaitForOrder(this.entity));
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
