package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.versionPre.CleanRequest;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class RequestTableClean extends Action {
	
	//The table and seat that need to be cleaned
	private TableComponent tableComp;
	private int claimedSeat;

	public RequestTableClean(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		//Send request to clean the table
		CleanRequest request = new CleanRequest(tableComp, claimedSeat);
		MainApp.restaurant.dirtyTables.add(request);
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//All is done in start(), return true
		return true;
	}

}
