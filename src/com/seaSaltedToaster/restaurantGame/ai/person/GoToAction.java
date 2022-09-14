package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.Node;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class GoToAction extends Action {
	
	//Pathfinding object and target
	private PathfinderComponent pathfinder;
	private Vector3f target;

	//Entity we are moving and settings on how we are moving
	private Entity person;
	private boolean removeFinalNode; 
	
	public GoToAction(Vector3f target, Entity person, boolean removeFinalNode) {
		this.person = person;
		this.target = target;
		this.removeFinalNode = removeFinalNode;
	}
	
	@Override
	public void start() {
		//Get the entity's pathfinder and call the goTo method
		this.pathfinder = (PathfinderComponent) person.getComponent("Pathfinder");
		pathfinder.goTo(target.copy());
		
		//If our path is null
		if(pathfinder.getCurPath() == null) {
			//Nothing
		}
		
		//Get path node list
		List<Node> path = pathfinder.getCurPath();
		
		//Remove final node is setting is set and the path has more than one node
		if(path.size() > 1 && removeFinalNode) {
			Node tableNode = path.get(path.size()-1);
			pathfinder.getCurPath().remove(tableNode);
		}
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//If the pathfinder is done or the entity is close enough, we return isDone as true
		boolean isDone = (pathfinder.reachedEnd() || getTargetDistance() < 0.125f);
		
		//If done, stop moving
		if(isDone) {
			pathfinder.getMover().stop();
		}
		
		//Return done
		return isDone;
	}
	
	private float getTargetDistance() {
		//Get target position and person position
		Vector3f targetPos = target.copy();
		Vector3f personPos = person.getTransform().getPosition();
		
		//Get and return the distance bewteen the points
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

}
