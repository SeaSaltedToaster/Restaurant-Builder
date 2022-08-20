package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SitAtTable extends Action {
	
	//Object
	private Entity entity, table;
	private TableComponent tableComp;
	private int claimedSeat = -1;
	
	public SitAtTable(Entity entity, Entity table, int claimedSeat) {
		this.entity = entity;
		this.table = table;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		this.tableComp = (TableComponent) table.getComponent("Table");
		Vector3f seat = tableComp.getSeat(claimedSeat);
		seat = MathUtils.rotatePointAtCenter(seat, table.getTransform().getRotation().y);
		entity.getTransform().setPosition(table.getTransform().getPosition().copy().add(seat));
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		comp.getActions().add(new WaitAction(10.0f));
		comp.getActions().add(new OrderFood(tableComp, entity, claimedSeat));
		comp.getActions().add(new WaitForFood(tableComp, claimedSeat));
		comp.getActions().add(new WaitAction(25.0f));
		comp.getActions().add(new SetDishesDirty(tableComp, claimedSeat));
		comp.getActions().add(new RequestTableClean(tableComp, claimedSeat));
		comp.getActions().add(new WaitForCleanTable(tableComp, claimedSeat));
		comp.getActions().add(new WaitAction(15.0f));
		comp.getActions().add(new RequestPay(entity, tableComp));
		comp.getActions().add(new ExitTable(entity, tableComp, claimedSeat));
		comp.getActions().add(new LeaveBuilding(entity));
		return true;
	}

}
