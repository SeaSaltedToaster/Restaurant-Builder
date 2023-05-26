package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class FindTable extends Action {

	private Entity person, table;
	
	public FindTable() {
		//Nothing for now
	}

	@Override
	public void start() {
		this.person = super.object;
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		table = locateTable();
		if(table != null) {
			goToTable();
			return true;
		}
		
		return false;
	}

	private void goToTable() {
		//Go to the table
		GoToAction action = new GoToAction(table.getPosition(), object, false);
		
		//Sit and the table
		SitDownAction sitAction = new SitDownAction(table);
		
		//Wait and then order
		WaitAction wait1 = new WaitAction(10);
		OrderFood order = new OrderFood();
		
		ActionComponent comp = (ActionComponent) object.getComponent("Action");
		comp.getActions().add(1, action);
		comp.getActions().add(2, sitAction);
		comp.getActions().add(3, wait1);
		comp.getActions().add(4, order);

		//Move all people to the table PartyLeader
		PartyLeader leader = (PartyLeader) object.getComponent("PartyLeader");
		for(Entity member : leader.getPartyMembers()) {
			GoToAction mAction = new GoToAction(table.getPosition(), member, false);
			SitDownAction mSitAction = new SitDownAction(table);
			
			ActionComponent mComp = (ActionComponent) member.getComponent("Action");
			mComp.getActions().clear();
			mComp.getActions().add(mAction);
			mComp.getActions().add(mSitAction);
		}
		
		TableComponent component = (TableComponent) table.getComponent("Table");
		component.setTablePartyLeader(object);
		
		PartySeating seating = new PartySeating();
		this.object.addComponent(seating);
	}

	private Entity locateTable() {
		//Layer info
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		BuildLayer layer = id.getLayer();
		
		//Find a valid table
		PartyLeader leader = (PartyLeader) object.getComponent("PartyLeader");
		int partySize = leader.getPartyMembers().size() + 1;
		for(Entity obj : layer.getBuildings()) {
			if(obj == null) continue;
			if(obj.hasComponent("Table")) {
				TableComponent component = (TableComponent) obj.getComponent("Table");
				if(!component.isTaken() && component.getCapacity() >= partySize) {
					component.setTaken(true);
					return obj;
				}
			}
		}
		
		//Return
		return null;
	}

	@Override
	public String type() {
		return "FindTable";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();

		system.saveAction(index, super.actionIndex, type(), "FIND_TABLE_DATA");
	}

	@Override
	public void loadAction(String data) {
		//Nothing for now
	}

}
