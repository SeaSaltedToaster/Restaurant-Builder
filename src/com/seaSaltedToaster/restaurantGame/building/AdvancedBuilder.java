package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class AdvancedBuilder {
	
	private Building object;
	private Entity start, end;
	private List<Entity> placeTiles;
	
	private Vector3f lastChange;
	
	private boolean isPlacing = false;
	private int buildingIndex = 0;
	
	public AdvancedBuilder(Building object) {
		this.object = object;
		this.lastChange = new Vector3f(0.0f);
		
		this.start = object.getEntity().copyEntity();
		this.end = object.getEntity().copyEntity();
		this.placeTiles = new ArrayList<Entity>();
		showPreview(false);
	}
	
	public void startPlacement() {
		this.isPlacing = true;
		this.start.getTransform().setPosition(lastChange);
	}
	
	public void increasePlacement(Vector3f position) {
		if(!lastChange.equals(position) && isPlacing) {
			end.getTransform().setPosition(position);
			//calculateBox();
		} else if(!isPlacing) {
			start.getTransform().setPosition(position);
			end.getTransform().setPosition(position);
		}
		this.lastChange = position;
	}
	
	public void endPlacement(BuildLayer layer) {
		calculateBox();
		for(Entity entity : placeTiles) {
			entity.getTransform().getPosition().setY(layer.getLayerId() * BuildLayer.HEIGHT_OFFSET);
			if(!layer.isBuildingAt(entity.getTransform().getPosition(), object.type)) {
				layer.addBuilding(entity, object, buildingIndex);
				buildingIndex++;
			}
		}
		this.isPlacing = false;
		this.placeTiles.clear();	
		start.getTransform().setPosition(lastChange);
		end.getTransform().setPosition(lastChange);
	}
	
	private void calculateBox() {
		//Axis bounding
		Vector3f startPos = start.getTransform().getPosition();
		Vector3f endPos = end.getTransform().getPosition();
		boolean negativeX = startPos.x > endPos.x;
		boolean negativeZ = startPos.z > endPos.z;
		
		//Loop through grids in box
		List<Vector3f> gridPositions = new ArrayList<Vector3f>();
		for(float x = startPos.x; x <= startPos.x + Math.abs(endPos.x-startPos.x); x++) {
			for(float z = startPos.z; z <= startPos.z + Math.abs(endPos.z-startPos.z); z++) {
				Vector3f newPosition = new Vector3f(x, 0, z);
				if(negativeX)
					newPosition.x = startPos.x - (x - startPos.x);
				if(negativeZ)
					newPosition.z = startPos.z - (z - startPos.z);
				gridPositions.add(newPosition);
				Entity entity = object.getEntity().copyEntity();
				entity.getTransform().setPosition(newPosition);
				entity.getTransform().setRotation(end.getTransform().getRotation().copy());
				placeTiles.add(entity);
			}
		}
		gridPositions.clear();
	}

	public void showPreview(boolean show) {
		Transform transform = start.getTransform();
		Transform transform2 = end.getTransform();
		if(show) {
			transform.setScale(1.0f);
			transform2.setScale(1.0f);
		} else {
			transform.setScale(0.0f);
			transform2.setScale(0.0f);
		}
	}

	public List<Entity> getPreviews() {
		return placeTiles;
	}

	public Building getObject() {
		return object;
	}

	public Entity getStart() {
		return start;
	}

	public Entity getEnd() {
		return end;
	}

	public void setObject(Building object) {
		this.placeTiles.clear();
		this.object = object;
		this.start = object.getEntity().copyEntity();
		start.getTransform().setPosition(lastChange);
		this.end = object.getEntity().copyEntity();
		end.getTransform().setPosition(lastChange);
	}

}
