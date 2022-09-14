package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Pathfinder {
	
	//Pathfinding world settings
	private int worldSize = (int) (Ground.worldSize * 2);
			
	public List<Node> getPath(Vector3f start, Vector3f end, BuildLayer layer) {
		//Convert list of booleans to a node grid
		Node[][] grid = getNodes(layer);
		
		//Get the target and start nodes, make sure they can be walked on
		Node startNode = getNode(start, grid);
		startNode.setWalkable(true);
		Node endNode = getNode(end, grid);
		endNode.setWalkable(true);
		
		//Lists of nodes that are searched and ones that are not
		List<Node> openSet = new ArrayList<Node>();
		HashSet<Node> closedSet = new HashSet<Node>();
		openSet.add(startNode);
		
		//Have the last used node saved for later use
		Node lastNode = null;
		
		//While there are unsearched nodes, continue looping
		while(openSet.size() > 0) {
			//Get first available node
			Node node = openSet.get(0);
			lastNode = node;
			
			//Go through the list of other unsearched nodes and check if it is better to move there
			for (int i = 1; i < openSet.size(); i ++) {
				if (openSet.get(i).getFCost() < node.getFCost() || openSet.get(i).getFCost() == node.getFCost()) {
					if (openSet.get(i).hCost < node.hCost)
						node = openSet.get(i);
				}
			}

			//This node has been searched
			openSet.remove(node);
			closedSet.add(node);
			
			//If we have reached the end, retrace our path and return
			if(node.equalsNode(endNode)) {
				List<Node> path = RetracePath(startNode, endNode);
				return path;
			}

			//Get the neighbors of this node
			List<Node> neighbors = getNeighbours(node, grid);
			for(Node neighbour : neighbors) {
				//If the neighbor is unusable, return to the next one
				if(!neighbour.isWalkable || closedSet.contains(neighbour) || wallObstruction(layer, node.getNodePoint(), neighbour.getNodePoint())) {
					if(neighbour != endNode)
						continue;
				}

				//Calculate the cost of moving to the neighbor, and add it to the unsearched list
				float newCostToNeighbour = node.gCost + GetDistance(node, neighbour);
				if(newCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)) {
					//Add new node
					neighbour.gCost = newCostToNeighbour;
					neighbour.hCost = -GetDistance(neighbour, endNode);
					neighbour.parent = node;
					openSet.add(neighbour);
				}
			}
		}
		return RetracePath(startNode, lastNode);
	}
	
	private boolean wallObstruction(BuildLayer layer, Vector3f start, Vector3f end) {
		//Get the pathfinding world list
		PathfindingWorld world = layer.getManager().getPathWorld();
		
		//Return state of wall obstruction
		boolean obs = world.wallObstruction(start, end);
		return obs;
	}

	private int GetDistance(Node nodeA, Node nodeB) {
		//Get distance in absolute
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridZ - nodeB.gridZ);
		
		//Calculte and return the dist between the nodes
		if (dstX > dstY)
			return 14*dstY + 10* (dstX-dstY);
		return 14*dstX + 10 * (dstY-dstX);
	}
	
	private List<Node> RetracePath(Node startNode, Node endNode) {
		//Normal path and reversed path list
		List<Node> path = new ArrayList<Node>();
		List<Node> reversedPath = new ArrayList<Node>();
		
		//Current starting node (end in reverse)
		Node currentNode = endNode;
		
		//Loop until we reach the starting node
		while (currentNode != startNode) {
			//Add the parent node and continue
			path.add(currentNode);
			currentNode = currentNode.parent;
			reversedPath.add(currentNode);
		}
		
		//Reverse the path
		for(Node node : path) {
			int index = path.size() - path.indexOf(node) - 1;
			reversedPath.set(index, node);
		}
		
		//Return the reversed list
		return reversedPath;
	}
	
	private List<Node> getNeighbours(Node node, Node[][] grid) {
		//List of neighbors to return
		List<Node> neighbors = new ArrayList<Node>();
		
		//Loop through all possible neighbors
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				//Invalid neighbors (center and diagonal)
				if (x == 0 && y == 0)
					continue;
				if(x == 1 && y == 1)
					continue;
				if(x == 1 && y == -1)
					continue;
				if(x == -1 && y == 1)
					continue;
				if(x == -1 && y == -1)
					continue;
				
				//Add grid node to neighbor list
				int checkX = node.getGridX() + x;
				int checkY = node.getGridZ() + y;
				neighbors.add(grid[checkX][checkY]);
			}
		}

		return neighbors;
	}

	private Node[][] getNodes(BuildLayer layer) {
		//Get the list of booleans of walkability in this layer
		int gridSize = worldSize;
		boolean[][] world = layer.getManager().getPathWorld().getWalkableWorld();
				
		//Create the array and loop through all indexes
		Node[][] grid = new Node[gridSize][gridSize];
		for(int x = 0; x < gridSize; x++) {
			for(int y = 0; y < gridSize; y++) {
				//Check if it is walable
				boolean walkable = world[x][y];
				
				//Create new node
				Node node = new Node(getNodePosition(x,y), walkable);
				node.setGridX(x);
				node.setGridZ(y);
				
				//Set that node at the index coords
				grid[x][y] = node;
			}
		}
		return grid;
	}
	
	private Node getNode(Vector3f position, Node[][] grid) {
		//Get the node in the grid at this position
		int halfScale = worldSize / 2;
		
		//Get normalized coords
		int rawX = (int) position.x;
		int X = rawX + halfScale;
		int rawY = (int) position.z;
		int Y = rawY + halfScale;
		
		//Return node at the new coords
		return grid[X][Y];
	}

	private Vector3f getNodePosition(int x, int y) {
		//Normalize the position to node space
		int halfScale = worldSize / 2;
		return new Vector3f(x - halfScale, 0, y - halfScale);
	}
}
