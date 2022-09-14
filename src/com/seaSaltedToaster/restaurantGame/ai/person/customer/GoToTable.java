package com.seaSaltedToaster.restaurantGame.ai.person.customer;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.Node;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class GoToTable extends Action {

	//Entity we are moving to the table
	private Entity entity;
	
	//Pathfinding
	private PathfinderComponent pathfinder;
	
	//The exact table and seat we are going to
	private Entity table;
	private int seat;
	
	public GoToTable(Entity entity, Entity table, int seat) {
		this.entity = entity;
		this.table = table;
		this.seat = seat;
	}
	
	@Override
	public void start() {		
		//Get the pathfinder and move there
		this.pathfinder = (PathfinderComponent) entity.getComponent("Pathfinder");
		pathfinder.goTo(table.getTransform().getPosition().copy());
		
		//If the path doesnt exist
		if(pathfinder.getCurPath() == null) {
			
		} 
		
		//Get the final path list
		List<Node> path = pathfinder.getCurPath();
		
		//Remove the last node if the path is more than one tile
		if(path.size() > 1) {
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
		//Get the entity's action component
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		
		//Check if we are close enough to the table
		float tableDist = getTargetDistance();
		boolean atTable = (tableDist < 1.0f && tableDist > -1.0f);
		
		//If we are at the table
		if(atTable) {
			//Stop and sit down
			pathfinder.getMover().stop();
			comp.getActions().add(new SitAtTable(entity, table, seat));
			return true;
		} 
		//If we are not
		if(!atTable && pathfinder.reachedEnd()) {
			//Repeat going to the table
			comp.getActions().add(new GoToTable(entity, table, seat));
			return true;
		}
		
		//Return done state
		return atTable;
	}

	private float getTargetDistance() {
		//Get target position and person position
		Vector3f targetPos = table.getPosition().copy();
		Vector3f personPos = entity.getPosition();
		
		//Get and return the distance bewteen the points
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}
	
}
