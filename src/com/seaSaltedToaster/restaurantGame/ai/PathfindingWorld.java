package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class PathfindingWorld {
	
	//Pathfinding world data
	private int worldSize;
	private final int curLayer = 0;
	
	//Walkable world lists and layers
	private boolean[][] walkableWorld;
	private List<Vector3f> walls, objects;
	
	public PathfindingWorld(int worldSize) {
		//World size and walls init
		this.worldSize = worldSize;
		this.walls = new ArrayList<Vector3f>();
		this.objects = new ArrayList<Vector3f>();
		
		//Walkable list init and set all to false
		this.walkableWorld = new boolean[worldSize][worldSize];
		setAll(walkableWorld, false);
	}
	
	public boolean wallObstruction(Vector3f start, Vector3f end) {
		//Get area of wall in between the two tiles
		Vector3f middle = new Vector3f((start.x + end.x) / 2, (start.y + end.y) / 2, (start.z + end.z) / 2);
		
		//Check if a wall is there
		for(Vector3f wall : walls) {
			if(wall.equals(middle)) {
				//Found one, return true
				return true;
			}
		}
		
		//There is none
		return false;
	}
	
	public boolean hasObstruction(Vector3f location) {
		//Check if a wall is there
		for(Vector3f object : objects) {
			if(object.equals(location)) {
				//Found one, return true
				return true;
			}
		}
		
		//There is none
		return false;
	}

	public void addBuilding(Building building, Vector3f location) {
		//Change depending on building type
		switch(building.type) {
		case Floor:
			//Simply set it to true
			set(location, walkableWorld, true);
			break;
		case Object:
			//Check if the object is obstructive
//			if(building.isObstructive()) {
				objects.add(location);
//			} else {
//				//No obstruction, no list
//			}
			break;
		case Person:
			//Nothing
			break;
		case Wall:
			//Add wall
			if(!building.isWalkThrough())
				walls.add(location);
			break;
		default:
			//Nothing
			break;
		}
	}
	
	public void removeBuilding(Entity entity, BuildLayer layer) {
		//Get building id
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		Building type = id.getType();
		BuildingType structType = type.type;

		//Get coords
		Vector3f position = entity.getPosition();
		
		//Remove from wall list if it is a wall
		if(structType == BuildingType.Wall) {
			this.walls.remove(position);
			return;
		}

		//Check if there is a floor if it is an obstructive object
		if(structType == BuildingType.Object) {
			this.objects.remove(position);
			return;
		}
		
		//Set to unwalkable if it is a floor
		if(structType == BuildingType.Floor) {
			set(position, walkableWorld, false);
			return;
		}
	}
	
	public void recalculateWorld(BuildLayer layer) {
		//TODO recalc world
	}
	
	public boolean get(Vector3f location, boolean[][] world) {
		//Convert to tile coords
		int x = getTileX(location);
		int z = getTileZ(location);
		
		//Return state at those coords
		return world[x][z];
	}
	
	public void set(Vector3f location, boolean[][] world, boolean state) {
		//Convert to tile coords
		int x = getTileX(location);
		int z = getTileZ(location);
		
		//Set state at those coords
		world[x][z] = state;
	}
	
	public int getTileX(Vector3f location) {
		//Get X position
		int pos = (int) location.x;
		
		//Convert and normalize
		int normalized = pos + (worldSize / 2);
		return normalized;
	}
	
	public int getTileZ(Vector3f location) {
		//Get Z position
		int pos = (int) location.z;
		
		//Convert and normalize
		int normalized = pos + (worldSize / 2);
		return normalized;
	}

	public void setAll(boolean[][] world, boolean state) {
		//Loop through every item in the array
		for(int x = 0; x < worldSize; x++) {
			for(int y = 0; y < worldSize; y++) {
				//Set that part to the state
				world[x][y] = state;
			}
		}
	}

	public int getWorldSize() {
		return worldSize;
	}

	public int getCurLayer() {
		return curLayer;
	}

	public boolean[][] getWalkableWorld() {
		return walkableWorld;
	}

}
