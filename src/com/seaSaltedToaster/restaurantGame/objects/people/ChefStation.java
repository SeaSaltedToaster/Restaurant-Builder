package com.seaSaltedToaster.restaurantGame.objects.people;

import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ChefStation extends Component {

	private Vector3f[] spots;
	private Entity[] foodDisplays;
	private int foodSpotCount = 3;
	
	public ChefStation() {
		this.spots = new Vector3f[foodSpotCount];
		this.foodDisplays = new Entity[foodSpotCount];
	}
	
	@Override
	public void init() {
		Restaurant restaurant = MainApp.restaurant;
		float shelfHeight = 0.725f;
		float plateDist = 0.33f;
		float plateDistFromChef = 0.15f;
		this.spots[0] = new Vector3f(plateDistFromChef, shelfHeight, 0);
		this.spots[1] = new Vector3f(plateDistFromChef, shelfHeight, -plateDist);
		this.spots[2] = new Vector3f(plateDistFromChef, shelfHeight, plateDist);
		
		for(int i = 0; i < foodSpotCount; i++) {
			Entity entity = new Entity();
			entity.addComponent(new ModelComponent(null));
			Vector3f spotPos = spots[i];
			spotPos = MathUtils.rotatePointAtCenter(spotPos, this.entity.getTransform().getRotation().y);
			entity.getTransform().setPosition(spotPos.add(this.entity.getTransform().getPosition()));
			restaurant.engine.addEntity(entity);
			this.foodDisplays[i] = entity;
		}
	}

	@Override
	public void update() {
		List<ItemOrder> orders = MainApp.restaurant.chefOrders;
		int index = -1;
		for(Entity display : foodDisplays) {
			ModelComponent comp = (ModelComponent) display.getComponent("Model");
			comp.setMesh(null);
		}
		for(ItemOrder order : orders) {
			index++;
			if(!order.isCooked()) {
				continue;
			}
			if(index < foodSpotCount) {
				ModelComponent comp = (ModelComponent) foodDisplays[index].getComponent("Model");
				if(order.isCooked() && !order.isDelivered())
					comp.setMesh(order.getFoodItem().getVao());
				else
					comp.setMesh(null);
			}
		}
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
