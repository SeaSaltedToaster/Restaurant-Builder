package com.seaSaltedToaster.simpleEngine.uis.layouts;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;

public class HorizontalLayout extends UiLayout {
	
	private float edgeSpace, componentSpace;
	
	public HorizontalLayout(float edgeSpace, float componentSpace) {
		this.edgeSpace = edgeSpace;
		this.componentSpace = componentSpace;
	}

	@Override
	public void update(UiComponent component, List<UiComponent> children) {
		float spacing = (component.getPosition().x - component.getScale().x) + (edgeSpace * component.getScale().x);
		
		for(UiComponent child : children) {
			if(hasExistingConstraints(child)) continue;
			
			spacing += child.getScale().x * 2;
			child.getPosition().setX(spacing);
			spacing += (componentSpace * component.getScale().x);
		}
	}

	private boolean hasExistingConstraints(UiComponent child) {
		return (child.getConstraints().getXConstraint() != null); 
	}
	
}
