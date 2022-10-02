package com.seaSaltedToaster.restaurantGame.objects.people;

import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.building.renderer.BuildingRenderer;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ChefStation extends Component {

	//Chef data
	private boolean isOccupied = false;
	private Entity owner;
	
	//Cook data
	private int spotCount = 3;
	private Entity[] foodDisplays;
	private Vector3f[] spots;
	
	public ChefStation() {
		this.spots = new Vector3f[spotCount];
		this.foodDisplays = new Entity[spotCount];
	}
	
	@Override
	public void init() {
		float shelfHeight = 0.7f;
		float plateDist = 0.33f;
		float plateDistFromChef = -0.35f;
		this.spots[0] = new Vector3f(plateDistFromChef, shelfHeight, 0);
		this.spots[1] = new Vector3f(plateDistFromChef, shelfHeight, -plateDist);
		this.spots[2] = new Vector3f(plateDistFromChef, shelfHeight, plateDist);
		
		for(int i = 0; i < spotCount; i++) {
			Entity entity = new Entity();
			entity.addComponent(new ModelComponent(null));
			
			Vector3f chefPos = this.entity.getTransform().getPosition().copy();
			Vector3f spotPos = spots[i];
			
			Vector3f newSpot = new Vector3f(spotPos);
			float times = (this.entity.getTransform().getRotation().y / 90.0f);
			for(int x = 0; x < times; x++) {
				Vector3f result = new Vector3f(spotPos);
				result.x = newSpot.z;
				result.z = -newSpot.x;
				newSpot = result;
			}
			
			Vector3f combinedPos = chefPos.add(newSpot);
			entity.getTransform().setPosition(combinedPos);
			this.foodDisplays[i] = entity;
		}
		
		BuildLayer layer = MainApp.restaurant.layers.get(0);
		BuildingRenderer render = layer.getManager().getRenderer();
		for(Entity food : foodDisplays) {
			render.getFoods().add(food);
		}
	}

	@Override
	public void update() {
		if(!isOccupied) return;
		
		List<ItemOrder> orders = MainApp.restaurant.chefOrders;
		int index = -1;
		for(Entity display : foodDisplays) {
			ModelComponent comp = (ModelComponent) display.getComponent("Model");
			comp.setMesh(null);
		}
		for(ItemOrder order : orders) {
			if(order.getChefWhoCooked() != owner) continue;
			index++;
			if(!order.isCooked()) {
				continue;
			}
			if(index < spotCount) {
				ModelComponent comp = (ModelComponent) foodDisplays[index].getComponent("Model");
				if(order.isCooked() && !order.isDelivered())
					comp.setMesh(order.getFoodItem().getVao());
				else
					comp.setMesh(null);
			}
		}
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}

	@Override
	public void reset() {

	}

	@Override
	public String getComponentType() {
		return "ChefStation";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new ChefStation();
	}
}
