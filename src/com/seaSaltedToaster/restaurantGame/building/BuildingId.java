package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildingId extends Component {

	private int id;
	private Vector3f primary, secondary;
	
	private Building type;
	private BuildLayer layer;
	
	public BuildingId(int id, Building preview, BuildLayer buildLayer) {
		this.id = id;
		this.type = preview;
		this.layer = buildLayer;
		
		//new Vector3f(Math.random(), Math.random(), Math.random());
		this.primary = preview.getDefPrimary();
		this.secondary = preview.getDefSecondary();
	}

	public Vector3f getPrimary() {
		return primary;
	}

	public void setPrimary(Vector3f primary) {
		this.primary = primary;
	}

	public Vector3f getSecondary() {
		return secondary;
	}

	public void setSecondary(Vector3f secondary) {
		this.secondary = secondary;
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
