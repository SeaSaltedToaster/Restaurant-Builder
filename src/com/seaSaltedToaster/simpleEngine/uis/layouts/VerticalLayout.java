package com.seaSaltedToaster.simpleEngine.uis.layouts;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;

public class VerticalLayout extends UiLayout {

	private float edgeSpace, componentSpace;
	
	public VerticalLayout(float edgeSpace, float componentSpace) {
		this.edgeSpace = edgeSpace;
		this.componentSpace = componentSpace;
	}

	@Override
	public void update(UiComponent component, List<UiComponent> children) {
		float spacing = (component.getPosition().y + component.getScale().y) - edgeSpace;
		spacing -= children.get(0).getScale().y; // + (component.getScale().y * componentSpace);
		
		for(UiComponent child : children) {
			if(hasExistingConstraints(child)) continue;
			
			spacing -= child.getScale().y * 2;
			child.getPosition().setY(spacing);
			spacing -= componentSpace;
		}
	}

	private boolean hasExistingConstraints(UiComponent child) {
		return (child.getConstraints().getYConstraint() != null); 
	}

	public float getEdgeSpace() {
		return edgeSpace;
	}

	public void setEdgeSpace(float edgeSpace) {
		this.edgeSpace = edgeSpace;
	}

	public float getComponentSpace() {
		return componentSpace;
	}

	public void setComponentSpace(float componentSpace) {
		this.componentSpace = componentSpace;
	}

}
