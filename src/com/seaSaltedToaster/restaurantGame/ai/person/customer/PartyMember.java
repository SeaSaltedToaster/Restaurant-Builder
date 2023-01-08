package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class PartyMember extends Component {
	
	private Entity leader;
	
	public PartyMember(Entity leader) {
		this.leader = leader;
	}
	

	public Entity getLeader() {
		return leader;
	}


	public void setLeader(Entity leader) {
		this.leader = leader;
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
		return "PartyMember";
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
