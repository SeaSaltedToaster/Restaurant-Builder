package com.seaSaltedToaster.simpleEngine.uis.layouts;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;

public abstract class UiLayout {

	public abstract void update(UiComponent component, List<UiComponent> children);
	
}