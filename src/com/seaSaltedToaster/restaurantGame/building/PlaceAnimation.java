package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class PlaceAnimation extends Component {

	//Animation
	private SmoothFloat popup;
	
	@Override
	public void init() {
		this.popup = new SmoothFloat(0.0f);
		this.popup.setTarget(1.0f);
		this.popup.setAmountPer(0.05f);
	}

	@Override
	public void update() {
		if(popup != null) {
			float newScale = popup.getValue();
			this.getEntity().getTransform().setScale(newScale);
			if(newScale == 1.0f) {
				popup = null;
			}
		}
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "BuildPlace";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new PlaceAnimation();
	}

}
