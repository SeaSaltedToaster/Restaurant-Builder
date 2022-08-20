package com.seaSaltedToaster.restaurantGame.ai;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Node {
	
	//Pos
	private Vector3f nodePoint;
	public boolean isWalkable;
	public float gCost, hCost;
	public int gridX, gridZ;
	public Node parent;
	
	public Node(Vector3f nodePoint, boolean isWalkable) {
		this.nodePoint = nodePoint;
		this.isWalkable = isWalkable;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridZ() {
		return gridZ;
	}

	public void setGridZ(int gridZ) {
		this.gridZ = gridZ;
	}

	public float getFCost() {
		return hCost + gCost;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public void setWalkable(boolean isWalkable) {
		this.isWalkable = isWalkable;
	}

	public Vector3f getNodePoint() {
		return nodePoint;
	}

	public void setNodePoint(Vector3f nodePoint) {
		this.nodePoint = nodePoint;
	}

	public boolean equalsNode(Node node) {
		return(node.getGridX() == gridX && node.getGridZ() == gridZ);
	}

}
