package com.seaSaltedToaster.restaurantGame.objects.people;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class CustomerComponent extends Component {
	
	public List<ItemOrder> personOrders;

	@Override
	public void init() {
		this.personOrders = new ArrayList<ItemOrder>();
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Customer";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new CustomerComponent();
	}

}
