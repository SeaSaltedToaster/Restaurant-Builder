package com.seaSaltedToaster.restaurantGame.objects;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.CleanRequest;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.PayRequest;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.ServerComponent;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Restaurant {
	
	//Customers
	public List<TableComponent> tables;
	
	//Servers
	public List<ServerComponent> servers;
	public List<ItemOrder> orders;
	public List<CleanRequest> dirtyTables;
	public List<PayRequest> payRequests;
	
	//Chefs
	public List<ChefComponent> chefs;
	public List<ItemOrder> chefOrders;
	
	//Layers
	public List<BuildLayer> layers;
	
	//Money
	public int money = 0;
	
	//Locations
	public Engine engine;

	public Restaurant(Engine engine) {
		this.layers = new ArrayList<BuildLayer>();
		this.tables = new ArrayList<TableComponent>();
		this.servers = new ArrayList<ServerComponent>();
		this.orders = new ArrayList<ItemOrder>();
		this.dirtyTables = new ArrayList<CleanRequest>();
		this.payRequests = new ArrayList<PayRequest>();
		this.chefs = new ArrayList<ChefComponent>();
		this.chefOrders = new ArrayList<ItemOrder>();
		this.money = 2500;
		this.engine = engine;
	}

	public void update() {
		for(ChefComponent chef : chefs) {
			chef.update();
		}
		for(ServerComponent server : servers) {
			server.update();
		}
	}
	
	public boolean isBankrupt() {
		return money < 0;
	}

	public boolean atCapacity() {
		for(TableComponent table : tables) {
			if(!table.isTaken()) {
				return false;
			}
		}
		return true;
	}

}
