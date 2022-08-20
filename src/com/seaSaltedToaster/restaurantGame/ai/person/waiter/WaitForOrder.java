package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.GoToTable;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForOrder extends Action {

	//Waiter
	private ServerComponent server;
	private Entity waiterEntity;
	
	//Order
	private ItemOrder order = null;
	private CleanRequest cleanRequest = null; 
	private PayRequest payRequest = null;
	
	public WaitForOrder(Entity waiterEntity) {
		this.order = null;
		this.cleanRequest = null;
		this.payRequest = null;
		this.waiterEntity = waiterEntity;
	}
	
	@Override
	public void start() {
		this.server = (ServerComponent) waiterEntity.getComponent("Server");
	}

	@Override
	public void update() {
		Restaurant restaurant = MainApp.restaurant;
		
		//Dirty tables
		if(order != null || cleanRequest != null || payRequest != null) return;
		if(restaurant.payRequests.size() > 0) {
			PayRequest request = restaurant.payRequests.get(0);
			this.payRequest = request;
			restaurant.payRequests.remove(request);
			return;
		}

		
		//Chef order
		if(order != null || cleanRequest != null || payRequest != null) return;
		if(restaurant.chefOrders.size() > 0) {
			ItemOrder newOrder = restaurant.chefOrders.get(0);
			if(newOrder.isCooked() && !newOrder.isTaken()) {
				this.order = newOrder;
				server.setOrder(order);
				newOrder.setTaken(true);
			} 
			return;
		}
		
		//Customer order
		if(order != null || cleanRequest != null || payRequest != null) return;
		if(restaurant.orders.size() > 0) {
			this.order = restaurant.orders.get(0);
			restaurant.orders.remove(order);
			return;
		}
		
		//Dirty tables
		if(order != null || cleanRequest != null || payRequest != null) return;
		if(restaurant.dirtyTables.size() > 0) {
			CleanRequest request = restaurant.dirtyTables.get(0);
			this.cleanRequest = request;
			restaurant.dirtyTables.remove(request);
			return;
		}
	}

	@Override
	public boolean isDone() {
		boolean isDone = (order != null || cleanRequest != null || payRequest != null);
		if(isDone) {
			ActionComponent comp = (ActionComponent) waiterEntity.getComponent("Action");
			if(payRequest != null) {
				comp.getActions().add(new GoToTable(waiterEntity, payRequest.getTable().getEntity()));
				comp.getActions().add(new TakePayment(payRequest));
				comp.getActions().add(new WaitForOrder(waiterEntity));
			} else if(cleanRequest != null) {
				comp.getActions().add(new GoToTable(waiterEntity, cleanRequest.getTableComp().getEntity()));
				comp.getActions().add(new WaitAction(1f));
				comp.getActions().add(new CleanTable(cleanRequest));
				comp.getActions().add(new WaitAction(1f));
				comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
				comp.getActions().add(new WaitForOrder(waiterEntity));
			} else if(!order.isCooked()) {
				comp.getActions().add(new GoToTable(waiterEntity, order.getTable()));
				comp.getActions().add(new FillOrder(waiterEntity));
				server.setOrder(order);
			} else if(order.isCooked()) {
				comp.getActions().add(new GoToAction(order.getCookingLocation(), waiterEntity, true));
				comp.getActions().add(new WaitAction(1.5f));
				comp.getActions().add(new GrabOrder(order));
				comp.getActions().add(new GoToAction(order.getTable().getTransform().getPosition(), waiterEntity, true));
				comp.getActions().add(new WaitAction(1.5f));
				comp.getActions().add(new GiveCustomerOrder(order, waiterEntity));
				comp.getActions().add(new WaitAction(1.5f));
				comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
				comp.getActions().add(new WaitAction(1.5f));
				comp.getActions().add(new WaitForOrder(waiterEntity));
			}

		}
		return isDone;
	}

}
