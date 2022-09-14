package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SitAtTable extends Action {
	
	//Entity and the table their sat at
	private Entity entity, table;
	
	//The exact table component and seat
	private TableComponent tableComp;
	private int claimedSeat = -1;
	
	public SitAtTable(Entity entity, Entity table, int claimedSeat) {
		this.entity = entity;
		this.table = table;
		this.claimedSeat = claimedSeat;
	}

	@Override
	public void start() {
		//Get component of the table
		this.tableComp = (TableComponent) table.getComponent("Table");
		
		//Get the seat position the entity should be at
		Vector3f seat = tableComp.getSeat(claimedSeat);
		seat = MathUtils.rotatePointAtCenter(seat, table.getTransform().getRotation().y);
		
		//Set entity position to seat position
		entity.getTransform().setPosition(table.getTransform().getPosition().copy().add(seat));
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//Get the action component
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		
		//Wait before ordering, then order food
		comp.getActions().add(new WaitAction(10.0f));
		comp.getActions().add(new OrderFood(tableComp, entity, claimedSeat));
		
		//Wait for food, upon food arrival, wait the comsumption time of 25.0f seconds
		comp.getActions().add(new WaitForFood(tableComp, claimedSeat));
		comp.getActions().add(new WaitAction(25.0f));
		
		//Set the dishes as dirty and request cleaning
		comp.getActions().add(new SetDishesDirty(tableComp, claimedSeat));
		comp.getActions().add(new RequestTableClean(tableComp, claimedSeat));
		
		//Wait for table to be clean and wait to pay and leave
		comp.getActions().add(new WaitForCleanTable(tableComp, claimedSeat));
		comp.getActions().add(new WaitAction(15.0f));
		
		//Pay for the food
		comp.getActions().add(new RequestPay(entity, tableComp));
		
		//Exit the table and leave the building
		comp.getActions().add(new ExitTable(entity, tableComp, claimedSeat));
		comp.getActions().add(new DeleteCustomer(entity));
		
		//Return done true
		return true;
	}

}
