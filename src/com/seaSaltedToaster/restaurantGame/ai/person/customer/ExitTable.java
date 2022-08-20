package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ExitTable extends Action {
	
	private Entity entity;
	private TableComponent tableComp;
	private int curSeat;

	public ExitTable(Entity entity, TableComponent tableComp, int curSeat) {
		this.entity = entity;
		this.tableComp = tableComp;
		this.curSeat = curSeat;
	}

	@Override
	public void start() {
		tableComp.getSeatTaken()[curSeat] = false;
		tableComp.setTaken(false);
		
		Vector3f endPos = tableComp.getEntity().getTransform().getPosition();
		entity.getTransform().setPosition(endPos);
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

}
