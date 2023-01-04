package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.objects.FloorComponent;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class PathfindingWorld {
	
	//Pathfinding world data
	private int worldSize, gridSize;
	private final int curLayer = 0;
	
	//Walkable world lists and layers
	private boolean[][] walkableWorld;
	private List<Vector3f> objects;
	private List<WallComponent> walls;
	
	public PathfindingWorld(int worldSize) {
		//World size and walls init
		this.walls = new ArrayList<WallComponent>();
		this.objects = new ArrayList<Vector3f>();
		
		this.worldSize = worldSize;
		float tileSpace = worldSize * (1.0f / Ground.tileSize);
		this.gridSize = (int) (tileSpace + 1.0f);
		
		//Walkable list init and set all to false
		this.walkableWorld = new boolean[gridSize][gridSize];
		setAll(walkableWorld, false);
	}
	
	public boolean wallObstruction(Vector3f start, Vector3f end) {
		//Check if a wall is there
		for(WallComponent wall : walls) {
			Vector2f p1 = new Vector2f(wall.getStart().x, wall.getStart().z);
			Vector2f p2 = new Vector2f(wall.getEnd().x, wall.getEnd().z);
			
			Vector2f q1 = new Vector2f(start.x, start.z);
			Vector2f q2 = new Vector2f(end.x, end.z);
			
			if(MathUtils.doIntersect(p1, p2, q1, q2)) {
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

	public void addBuilding(Building building, Entity preview) {
		Vector3f objectPos = preview.getPosition();
		
		//Change depending on building type
		switch(building.type) {
		case Floor :
			FloorComponent comp = (FloorComponent) preview.getComponent("Floor");
			if(comp.getPoints().size() == 2) {
				fillArea(comp.getPoints().get(0), comp.getPoints().get(1));
			}
			//Simply set it to true
			break;
		case Object:
			objects.add(objectPos);
			this.set(objectPos, walkableWorld, false);
			break;
		case Person:
			//Nothing
			break;
		case Wall:
			//Add wall
			WallComponent wall = (WallComponent) preview.getComponent("Wall");
			walls.add(wall);
			break;
		default:
			//Nothing
			break;
		}
	}
	
	private void fillArea(Vector3f p1, Vector3f p2) {
		//Space
		float xDif = Math.abs(p1.x - p2.x);
		float zDif = Math.abs(p1.z - p2.z);
		float tileSpace = (Ground.tileSize);
		
		//Get start of row fill
		float rowStart = 0;
		if(p1.x > p2.x)
			rowStart = p2.x;
		else
			rowStart = p1.x;
		
		//p2 to p1 Z
		if(p1.z > p2.z) 
		{
			for(float z = p2.z; z <= p2.z + zDif; z += tileSpace) {
				int index = getIndex(z);
				boolean[] row = walkableWorld[index];
				
				for(float i = rowStart; i <= rowStart + xDif; i += tileSpace) 
				{
					if(noObj(z, i))
						row[getIndex(i)] = true;
				}
				walkableWorld[index] = row;
			}
		}
		//p2 to p1 Z
		else 
		{
			for(float z = p1.z; z <= p1.z + zDif; z += tileSpace) {
				int index = getIndex(z);
				boolean[] row = walkableWorld[index];
				
				for(float i = rowStart; i <= rowStart + xDif; i += tileSpace) 
				{
					if(noObj(z, i))
						row[getIndex(i)] = true;
				}
				walkableWorld[index] = row;
			}
		}
	}

	private boolean noObj(float z, float i) {
		Vector2f base = new Vector2f(i, z);
		for(Vector3f obj : objects)
		{
			if(base.x == obj.x && base.y == obj.z)
				return false;
		}
		return true;
	}

	public int getIndex(float x) {
		return (int) (x * (1.0f / Ground.tileSize)) + (worldSize*2 + 2);
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
			WallComponent wall = (WallComponent) entity.getComponent("Wall");
			this.walls.remove(wall);
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
		return world[z][x];
	}
	
	public void set(Vector3f location, boolean[][] world, boolean state) {
		//Convert to tile coords
		int x = getIndex(location.x);
		int z = getIndex(location.z);
		
		//Set state at those coords
		world[z][x] = state;
	}
	
	public int getTileX(Vector3f location) {
		//Get X position
		int pos = (int) location.x;
		
		//Convert and normalize
		int normalized = pos + (gridSize / 2);
		return normalized;
	}
	
	public int getTileZ(Vector3f location) {
		//Get Z position
		int pos = (int) location.z;
		
		//Convert and normalize
		int normalized = pos + (gridSize / 2);
		return normalized;
	}
	
	public float posOf(int x) {
		float unnormal = x - (gridSize / 2);
		unnormal /= (1.0f / Ground.tileSize);
		unnormal -= (2.0f * Ground.tileSize);
		return unnormal;
	}


	public void setAll(boolean[][] world, boolean state) {
		//Loop through every item in the array
		for(int x = 0; x < gridSize; x++) {
			for(int y = 0; y < gridSize; y++) {
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
