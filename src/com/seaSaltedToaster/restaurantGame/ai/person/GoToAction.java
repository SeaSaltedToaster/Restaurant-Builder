package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.Node;
import com.seaSaltedToaster.restaurantGame.ai.PathfinderComponent;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
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
		System.out.println(target);
		this.person = person;
		this.target = target;
		this.removeFinalNode = removeFinalNode;
	}
	
	@Override
	public void start() {
		//Get the entity's pathfinder and call the goTo method
		this.pathfinder = (PathfinderComponent) person.getComponent("Pathfinder");
		this.pathfinder.goTo(target.copy());
		
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
		System.out.println(path.size());
	}

	@Override
	public void update() {
		//Nothing
	}

	@Override
	public boolean isDone() {
		//If the pathfinder is done or the entity is close enough, we return isDone as true
		boolean isDone = (getTargetDistance() < 0.25f || pathfinder.reachedEnd());
		
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
		Vector3f personPos = person.getTransform().getPosition().copy();
		
		//Get and return the distance bewteen the points
		float dist = targetPos.subtract(personPos).length();
		return Math.abs(dist);
	}

	@Override
	public String type() {
		return "GoTo";
	}

	@Override
	public void saveAction(SaveSystem system) {
		//SAVE ACTION GoTo
		BuildingId id = (BuildingId) super.object.getComponent("BuildingId");
		int index = id.getId();
		
		String data = "PATH_DATA[" + target.toString().replaceAll(",", "/").replace(" ", "") + "=" + removeFinalNode + "]";
		system.saveAction(index, super.actionIndex, type(), data);
	}

	@Override
	public void loadAction(String data) {
		//TODO LOAD ACTION GoTo
		String line = data.replace("[", "").replace("]", "").replace("PATH_DATA", "").replace(";", "");
		String[] lines = line.split("=");
		
		String dest = lines[0];
		String[] vectorParts = dest.split("/");
		float vx = Float.parseFloat(vectorParts[0]);
		float vy = Float.parseFloat(vectorParts[1]);
		float vz = Float.parseFloat(vectorParts[2]);
		Vector3f finalDest = new Vector3f(vx,vy,vz);
		this.target = finalDest;
		
		boolean end = Boolean.parseBoolean(lines[1]);
		this.removeFinalNode = end;
		
		this.person = super.object;
	}

}
