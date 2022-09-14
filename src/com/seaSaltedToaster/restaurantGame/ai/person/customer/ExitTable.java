package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ExitTable extends Action {
	
	//Entity in the table seat
	private Entity entity;
	
	//Table being sat at and the exact seat
	private TableComponent tableComp;
	private int curSeat;

	public ExitTable(Entity entity, TableComponent tableComp, int curSeat) {
		this.entity = entity;
		this.tableComp = tableComp;
		this.curSeat = curSeat;
	}

	@Override
	public void start() {
		//Set the seat and table to not be taken
		tableComp.getSeatTaken()[curSeat] = false;
		tableComp.setTaken(false);
		
		//Set the entity's position to be off the seat
		Vector3f endPos = tableComp.getEntity().getTransform().getPosition();
		entity.getTransform().setPosition(endPos);
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
