package com.seaSaltedToaster.restaurantGame.building.layers;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public interface PlacementListener {
	
	public void notifyPlacement(Entity obj, Building type);

}
