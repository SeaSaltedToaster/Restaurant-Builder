package com.seaSaltedToaster.restaurantGame.objects.food;

import java.util.HashMap;

import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class PartyOrder extends Component {
	
	private HashMap<SeatComponent, ItemOrder> orders;

	public PartyOrder() {
		this.orders = new HashMap<SeatComponent, ItemOrder>();
	}
	
	public void add(SeatComponent seat, ItemOrder order) {
		this.orders.put(seat, order);
	}

	public HashMap<SeatComponent, ItemOrder> getOrders() {
		return orders;
	}

	public void setOrders(HashMap<SeatComponent, ItemOrder> orders) {
		this.orders = orders;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "PartyOrder";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return null;
	}

}
