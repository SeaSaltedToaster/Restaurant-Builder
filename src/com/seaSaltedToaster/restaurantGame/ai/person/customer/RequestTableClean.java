package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.CleanRequest;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class RequestTableClean extends Action {
	
	private TableComponent tableComp;
	private int claimedSeat;

	public RequestTableClean(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		CleanRequest request = new CleanRequest(tableComp, claimedSeat);
		MainApp.restaurant.dirtyTables.add(request);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
