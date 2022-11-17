package com.seaSaltedToaster.simpleEngine.uis.constraints.scale;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.ConstraintType;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraint;

public class RelativeScale extends UiConstraint {

	private float relativeScale;
	
	public RelativeScale(float relativeScale) {
		this.relativeScale = relativeScale;
	}

	@Override
	protected void update(UiComponent component, ConstraintType type) {
		if(type == ConstraintType.WIDTH) {
			//Get Percentage of Width
			UiComponent componentParent = component.getParentComponent();
			float currentScaleX = componentParent.getScale().x;
			float newScale = currentScaleX*(relativeScale/1.0f);
			component.getScale().setX(newScale);
		} else if(type == ConstraintType.HEIGHT) {
			//Same thing but on the Y axis
			UiComponent componentParent = component.getParentComponent();
			float currentScaleY = componentParent.getScale().y;
			float newScale = currentScaleY*(relativeScale/1.0f);
			component.getScale().setY(newScale);
		}
	}

	public float getRelativeScale() {
		return relativeScale;
	}

	public void setRelativeScale(float relativeScale) {
		this.relativeScale = relativeScale;
	}

}
