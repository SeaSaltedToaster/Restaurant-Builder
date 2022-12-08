package com.seaSaltedToaster.restaurantGame.building.floors;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public abstract class FloorGenerator {
	
	public abstract void addTile(List<Vector3f> vertices, List<Integer> triangles, List<Vector3f> colors, float x, float z);
	
	public abstract void addCornerTile();

	public abstract String getType();

}
