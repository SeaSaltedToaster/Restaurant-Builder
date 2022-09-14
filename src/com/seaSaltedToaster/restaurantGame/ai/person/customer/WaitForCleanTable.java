package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class WaitForCleanTable extends Action {

	//The table and seat we are waiting to be clean
	private TableComponent tableComp;
	private int claimedSeat;

	public WaitForCleanTable(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		//Nothing
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//Check if the table is clean and return it
		boolean isDone = !tableComp.hasFood(claimedSeat);
		return isDone;
	}

}
