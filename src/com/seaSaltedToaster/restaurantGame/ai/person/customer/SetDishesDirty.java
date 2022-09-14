package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;

public class SetDishesDirty extends Action {

	//The table and seat that are set to be dirty
	private TableComponent tableComp;
	private int claimedSeat;
	
	public SetDishesDirty(TableComponent tableComp, int claimedSeat) {
		this.tableComp = tableComp;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		//Set the food value to a dirty dish (-1)
		tableComp.setFood(FoodRegistry.getFood(-1).getVao(), claimedSeat);
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
