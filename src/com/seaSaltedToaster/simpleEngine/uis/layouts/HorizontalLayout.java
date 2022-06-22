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
		float spacing = (component.getPosition().x - (component.getScale().x)) + (edgeSpace * component.getScale().x);
		spacing -= children.get(0).getScale().x;
		
		for(UiComponent child : children) {
			spacing += child.getScale().x * 2;
			child.getPosition().setX(spacing);
			spacing += component.getScale().x * componentSpace;
		}
	}

}
