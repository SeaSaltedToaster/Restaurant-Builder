package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class LeaveRestaurant extends Action {
	
	private PartyLeader leader;

	public LeaveRestaurant(PartyLeader member) {
		this.leader = member;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {

	}

	@Override
	public boolean isDone() {
		TableComponent table = ((PartySeating) leader.getEntity().getComponent("PartySeating")).getSeating().get(object).getTable();
		table.setTaken(false);
		
		for(SeatComponent seat : table.getChairs()) {
			seat.setTaken(false);
		}
		
		MainApp.restaurant.money += 100;
		
		for(Entity entity : leader.getPartyMembers()) {
			BuildingId id = (BuildingId) entity.getComponent("BuildingId");
			int index = id.getLayer().getBuildings().indexOf(entity);
			id.getLayer().getBuildings().set(index, null);
		}
		
		BuildingId id = (BuildingId) object.getComponent("BuildingId");
		int index = id.getLayer().getBuildings().indexOf(object);
		id.getLayer().getBuildings().set(index, null);
		
		return true;
	}

	@Override
	public String type() {
		return "Leave";
	}

	@Override
	public void saveAction(SaveSystem system) {
		
	}

	@Override
	public void loadAction(String data) {
		
	}

}
