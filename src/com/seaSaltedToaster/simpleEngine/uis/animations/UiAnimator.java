package com.seaSaltedToaster.simpleEngine.uis.animations;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;

public class UiAnimator {
	
	private List<UiAnimation> activeTransitions = new ArrayList<UiAnimation>();
	
	private UiComponent component;
	
	public UiAnimator(UiComponent component) {
		this.component = component;
	}
	
	public void update(UiComponent component) {
		for(UiAnimation transition : activeTransitions) {
			transition.update(component);
		}
	}
	
	public void doAnimation(UiAnimation transition) {
		activeTransitions.add(transition);
		for(UiComponent component : component.getChildren()) {
			component.getAnimator().doAnimation(transition);
		}
	}
	
	public void doAnimation(UiAnimation transition, float delay) {
		activeTransitions.add(transition);
		for(UiComponent component : component.getChildren()) {
			transition.setDelay(delay);
			component.getAnimator().doAnimation(transition);
		}
	}

}
