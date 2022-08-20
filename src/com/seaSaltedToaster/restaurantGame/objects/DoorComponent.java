package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class DoorComponent extends Component {

	@Override
	public void init() {
		// TODO Auto-generated method stub

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
		return "Door";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new DoorComponent();
	}

}
