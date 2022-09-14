package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WaitForTable extends Action {

	//The customer entity and their build layer
	private BuildLayer layer;
	private Entity entity;
	
	public WaitForTable(Entity entity) {
		this.entity = entity;
		
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		this.layer = id.getLayer();
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
		//List of tables and the table we may have found
		List<Entity> tables = layer.getTables();
		Entity curTable = null;
		
		//Loop through all tables
		for(Entity table : tables) {
			//Get the table component as see if it is open
			TableComponent tableComp = (TableComponent) table.getComponent("Table");
			if(tableComp.hasOpenSeat() && !tableComp.isTaken()) {
				//It is open, break the loop and set the found table
				curTable = table; 
				break;
			}
		}
		
		//If the table is found
		boolean isDone = (curTable != null);
		if(isDone) {
			//Get the table component the table to be taken
			TableComponent tableComp = (TableComponent) curTable.getComponent("Table");
			tableComp.setTaken(true);

			//Find a seat and set it to be taken
			int seat = getSeatIndex(tableComp);
			tableComp.setSeatTaken(seat, true);
			
			//Add our next action
			ActionComponent comp = (ActionComponent) entity.getComponent("Action");
			comp.getActions().add(new GoToTable(entity, curTable, seat));
		}
		
		//Return done state
		return isDone;
	}

	private int getSeatIndex(TableComponent tableComp) {
		//Loop all seats in a table
		for(int i = 0; i < tableComp.seatCount; i++) {
			//Check if the seat is open and not null
			Vector3f seat = tableComp.getSeatIfOpen(i);
			if(seat != null) {
				//Return the id of the seat we found
				return i;
			}
		}		
		//We found none
		return -1;
	}

}
