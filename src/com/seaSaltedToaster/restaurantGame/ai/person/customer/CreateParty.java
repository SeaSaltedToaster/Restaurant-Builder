package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class CreateParty extends Action {
	
	private List<Entity> partyMembers;
	private int partySize = -127;
	
	public CreateParty() {
		this.partyMembers = new ArrayList<Entity>();
	}
	
	public CreateParty(List<Entity> partyMembers, int partySize) {
		this.partyMembers = partyMembers;
		this.partySize = partySize;
	}


	@Override
	public void start() {
		//None
	}

	private Vector3f randomShirtColor() {
		return new Vector3f(Math.random(), Math.random(), Math.random());
	}

	@Override
	public void update() {
		//None
	}

	@Override
	public boolean isDone() {
		if(partySize != -127) return true;
		
		//Main person leader
		this.partySize =  MainApp.restaurant.capacity();
		BuildingId leaderId = (BuildingId) object.getComponent("BuildingId");
		
		if(this.partySize < 1 || MainApp.restaurant.capacity() < 1)
			return false;

		//Create our party
		for(int i = 0; i < partySize; i++) 
		{
			if(i == 0)
			{
				leaderId.setPrimary(randomShirtColor());
			}
			else
			{
				Building bld = BuildingList.getBuilding("Customer_TEST");
				Entity member = bld.getEntity().copyEntity();
				member.getPosition().setX(object.getPosition().x);
				member.getPosition().setZ(object.getPosition().z);

				partyMembers.add(member);
				leaderId.getLayer().addBuilding(member, bld, -127);
				
				ActionComponent memberAComp = (ActionComponent) member.getComponent("Action");
				memberAComp.setTree("Guest");
				memberAComp.getActions().clear();
				
				PartyMember memberComp = new PartyMember(object);
				member.addComponent(memberComp);
				
				BuildingId id = (BuildingId) member.getComponent("BuildingId");
				id.setPrimary(randomShirtColor());
			}
		}
		
		PartyLeader leader = new PartyLeader(object, partyMembers, partySize);
		object.addComponent(leader);
		
		for(Entity member : leader.getPartyMembers()) {
			ActionComponent memberAComp = (ActionComponent) member.getComponent("Action");
			HoverParty party = new HoverParty();
			party.object = member;
			memberAComp.getActions().add(party);
		}
		
		return partySize != -127;
	}

	@Override
	public String type() {
		return "CreateParty";
	}

	@Override
	public void saveAction(SaveSystem system) {
		//SAVE ACTION GoTo
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();
		PartyLeader leader = (PartyLeader) super.object.getComponent("PartyLeader");
		
		String data = "PARTY_DATA[";
		for(Entity entity : leader.getPartyMembers()) {
			BuildingId partyMember = (BuildingId) entity.getComponent("BuildingId");
			data += (partyMember.getId() + "=");
		}
		data += "]";
		system.saveAction(index, super.actionIndex, type(), data);
	}

	@Override
	public void loadAction(String data) {		
		String line = data.replace("[", "").replace("]", "").replace("PARTY_DATA", "").replace(";", "");
		String[] lines = line.split("=");
		
		this.partySize = 0;
		for(String member : lines) {
			if(lines.length <= 1) break;
			int num = Integer.parseInt(member);
			for(BuildLayer layer : MainApp.restaurant.layers) {
				for(Entity entity : layer.getBuildings()) {
					
					BuildingId id = (BuildingId) entity.getComponent("BuildingId");
					if(id.getId() == num) {
						
						ActionComponent memberAComp = (ActionComponent) entity.getComponent("Action");
						memberAComp.setTree("Guest");
						memberAComp.doTree = false;
						memberAComp.getActions().clear();
						
						PartyMember memberComp = new PartyMember(object);
						entity.addComponent(memberComp);
						
						this.partyMembers.add(entity);
						this.partySize++;
					}
					
				}
			}
		}
		
		PartyLeader leader = new PartyLeader(object, partyMembers, partySize);
		object.addComponent(leader);
		
		ActionComponent comp = (ActionComponent) object.getComponent("Action");
		comp.doTree = false;
		comp.getActions().clear();
	}

}
