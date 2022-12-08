package com.seaSaltedToaster.restaurantGame.building.walls;

import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public abstract class WallGenerator {
		
	public abstract Entity createWall(Vector3f startPoint, Vector3f endPoint, WallComponent wallType, boolean addToBatch);
	
	public abstract String getType();

}
