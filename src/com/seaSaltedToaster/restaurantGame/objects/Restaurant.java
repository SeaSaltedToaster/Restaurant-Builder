package com.seaSaltedToaster.restaurantGame.objects;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.waiter.CleanRequest;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.PayRequest;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
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
	
	//Locations
	public Vector3f exit;
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
		this.exit = new Vector3f(0.0f);
		this.engine = engine;
	}

}
