package com.seaSaltedToaster.restaurantGame.building;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class PlaceAnimation extends Component {

	//Animation
	private SmoothFloat popup;
	private float HEIGHT = 1.5f;
	private boolean isDone = false;
	
	@Override
	public void init() {
		BuildingId id = (BuildingId) this.entity.getComponent("BuildingId");
		
		float curY = entity.getPosition().y + (id.getLayer().getLayerId() * BuildLayer.HEIGHT_OFFSET);
		this.popup = new SmoothFloat(0);
		this.popup.setValue(HEIGHT + curY);
		this.popup.setTarget(curY);
		this.popup.setAmountPer(0.25f);
	}

	@Override
	public void update() {
		if(popup != null && !isDone) {
			popup.update(Window.DeltaTime);
			float newY = popup.getValue();
			this.getEntity().getTransform().getPosition().setY(newY);
			if(newY <= 0.025f) {
				isDone = true;
				this.getEntity().getTransform().getPosition().setY(0);
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
