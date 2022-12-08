package com.seaSaltedToaster.restaurantGame.objects;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.simpleEngine.Engine;

public class Restaurant {
	
	//Customers
	public List<TableComponent> tables;
	public List<SeatComponent> chair;

	//Servers
	public List<ServerComponent> servers;
	public List<ItemOrder> orders;
	
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
		this.chair = new ArrayList<SeatComponent>();
		
		this.servers = new ArrayList<ServerComponent>();
		this.orders = new ArrayList<ItemOrder>();
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
