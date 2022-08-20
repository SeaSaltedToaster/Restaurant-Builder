package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class WaitForCleanTable extends Action {

	private TableComponent tableComp;
	private int claimedSeat;

	public WaitForCleanTable(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		boolean isDone = !tableComp.hasFood(claimedSeat);
		return isDone;
	}

}
