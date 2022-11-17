package com.seaSaltedToaster.restaurantGame.ai.person.versionPre;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;

public class CleanTable extends Action {

	//The request given by the customer
	private CleanRequest cleanRequest;
	
	public CleanTable(CleanRequest cleanRequest) {
		this.cleanRequest = cleanRequest;
	}

	@Override
	public void start() {
		//Set the table to be clean (null food)
		TableComponent table = cleanRequest.getTableComp();
		table.setFood(null, cleanRequest.getClaimedSeat());
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//Everything is done in start(), return true
		return true;
	}

}
