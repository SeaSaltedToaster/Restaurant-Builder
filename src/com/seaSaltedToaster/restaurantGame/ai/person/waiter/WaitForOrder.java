package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.GoToTable;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class WaitForOrder extends Action {

	//Waiter entity and their component
	private ServerComponent server;
	private Entity waiterEntity;
	
	//The next request / order we are doing
	private ItemOrder order = null;
	private CleanRequest cleanRequest = null; 
	private PayRequest payRequest = null;
	
	public WaitForOrder(Entity waiterEntity) {
		this.order = null;
		this.cleanRequest = null;
		this.payRequest = null;
		
		this.waiterEntity = waiterEntity;
		this.server = (ServerComponent) waiterEntity.getComponent("Server");
	}
	
	@Override
	public void start() {
		//Nothing
	}

	@Override
	public void update() {
		//Main restaurant body with all of our lists
		Restaurant restaurant = MainApp.restaurant;
		
		//Search for paying table requests
		if(foundTask()) return;
		if(restaurant.payRequests.size() > 0) {
			PayRequest request = restaurant.payRequests.get(0);
			this.payRequest = request;
			restaurant.payRequests.remove(request);
			return;
		}

		
		//Search for chef order requests
		if(foundTask()) return;
		if(restaurant.chefOrders.size() > 0) {
			ItemOrder newOrder = restaurant.chefOrders.get(0);
			if(newOrder.isCooked() && !newOrder.isTaken()) {
				this.order = newOrder;
				server.setOrder(order);
				newOrder.setTaken(true);
			} 
			return;
		}
		
		//Search for customer orders
		if(foundTask()) return;
		if(restaurant.orders.size() > 0) {
			this.order = restaurant.orders.get(0);
			restaurant.orders.remove(order);
			return;
		}
		
		//Search for dirty table requests
		if(foundTask()) return;
		if(restaurant.dirtyTables.size() > 0) {
			CleanRequest request = restaurant.dirtyTables.get(0);
			this.cleanRequest = request;
			restaurant.dirtyTables.remove(request);
			return;
		}
	}
	
	private boolean foundTask() {
		return (order != null || cleanRequest != null || payRequest != null);
	}

	@Override
	public boolean isDone() {
		//Whether we found a task to do
		boolean isDone = (order != null || cleanRequest != null || payRequest != null);
		
		if(isDone) {
			//We found one, add the appropriate actions
			ActionComponent comp = (ActionComponent) waiterEntity.getComponent("Action");
			
			//We found a pay request, execute it
			if(payRequest != null) {
				server.executePayBranch(payRequest);
				return true;
			}
			
			if(order != null) {
				if(!order.isCooked()) {
					comp.getActions().add(new GoToAction(order.getTable().getTransform().getPosition(), waiterEntity, true));
					comp.getActions().add(new FillOrder(waiterEntity));
					server.setOrder(order);
					return true;
				}
				
				else if(order.isCooked()) {
					ChefComponent cook = (ChefComponent) order.getChefWhoCooked().getComponent("Chef");
					comp.getActions().add(new GoToAction(cook.getWorkstation().getTransform().getPosition(), waiterEntity, true));
					comp.getActions().add(new WaitAction(1.5f));
					comp.getActions().add(new GrabOrder(order, server));
					comp.getActions().add(new GoToAction(order.getTable().getTransform().getPosition(), waiterEntity, true));
					comp.getActions().add(new WaitAction(1.5f));
					comp.getActions().add(new GiveCustomerOrder(order, server));
					comp.getActions().add(new WaitAction(1.5f));
					comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
					comp.getActions().add(new WaitAction(1.5f));
					comp.getActions().add(new WaitForOrder(waiterEntity));
					return true;
				}
			}

			//We found a table in need of cleaning
			if(cleanRequest != null) {
				comp.getActions().add(new GoToAction(cleanRequest.getTableComp().getEntity().getTransform().getPosition(), waiterEntity, true));
				comp.getActions().add(new WaitAction(1f));
				comp.getActions().add(new CleanTable(cleanRequest));
				comp.getActions().add(new WaitAction(1f));
				comp.getActions().add(new GoToAction(server.getWorkstation().getTransform().getPosition(), waiterEntity, false));
				comp.getActions().add(new WaitForOrder(waiterEntity));
				return true;
			}

		}
		return isDone;
	}

}
