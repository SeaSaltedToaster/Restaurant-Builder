package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Pathfinder {
			
	public List<Node> getPath(Vector3f start, Vector3f end, BuildLayer layer) {
		Node[][] grid = getNodes(layer);
		Node startNode = getNode(start, grid);
		startNode.setWalkable(true);
		Node endNode = getNode(end, grid);
		endNode.setWalkable(true);
		
		List<Node> openSet = new ArrayList<Node>();
		HashSet<Node> closedSet = new HashSet<Node>();
		openSet.add(startNode);
		
		while(openSet.size() > 0) {
			Node node = openSet.get(0);
			for (int i = 1; i < openSet.size(); i ++) {
				if (openSet.get(i).getFCost() < node.getFCost() || openSet.get(i).getFCost() == node.getFCost()) {
					if (openSet.get(i).hCost < node.hCost)
						node = openSet.get(i);
				}
			}

			openSet.remove(node);
			closedSet.add(node);
			
			if(node.equalsNode(endNode)) {
				List<Node> path = RetracePath(startNode, endNode);
				return path;
			}

			List<Node> neighbors = getNeighbours(node, grid);
			for(Node neighbour : neighbors) {
				if(!neighbour.isWalkable || closedSet.contains(neighbour)) {
					if(neighbour != endNode)
						continue;
				}

				float newCostToNeighbour = node.gCost + GetDistance(node, neighbour);
				if(newCostToNeighbour < neighbour.gCost || !openSet.contains(neighbour)) {
					neighbour.gCost = newCostToNeighbour;
					neighbour.hCost = -GetDistance(neighbour, endNode);
					neighbour.parent = node;
					openSet.add(neighbour);
				}
			}
		}
		return null;
	}
	
	private int GetDistance(Node nodeA, Node nodeB) {
		int dstX = Math.abs(nodeA.gridX - nodeB.gridX);
		int dstY = Math.abs(nodeA.gridZ - nodeB.gridZ);

		if (dstX > dstY)
			return 14*dstY + 10* (dstX-dstY);
		return 14*dstX + 10 * (dstY-dstX);
	}
	
	private List<Node> RetracePath(Node startNode, Node endNode) {
		List<Node> path = new ArrayList<Node>();
		List<Node> reversedPath = new ArrayList<Node>();
		Node currentNode = endNode;

		while (currentNode != startNode) {
			path.add(currentNode);
			currentNode = currentNode.parent;
			reversedPath.add(currentNode);
		}
		
		for(Node node : path) {
			int index = path.size() - path.indexOf(node) - 1;
			reversedPath.set(index, node);
		}
		return reversedPath;
	}
	
	private List<Node> getNeighbours(Node node, Node[][] grid) {
		List<Node> neighbours = new ArrayList<Node>();

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
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

				int checkX = node.getGridX() + x;
				int checkY = node.getGridZ() + y;

				neighbours.add(grid[checkX][checkY]);
			}
		}

		return neighbours;
	}

	private Node[][] getNodes(BuildLayer layer) {
		int gridSize = layer.getWalkableMap().length;
		Node[][] grid = new Node[gridSize][gridSize];
		for(int x = 0; x < gridSize; x++) {
			for(int y = 0; y < gridSize; y++) {
				int walkable = (int) layer.getWalkableMap()[x][y];
				if(walkable == WalkableType.WALKABLE.ordinal()) {
					Node node = new Node(getNodePosition(x,y), true);
					node.setGridX(x);
					node.setGridZ(y);
					grid[x][y] = node;
				} else {
					Node node = new Node(getNodePosition(x,y), false);
					node.setGridX(x);
					node.setGridZ(y);
					grid[x][y] = node;
				}
			}
		}
		return grid;
	}
	
	private Node getNode(Vector3f position, Node[][] grid) {
		int worldScale = (int) (Ground.worldSize * 2);
		int rawX = (int) position.x;
		int X = rawX + (worldScale / 2);
		int rawY = (int) position.z;
		int Y = rawY + (worldScale / 2);
		return grid[X][Y];
	}

	private Vector3f getNodePosition(int x, int y) {
		int worldScale = (int) (Ground.worldSize);
		return new Vector3f(x - worldScale, 0, y - worldScale);
	}
}
