package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.Node;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class GoToTable extends Action {

	//Info
	private Entity entity;
	private boolean cantFindTable = false;
	
	//Pathfinding
	private PathfinderComponent pathfinder;
	private Entity table;
	
	public GoToTable(Entity entity, Entity table) {
		this.entity = entity;
		this.table = table;
	}
	
	@Override
	public void start() {		
		this.pathfinder = (PathfinderComponent) entity.getComponent("Pathfinder");
		pathfinder.goTo(table.getTransform().getPosition().copy());
		if(pathfinder.getCurPath() == null) {
			
		} 
		List<Node> path = pathfinder.getCurPath();
		if(path.size() > 1) {
			Node tableNode = path.get(path.size()-1);
			pathfinder.getCurPath().remove(tableNode); //remove final node
		}
		System.out.println("Going to table");
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		boolean isDone = (pathfinder.reachedEnd() || getTableDist() < 0.5f || cantFindTable);
		if(isDone) {
			pathfinder.getMover().stop();
			System.out.println("Reached table");
		}
		return isDone;
	}

	private float getTableDist() {
		Vector3f tablePos = table.getTransform().getPosition();
		Vector3f personPos = entity.getTransform().getPosition();
		float dist = tablePos.subtract(personPos).length();
		return Math.abs(dist);
	}
	
}
