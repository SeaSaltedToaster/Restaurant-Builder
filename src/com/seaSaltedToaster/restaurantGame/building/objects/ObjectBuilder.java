package com.seaSaltedToaster.restaurantGame.building.objects;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ObjectBuilder {
	
	//Placment
	private Building object;
	private Entity preview;
	
	//Last
	public static int buildingIndex = 0;
	private BuildingManager manager;
	
	private boolean isPlacing = false;
	private Transform lastChange;
	
	public ObjectBuilder(BuildingManager manager, Building object) {
		this.object = object;
		this.manager = manager;
		this.lastChange = new Transform();
		
		this.preview = object.getEntity().copyEntity();
		this.preview.addComponent(new BuildingId(-1, object, MainApp.restaurant.layers.get(0)));
		showPreview(false);
	}
	
	public void placeAt(BuildLayer layer) {
		boolean alreadyBuildingAt = !layer.isBuildingAt(preview.getPosition(), object.type);
		if(alreadyBuildingAt) {
			layer.addBuilding(preview.copyEntity(), object, -127);
			MainApp.restaurant.money -= object.getPrice();
			buildingIndex++;
		}
		this.isPlacing = false;
		this.preview.setTransform(lastChange);
		
		manager.setBuilding(false);
		manager.setCurrentBuilding(object);
	}
	
	public void movePreview(Vector3f position) {
		this.isPlacing = manager.isBuilding();
		if(isPlacing) {
			Vector3f placePosition = calculatePlacePosition(position);
			if(!lastChange.equals(placePosition)) {
				this.preview.setPosition(placePosition);
			}
			this.lastChange = preview.getTransform();
			return;
		}
		this.lastChange.setPosition(position);
	}
	
	
	private Vector3f calculatePlacePosition(Vector3f placement) {
		Vector3f snapPosition = null;
		float snap = 4.0f;
		
		BuildingType type = object.type;
		switch(type) {
			case Floor:
				//Default snap to 1x1 grid
				snapPosition = new Vector3f(snap(placement.x, 1.0f), 0, snap(placement.z, 1.0f));
				break;
			case Object:
				snapPosition = new Vector3f(snap(placement.x, snap), 0, snap(placement.z, snap));
				break;
			case WallObject :
				Entity entity = manager.getSelectedEntity();
				if(entity == null) {
					snapPosition = placement;
					break;
				}
				BuildingId id = (BuildingId) entity.getComponent("BuildingId");
				boolean onWall = id.getType().type == BuildingType.Wall;
				if(onWall) {
					WallComponent wallComp = (WallComponent) entity.getComponent("Wall");
					snapPosition = snapToWall(wallComp, placement);
					this.preview.getTransform().getRotation().setY(-wallComp.getProjAngle());
					break;
				}
				else {
					snapPosition = placement;
				}
			case Person:
				snapPosition = new Vector3f(snap(placement.x, 1.0f), 0, snap(placement.z, 1.0f));
				break;
			case Pillar:
				snapPosition = new Vector3f(snap(placement.x, 4.0f), 0, snap(placement.z, 4.0f));
				break;
			case Wall:
				snapPosition = new Vector3f(snap(placement.x, 3.0f), 0, snap(placement.z, 3.0f));
				break;
		}		
		return snapPosition;
	}
	
	private Vector3f snapToWall(WallComponent wallComp, Vector3f placement) {
		//Calculate X and Z line
		Vector3f snapPosition = new Vector3f(0.0f);
		snapPosition = MathUtils.snapPointToLine(placement, wallComp.getStart(), wallComp.getEnd());
		
		//Calculate height
		snapPosition.y = 0.0f;
		
		//Return
		return snapPosition;
	}

	private float snap(float value, float gridSize) {
		float snap = gridSize / 1.0f;
		return Math.round(value * snap) / snap;
	}
	
	public void setObject(Building object) {
		this.object = object;
		this.preview = object.getEntity().copyEntity();
		this.preview.setTransform(lastChange.copyTransform());
	}
	
	public void showPreview(boolean show) {
		Transform transform = preview.getTransform();
		if(show) {
			transform.setScale(1.0f);
		} else {
			transform.setScale(0.0f);
		}
	}

	public Building getObject() {
		return object;
	}

	public Entity getPreview() {
		return preview;
	}

}
