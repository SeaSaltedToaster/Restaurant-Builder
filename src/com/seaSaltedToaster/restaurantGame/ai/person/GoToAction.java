package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.Node;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class GoToAction extends Action {

	//Data
	private Entity person;
	private Vector3f target;
	
	//Pathfinding
	private PathfinderComponent pathfinder;
	private boolean removeFinalNode; 
	
	public GoToAction(Vector3f target, Entity person, boolean removeFinalNode) {
		this.person = person;
		this.target = target;
		this.removeFinalNode = removeFinalNode;
	}
	
	@Override
	public void start() {
		this.pathfinder = (PathfinderComponent) person.getComponent("Pathfinder");
		pathfinder.goTo(target.copy());
		if(pathfinder.getCurPath() == null) {
			
		} 
		List<Node> path = pathfinder.getCurPath();
		if(path.size() > 1 && removeFinalNode) {
			Node tableNode = path.get(path.size()-1);
			pathfinder.getCurPath().remove(tableNode); //remove final node
		}
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		boolean isDone = (pathfinder.reachedEnd() || getTargetDistance() < 0.125f);
		if(isDone) {
			pathfinder.getMover().stop();
		}
		return isDone;
	}
	
	private float getTargetDistance() {
		Vector3f targetPos = target.copy();
		Vector3f personPos = person.getTransform().getPosition();
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

}
