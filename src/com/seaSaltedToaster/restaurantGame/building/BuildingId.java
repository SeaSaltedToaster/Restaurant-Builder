package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class BuildingId extends Component {

	private int id;
	
	public BuildingId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getComponentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changesRenderer() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Component copyInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
