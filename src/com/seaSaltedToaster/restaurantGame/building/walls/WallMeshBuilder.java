package com.seaSaltedToaster.restaurantGame.building.walls;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WallMeshBuilder {
	
	public WallGenerator generator;
	
	public List<Entity> createMesh(Vector3f startPoint, Vector3f endPoint, WallComponent wallType) {
		this.setGenerator(wallType.getGenerator());
		
		List<Entity> walls = new ArrayList<Entity>();
		Vector3f curPoint = startPoint;
		
		Vector3f direction = startPoint.copy().subtract(endPoint.copy());
		if(direction.length() == (int) direction.length()) {
			int amount = (int) direction.length();
			float scale = -1.0f / (float) amount;
			Vector3f part = direction.copy().scale(scale);
			for(int i = 0; i < amount; i++) {
				Vector3f newStart = part.copy().scale(i);
				Vector3f newEnd = part.copy().scale(i+1);
				
				Entity wall = generator.createWall(newStart.add(startPoint), newEnd.add(startPoint), wallType, true);
				walls.add(wall);
			}
		}
		else {
			Entity wall = generator.createWall(curPoint, endPoint, wallType, true);
			walls.add(wall);
		}
		
		return walls;
	}
	
	public void setGenerator(String generatorType) {
		switch(generatorType) {
		case "Standard" :
			this.generator = new StandardWallGenerator();
			break;
		case "Trimmed" :
			this.generator = new TrimmedWallGenerator();
			break;
		default :
			this.generator = new StandardWallGenerator();
			break;
		}
	}
	
}
