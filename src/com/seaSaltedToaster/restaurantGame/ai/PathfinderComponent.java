package com.seaSaltedToaster.restaurantGame.ai;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class PathfinderComponent extends Component {

	//Target
	private List<Node> curPath;
	private Node curNode;
	
	//Path
	private Pathfinder pathfinder;
	private Mover mover;
	
	//Other
	private BuildLayer layer;
	
	public PathfinderComponent() {
		this.curPath = new ArrayList<Node>();
		this.pathfinder = new Pathfinder();
	}
	
	@Override
	public void update() {
		if(!mover.isMoving) return;
		Vector3f newPos = mover.update();
		if(newPos != null)
			entity.getTransform().setPosition(newPos.copy());

		if(mover.reachedTarget()) {
			curPath.remove(curNode);
			getNextNode();
		}
	}
	
	public void goTo(Vector3f target) {		
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		this.layer = id.getLayer();
		
		Vector3f end = target;
		this.curPath = pathfinder.getPath(entity.getTransform().getPosition().copy(), end, layer);
		if(curPath == null) {
			System.out.println("Coundn't calculate path");
			this.curPath = new ArrayList<Node>();
			return;
		}
		getNextNode();
	}
	
	public void getNextNode() {
		if(curPath.size() > 0) {
			curNode = curPath.get(0);
			mover.setTarget(curNode.getNodePoint());
		} else {
			mover.stop();
		}
	}
		
	public boolean reachedEnd() {
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

	@Override
	public void init() {
		this.mover = new Mover(entity);
	}


	@Override
	public void reset() {
		
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
