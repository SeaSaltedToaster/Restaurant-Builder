package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class PathfinderComponent extends Component {

	//Our path and current node
	private List<Node> curPath;
	private Node curNode;
	
	//Calculating and moving on the path
	private Pathfinder pathfinder;
	private Mover mover;
	
	//Layer the path is on
	private BuildLayer layer;
	
	public PathfinderComponent() {
		//Create path node list and pathfinder
		this.curPath = new ArrayList<Node>();
		this.pathfinder = new Pathfinder();
	}
	
	@Override
	public void update() {
		//If we arent moving, this code is obsolete
		if(!mover.isMoving) return;
		
		//Set position the entity should be at
		Vector3f newPos = mover.update();
		if(newPos != null)
			entity.getTransform().setPosition(newPos);
		
		//If the entity reached the node, move to the next
		if(mover.reachedTarget()) {
			curPath.remove(curNode);
			getNextNode();
		}
	}
	
	public void goTo(Vector3f target) {	
		//Set the layer we are moving on
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		this.layer = id.getLayer();
		
		//
		Vector3f end = target;
		this.curPath = pathfinder.getPath(entity.getTransform().getPosition().copy(), end, layer);
		
		//If the path doesnt exist, set an empty path and return an error
		if(curPath == null) {
			System.err.println("Coundn't calculate path");
			this.curPath = new ArrayList<Node>();
		}
		
		//Move to the first node in the path
		getNextNode();
	}
	
	private void getNextNode() {
		//If there are more nodes
		if(curPath.size() > 0) {
			//Get the next available node
			curNode = curPath.get(0);
			mover.setTarget(curNode.getNodePoint());
		} 
		//End of the path, stop entity
		else {
			mover.stop();
		}
	}
	
	public boolean reachedEnd() {
		//Check if the path is over or is non-existant
		return (curPath.size() <= 0 && curPath != null);
	}
	
	public Node getCurNode() {
		return curNode;
	}

	public void setCurNode(Node curNode) {
		this.curNode = curNode;
	}

	public List<Node> getCurPath() {
		return curPath;
	}

	public Pathfinder getPathfinder() {
		return pathfinder;
	}

	public Mover getMover() {
		return mover;
	}

	public BuildLayer getLayer() {
		return layer;
	}
	
	/*
	 * Standard component methods (mainly unused)
	 */

	@Override
	public void init() {
		this.mover = new Mover(entity);
	}


	@Override
	public void reset() {
		//Nothing
	}

	@Override
	public String getComponentType() {
		return "Pathfinder";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new PathfinderComponent();
	}

}
