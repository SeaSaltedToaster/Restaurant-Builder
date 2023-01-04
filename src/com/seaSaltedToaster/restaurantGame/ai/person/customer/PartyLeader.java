package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class PartyLeader extends Component {
	
	private List<Entity> partyMembers;
	private Entity partyLeader;
	private int partySize = -127;
	
	public PartyLeader(Entity object, List<Entity> partyMembers, int partySize) {
		this.partyLeader = object;
		this.partyMembers = partyMembers;
		this.partySize = partySize;
	}
	
	public void save() {
		CreateParty party = new CreateParty(partyMembers, partySize);
		party.object = partyLeader;
		party.actionIndex = 0;
		
		ActionComponent comp = (ActionComponent) partyLeader.getComponent("Action");
		comp.getActions().add(0, party);
	}

	public List<Entity> getPartyMembers() {
		return partyMembers;
	}

	public void setPartyMembers(List<Entity> partyMembers) {
		this.partyMembers = partyMembers;
	}

	public Entity getPartyLeader() {
		return partyLeader;
	}

	public void setPartyLeader(Entity partyLeader) {
		this.partyLeader = partyLeader;
	}

	public int getPartySize() {
		return partySize;
	}

	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "PartyLeader";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return null;
	}

}
