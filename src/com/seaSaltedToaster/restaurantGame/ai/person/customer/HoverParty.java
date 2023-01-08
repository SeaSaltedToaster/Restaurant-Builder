package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.MoveToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class HoverParty extends Action {

	private Entity followPerson;
	
	@Override
	public void start() {
		PartyMember member = (PartyMember) object.getComponent("PartyMember");
		this.followPerson = member.getLeader();
		
		ActionComponent memberAComp = (ActionComponent) object.getComponent("Action");
		
		Vector3f add = new Vector3f(Math.random(), 0, Math.random());
		Vector3f go = followPerson.getPosition().copy().add(add);
		go.y = 0;
		memberAComp.getActions().add(new MoveToAction(go, object));
		
		float waitTime = (float) Math.abs(Math.random() * 3);
		memberAComp.getActions().add(new WaitAction(waitTime));
		
		memberAComp.getActions().add(new HoverParty());
	}

	@Override
	public void update() {
		//Follow
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public String type() {
		return "HoverParty";
	}

	@Override
	public void saveAction(SaveSystem system) {
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();
		system.saveAction(index, super.actionIndex, type(), "HOVER_PARTY[" + "]");
	}

	@Override
	public void loadAction(String data) {
		//Nothing
	}

}
