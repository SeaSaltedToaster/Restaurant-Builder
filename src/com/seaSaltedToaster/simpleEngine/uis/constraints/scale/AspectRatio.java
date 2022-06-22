package com.seaSaltedToaster.simpleEngine.uis.constraints.scale;

import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.ConstraintType;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraint;

public class AspectRatio extends UiConstraint {
	
	private float aspect;
	
	public AspectRatio(float aspect) {
		this.aspect = aspect;
	}
	
	@Override
	protected void update(UiComponent component, ConstraintType type) {
		float displayRatio = (float) Window.getAspectRatio();
		
		if(type == ConstraintType.HEIGHT) {
			component.getScale().setY(component.getScale().x * aspect * displayRatio);
		} else if(type == ConstraintType.WIDTH) {
			component.getScale().setX(component.getScale().y * aspect * displayRatio);
		}
	}

}
