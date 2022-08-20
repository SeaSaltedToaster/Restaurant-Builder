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

	//Info
	private BuildLayer layer;
	private Entity entity;
	
	public WaitForTable(Entity entity) {
		this.entity = entity;
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		this.layer = id.getLayer();
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		List<Entity> tables = layer.getTables();
		Entity curTable = null;
		for(Entity table : tables) {
			TableComponent tableComp = (TableComponent) table.getComponent("Table");
			if(tableComp.hasOpenSeat() && !tableComp.isTaken()) {
				curTable = table; 
				break;
			}
		}
		
		boolean isDone = (curTable != null);
		if(isDone) {
			TableComponent tableComp = (TableComponent) curTable.getComponent("Table");
			int seat = getSeatIndex(tableComp);
			tableComp.setSeatTaken(seat, true);
			tableComp.setTaken(true);
			
			ActionComponent comp = (ActionComponent) entity.getComponent("Action");
			comp.getActions().add(new GoToTable(entity, curTable));
			comp.getActions().add(new SitAtTable(entity, curTable, seat));
		}
		return isDone;
	}

	private int getSeatIndex(TableComponent tableComp) {
		for(int i = 0; i < tableComp.seatCount; i++) {
			Vector3f seat = tableComp.getSeatIfOpen(i);
			if(seat != null) {
				return i;
			}
		}		
		return -1;
	}

}
