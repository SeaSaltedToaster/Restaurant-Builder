package com.seaSaltedToaster.restaurantGame.building.floors;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class FlatFloorGenerator extends FloorGenerator {

	private float height = 0.0125f, tileSize = 1.0f;
	
	@Override
	public void addTile(List<Vector3f> vertices, List<Integer> triangles, List<Vector3f> colors, float x, float z) {
		Vector3f one = new Vector3f(x, height, z);
		vertices.add(one);
		Vector3f two = new Vector3f(x + tileSize, height, z);
		vertices.add(two);
		Vector3f three = new Vector3f(x, height, z + tileSize);
		vertices.add(three);
		Vector3f four = new Vector3f(x + tileSize, height, z + tileSize);
		vertices.add(four);
		
		triangles.add(vertices.indexOf(two));
		triangles.add(vertices.indexOf(one));
		triangles.add(vertices.indexOf(three));
		
		triangles.add(vertices.indexOf(three));
		triangles.add(vertices.indexOf(four));
		triangles.add(vertices.indexOf(two));
		
		for(int i = 0; i < 4; i++) {
			colors.add(new Vector3f(2,0,0));
		}
	}

	@Override
	public void addCornerTile() {
		
	}

	@Override
	public String getType() {
		return "Flat";
	}


}
