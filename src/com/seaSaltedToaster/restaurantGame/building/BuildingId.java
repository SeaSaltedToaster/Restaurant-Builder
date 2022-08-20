package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class BuildingId extends Component {

	private int id;
	private Building type;
	private BuildLayer layer;
	
	public BuildingId(int id, Building preview, BuildLayer buildLayer) {
		this.id = id;
		this.type = preview;
		this.layer = buildLayer;
	}
	
	public Building getType() {
		return type;
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
		return "BuildingId";
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

	public BuildLayer getLayer() {
		return layer;
	}

	public void setLayer(BuildLayer layer) {
		this.layer = layer;
	}

}
