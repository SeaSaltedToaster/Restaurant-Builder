package com.seaSaltedToaster.restaurantGame.ai.person.waiter;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class CleanTable extends Action {

	private CleanRequest cleanRequest;
	
	public CleanTable(CleanRequest cleanRequest) {
		this.cleanRequest = cleanRequest;
	}

	@Override
	public void start() {
		TableComponent table = cleanRequest.getTableComp();
		table.setFood(null, cleanRequest.getClaimedSeat());
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
