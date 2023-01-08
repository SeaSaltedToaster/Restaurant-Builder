package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SitDownAction extends Action {

	private Entity table;
	
	public SitDownAction(Entity table) {
		this.table = table;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		TableComponent component = (TableComponent) table.getComponent("Table");
		for(SeatComponent seat : component.getChairs()) {
			if(!seat.isTaken()) {
				seat.setTaken(true);
				
				float height = 0.25f;
				Vector3f seatPos = new Vector3f(seat.seatPosition.x, height, seat.seatPosition.z);
				object.setPosition(seatPos);
				
				notifySeating(seat);
				return true;
			}
		}
		return false;
	}

	private void notifySeating(SeatComponent component) {
		if(object.hasComponent("PartyMember"))
		{
			PartyMember member = (PartyMember) object.getComponent("PartyMember");
			PartySeating seating = (PartySeating) member.getLeader().getComponent("PartySeating");
			seating.addSeating(object, component);
		}
		else if(object.hasComponent("PartyLeader"))
		{
			PartyLeader leader = (PartyLeader) object.getComponent("PartyLeader");
			PartySeating seating = (PartySeating) leader.getEntity().getComponent("PartySeating");
			seating.addSeating(object, component);
		}
	}

	@Override
	public String type() {
		return "SitDown";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();
		
		BuildingId tId = (BuildingId) table.getComponent("BuildingId");
		int tableId = tId.getId();

		system.saveAction(index, super.actionIndex, type(), "SIT_DOWN_DATA[" + tableId + "]");
	}

	@Override
	public void loadAction(String data) {
		String line = data.replace("[", "").replace("]", "").replace("SIT_DOWN_DATA", "").replace(";", "");
		this.table = BuildingManager.getBuildingWithID(Integer.parseInt(line));
	}

}
