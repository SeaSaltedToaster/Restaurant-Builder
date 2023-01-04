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
		
	}

	@Override
	public boolean isDone() {
		if(partySize != -127) return true;
		
		//Main person leader
		this.partySize =  (int) Math.abs(Math.random() * 7) + 1;
		BuildingId leaderId = (BuildingId) object.getComponent("BuildingId");

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
				member.setPosition(object.getPosition().add(new Vector3f(Math.random(), 0, Math.random())));

				partyMembers.add(member);
				leaderId.getLayer().addBuilding(member, bld, -127);
				
				member.removeComponent(member.getComponent("Action"));
				BuildingId id = (BuildingId) member.getComponent("BuildingId");
				id.setPrimary(randomShirtColor());
			}
		}
		
		PartyLeader leader = new PartyLeader(object, partyMembers, partySize);
		object.addComponent(leader);
		
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
			int num = Integer.parseInt(member);
			for(BuildLayer layer : MainApp.restaurant.layers) {
				for(Entity entity : layer.getBuildings()) {
					
					BuildingId id = (BuildingId) entity.getComponent("BuildingId");
					if(id.getId() == num) {
						entity.removeComponent(entity.getComponent("Action"));
						this.partyMembers.add(entity);
						this.partySize++;
					}
					
				}
			}
		}
		
		PartyLeader leader = new PartyLeader(object, partyMembers, partySize);
		object.addComponent(leader);
		
		ActionComponent comp = (ActionComponent) object.getComponent("Action");
		comp.getActions().clear();
	}

}
