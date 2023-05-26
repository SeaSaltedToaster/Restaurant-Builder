package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.HashMap;
import java.util.Map;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class LoadSeating extends Action {
	
	private HashMap<Entity, SeatComponent> seating;

	public LoadSeating(HashMap<Entity, SeatComponent> seating) {
		this.seating = seating;
	}

	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public String type() {
		return "LoadSeating";
	}

	@Override
	public void saveAction(SaveSystem system) {
		//SAVE ACTION GoTo
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();
		
		String data = "SEATING_DATA[";
		for(Map.Entry<Entity, SeatComponent> entry : seating.entrySet()) {
			Entity person = entry.getKey();
			SeatComponent tableCmp = entry.getValue();
			Entity table = tableCmp.entity;
			
			BuildingId partyMember = (BuildingId) person.getComponent("BuildingId");
			BuildingId tableId = (BuildingId) table.getComponent("BuildingId");

			data += (partyMember.getId() + "&" + tableId.getId() + "=");
		}
		data += "]";
		system.saveAction(index, super.actionIndex, type(), data);
	}

	@Override
	public void loadAction(String data) {
		String line = data.replace("[", "").replace("]", "").replace("SEATING_DATA", "").replace(";", "");
		String[] lines = line.split("=");
		
		HashMap<Entity, SeatComponent> seating = new HashMap<Entity, SeatComponent>();
		PartyLeader leader = (PartyLeader) object.getComponent("PartyLeader");
		
		for(String member : lines) {
			if(lines.length <= 0) break;
			
			//Break down
			String[] parts = member.split("&");
			int person = Integer.parseInt(parts[0]);
			int seat = Integer.parseInt(parts[1]);
			
			Entity personE = null;
			Entity seatE = null;

			for(BuildLayer layer : MainApp.restaurant.layers) {
				for(Entity entity : layer.getBuildings()) {
					
					BuildingId id = (BuildingId) entity.getComponent("BuildingId");
					if(id.getId() == person) {
						personE = entity;
					}
					if(id.getId() == seat) {
						seatE = entity;
					}
				}
			}
			
			if(seatE == null || personE == null) continue;
			
			SeatComponent comp = (SeatComponent) seatE.getComponent("Chair");
			comp.setTaken(true);
			
			comp.getTable().setTaken(true);
			comp.getTable().setTablePartyLeader(leader.getEntity());
			personE.setPosition(comp.seatPosition);
			seating.put(personE, comp);
			
			ActionComponent personEC = (ActionComponent) personE.getComponent("Action");
			personEC.getActions().clear(); //TODO just dont do the tree?
			personEC.getActions().add(new IdleStay()); //TODO only have one?
		}
		
		PartySeating seatingComponent = new PartySeating();
		seatingComponent.setSeating(seating);
		leader.getEntity().addComponent(seatingComponent);
	}

}
