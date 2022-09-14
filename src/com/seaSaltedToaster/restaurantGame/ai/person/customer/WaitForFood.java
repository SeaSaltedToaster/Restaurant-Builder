package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class WaitForFood extends Action {
	
	//The exact table and seat we are waiting for food at
	private TableComponent tableComp;
	private int claimedSeat;

	public WaitForFood(TableComponent tableComp, int claimedSeat) {
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
		//Check if the table seat has food, return that state
		boolean isDone = tableComp.hasFood(claimedSeat);
		return isDone;
	}

}
