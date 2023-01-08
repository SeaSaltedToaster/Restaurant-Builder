package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.HashMap;

import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class PartySeating extends Component {

	private HashMap<Entity, SeatComponent> seating; 
	
	public PartySeating() {
		this.seating = new HashMap<Entity, SeatComponent>();
	}
	
	public void saveSeating() {
		LoadSeating load = new LoadSeating(seating);
		load.object = entity;
		load.actionIndex = 0;
		
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		comp.getActions().add(1, load);
		System.out.println("G " + seating.size());
	}
	
	public void addSeating(Entity person, SeatComponent component) {
		this.seating.put(person, component);
	}
	
	public HashMap<Entity, SeatComponent> getSeating() {
		return seating;
	}

	public void setSeating(HashMap<Entity, SeatComponent> seating) {
		this.seating = seating;
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
		return "PartySeating";
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
