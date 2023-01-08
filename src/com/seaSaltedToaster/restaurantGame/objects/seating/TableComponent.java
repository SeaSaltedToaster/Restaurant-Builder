package com.seaSaltedToaster.restaurantGame.objects.seating;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.building.layers.PlacementListener;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class TableComponent extends Component implements PlacementListener {
		
	//Constants
	private static int MAX_DIST = 1;
	
	//Claim state
	private boolean isTaken = false;
	private int capacity = 0;
	
	//Chairs
	private List<SeatComponent> chairs;
	
	public TableComponent() {
		this.chairs = new ArrayList<SeatComponent>();
	}

	@Override
	public void init() {
		MainApp.restaurant.tables.add(this);
		BuildingManager.listeners.add(this);
		checkExisting();
	}
	
	private void checkExisting() {
		BuildLayer layer = ((BuildingId) entity.getComponent("BuildingId")).getLayer();
		for(Entity entity : layer.getBuildings()) {
			if(entity == null || !entity.hasComponent("BuildingId")) continue;
			BuildingId id = (BuildingId) entity.getComponent("BuildingId");
			this.notifyPlacement(entity, id.getType());
		}
	}

	@Override
	public void notifyPlacement(Entity obj, Building type) {
		boolean isChair = (type.type == BuildingType.Object) && obj.hasComponent("Chair");
		if(isChair) 
		{
			Vector3f toChair = obj.getPosition().copy().subtract(entity.getPosition().copy());
			float dist = Math.abs(toChair.length());
			
			if(dist <= MAX_DIST) 
			{
				SeatComponent component = (SeatComponent) obj.getComponent("Chair");
				int dot = (int) Math.toDegrees(Vector3f.angle(toChair, component.facing));
				if(dot != 179 && dot != 180 && dot != 149 && dot != 150) return;
				
				component.setTable(this);
				this.chairs.add(component);
				this.capacity++;
			}
		}
	}
	
	public int capacity() {
		return capacity;
	}

	public List<SeatComponent> getChairs() {
		return chairs;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isTaken() {
		return isTaken;
	}
	
	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Table";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new TableComponent();
	}

}
